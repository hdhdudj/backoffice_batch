package io.spring.backoffice_batch.job;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * searchOrderJob 필수 parameter :
 * --job.name=searchOrderJob (프로젝트 내 어떤 잡인지 알려주는 파라미터)
 * version=0 (스프링 배치에서 요구하는 필수 파라미터)
 *
 * 필수는 아닌 option parameter :
 * -page=3 (현재 날짜에서 얼마나 전으로 돌아가야 하는지에 대한 param. day와 조합되어 날짜 param을 결정함.)
 * -day=7 (며칠 동안의 데이터를 긁어올지 결정하는 파라미터.)
 * -mode={order or modify} (order : 최근 주문 순으로 옴, modify : 최근 수정한 주문 순으로 옴)
 * -orderNo=2112301555180852 (특정 주문 정보만 긁어오고 싶을 때. orderNo와 page 중 하나는 필수이다.)
 * -next={true of false} (false가 default이고 false인 경우 page의 숫자만큼 현재에서 역으로 날짜 가져올 정보를 정함. true인 경우는 2017년 초일부터 page와 day가 조합된 숫자만큼 지난 날짜의 정보를 가져옴.)
 * --spring.config.location=/var/jenkins_home/jar/order_search_config/application.properties,/var/jenkins_home/jar/order_search_config/godourl.yml (외부의 설정파일을 참조할 때)
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class OrderSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final OrderSearch orderSearch;

	private static final int chunkSize = 1000;


    @Bean
    public Job searchOrderJob(){
        return jobBuilderFactory.get("searchOrderJob")
				.start(searchOrderStep1(null, null, null, null, null))
                .next(searchOrderStep2())
                .next(searchOrderStep3())
				 .next(searchOrderStep4())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    /**
     * 가져온 if table을 전부 저장하는 step
     */
    @Bean
    @JobScope
	public Step searchOrderStep1(@Value("#{jobParameters[page]}") String page, @Value("#{jobParameters[day]}") String day,
			@Value("#{jobParameters[mode]}") String mode, @Value("#{jobParameters[next]}") String nexts, @Value("#{jobParameters[orderNo]}") String orderNo) {
        log.info("----- This is searchOrderStep1");
        return stepBuilderFactory.get("searchOrderStep1")
                .tasklet((contribution, chunkContext) -> {
                    boolean next = nexts == null || nexts.equals("false")? false : true; // false가 기본값
                    
					// if(mode==null) {
//                    	mode="modify";
					// }
                    
                    // 트랜잭션1. if table 저장
                    int n = page == null || page.trim().equals("")? -1 : Integer.parseInt(page);
                    int dayParam = day == null? 2 : Integer.parseInt(day); // 긁어올 날짜 단위(일)

                    String orderNum = orderNo == null? "": orderNo;

                    String startDt;
                    String endDt;
                    String dateType;
                    if(n >= 0 && !next){
                        startDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, dayParam * -(n+1));
                        endDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, dayParam * -n);//Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
                    }
                    else if(n >= 0 && next){
                        startDt = Utilities.getAnotherDate("2017-01-01",StringFactory.getDateFormat(),Calendar.DATE, dayParam * n);
                        endDt = Utilities.getAnotherDate("2017-01-01",StringFactory.getDateFormat(),Calendar.DATE, dayParam * (n+1));//Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
                    }
                    else{
                        startDt = null;
                        endDt = null;
                    }
                    startDt = startDt == null? "":startDt;
                    endDt = endDt == null? "":endDt;
                    dateType = mode == null ? "order" : mode;



					orderSearch.saveIfTables(orderNum, startDt, endDt, dateType); // "2106301555509122","2107021751024711",
																					// "2101081407020195"(addGoods 정렬
																					// 테스트용), "2110061315569293"(최신)
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * step1에서 저장한 if 테이블에서 한 줄씩 가져와 trdst DB에 쪼개 넣는 step
     */
    @Bean
    @JobScope
    public Step searchOrderStep2(){
        log.info("----- This is searchOrderStep2");
        return stepBuilderFactory.get("searchOrderStep2")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader(null, null))
                .processor(jpaOrderSearchItemProcessor())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }

    /**
     * step2에서 저장한 주문들의 상태를 판단하는 step (p1 -> A01)
     */
    @Bean
    @JobScope
    public Step searchOrderStep3(){
        log.info("----- This is searchOrderStep3");
        return stepBuilderFactory.get("searchOrderStep3")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader2(null, null))
                .processor(jpaOrderSearchItemProcessor2())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }
    /**
     * step2에서 저장한 주문들의 상태를 판단하는 step (A01 -> 서버)
     */
    @Bean
    @JobScope
    public Step searchOrderStep4(){
        log.info("----- This is searchOrderStep4");
        return stepBuilderFactory.get("searchOrderStep4")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader3(null, null))
                .processor(jpaOrderSearchItemProcessor3())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader jpaOrderSearchItemWriterReader(@Value("#{jobParameters[orderNo]}") String orderNo, @Value("#{jobParameters[day]}") String day) {
//        this.gOrderNo = orderNo;
//        String now = Utilities.removeTAndTransToStr(LocalDateTime.now().plusDays(1));
//        String yesterday = Utilities.removeTAndTransToStr(LocalDateTime.now().minusDays(3));
        JpaPagingItemReader<IfOrderMaster> jpaPagingItemReader = new JpaPagingItemReader<IfOrderMaster>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        int dayInt = day == null? -1 : Integer.parseInt(day);
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        Map<String, Object> paramMap = new HashMap<>();
        if(orderNo == null){
            jpaPagingItemReader.setQueryString("SELECT i FROM IfOrderMaster i join fetch i.ifOrderDetail where i.ifStatus='01' and i.updDt between :yesterday and :now");
            paramMap.put("now", LocalDateTime.now().plusDays(1));
            paramMap.put("yesterday", LocalDateTime.now().minusDays(day == null? 7 : dayInt));
        } else
        {
            jpaPagingItemReader.setQueryString("SELECT i FROM IfOrderMaster i join fetch i.ifOrderDetail where i.ifStatus='01' and i.channelOrderNo=:channelOrderNo");
            paramMap.put("channelOrderNo", orderNo);
        }
//        String channelOrderNo = this.gOrderNo;// "2112301555180852"; //null;//"2111241449240603";
        jpaPagingItemReader.setParameterValues(paramMap);
        return jpaPagingItemReader;
    }
    @Bean
    public ItemProcessor<IfOrderMaster, IfOrderMaster> jpaOrderSearchItemProcessor() {
        return ifOrderMaster -> orderSearch.saveOneIfNo(ifOrderMaster);
    }

    @Bean
    @StepScope
    public JpaPagingItemReader jpaOrderSearchItemWriterReader2(@Value("#{jobParameters[orderNo]}") String orderNo, @Value("#{jobParameters[day]}") String day) {
//        String now = Utilities.removeTAndTransToStr(LocalDateTime.now().plusDays(1));
//        String yesterday = Utilities.removeTAndTransToStr(LocalDateTime.now().minusDays(3));
        JpaPagingItemReader<TbOrderDetail> jpaPagingItemReader = new JpaPagingItemReader<TbOrderDetail>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        int dayInt = day == null? -1 : Integer.parseInt(day);
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        Map<String, Object> param = new HashMap<>();
        if(orderNo == null){
            jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where statusCd='p1' and td.updDt between :yesterday and :now order by td.orderId asc");
            param.put("now", LocalDateTime.now().plusDays(1));
            param.put("yesterday", LocalDateTime.now().minusDays(day == null? 7 : dayInt));
        } else
        {
            jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where statusCd='p1' and td.channelOrderNo=:channelOrderNo order by td.orderId asc");
            param.put("channelOrderNo", orderNo);
        }
//        String channelOrderNo = this.gOrderNo;// "2112301555180852"; //null;//"2111241449240603";
        jpaPagingItemReader.setParameterValues(param);
        return jpaPagingItemReader;
    }
    @Bean
    @StepScope
    public JpaPagingItemReader jpaOrderSearchItemWriterReader3(@Value("#{jobParameters[orderNo]}") String orderNo, @Value("#{jobParameters[day]}") String day) {
        JpaPagingItemReader<TbOrderDetail> jpaPagingItemReader = new JpaPagingItemReader<TbOrderDetail>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        int dayInt = day == null? -1 : Integer.parseInt(day);
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        Map<String, Object> param = new HashMap<>();
        if(orderNo == null){
            jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where orderId in (select iom.orderId from IfOrderMaster iom where iom.ifStatus='02') and td.statusCd='A01' and td.updDt between :yesterday and :now order by td.orderId asc");
            param.put("now", LocalDateTime.now().plusDays(1));
            param.put("yesterday", LocalDateTime.now().minusDays(day == null? 7 : dayInt));
        } else
        {
            jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where orderId in (select iom.orderId from IfOrderMaster iom where iom.ifStatus='02') and td.channelOrderNo=:channelOrderNo and td.statusCd='A01' order by td.orderId asc");
            param.put("channelOrderNo", orderNo);
        }
//        jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where orderId in (select iom.orderId from IfOrderMaster iom where iom.ifStatus='02') and (td.channelOrderNo=:channelOrderNo or :channelOrderNo is null) and td.statusCd='A01' and td.updDt between :yesterday and :now order by td.orderId asc");
//        String channelOrderNo = this.gOrderNo;// "2112301555180852"; //null;//"2111241449240603";
        jpaPagingItemReader.setParameterValues(param);
        return jpaPagingItemReader;
    }
    @Bean
    public ItemProcessor<TbOrderDetail, TbOrderDetail> jpaOrderSearchItemProcessor2() {
        return tbOrderDetail -> orderSearch.changeOneToStatusCd1(tbOrderDetail);
    }

    @Bean
    public ItemProcessor<TbOrderDetail, TbOrderDetail> jpaOrderSearchItemProcessor3() {
        return tbOrderDetail -> orderSearch.changeOneToStatusCd2(tbOrderDetail);
    }

//    @Bean
//    public Step searchOrderStep2(){
//        log.info("----- This is searchOrderStep2");
//        return stepBuilderFactory.get("searchOrderStep2")
//                .tasklet((contribution, chunkContext) -> {
//                    List<IfOrderMaster> ifOrderMasterList = orderSearch.getIfOrderMasterListWhereIfStatus01(); // if_order_master
////                  // 트랜잭션2. if table의 한 줄을 자체 table에 저장할 때 ifNo 하나 기준으로 어떤 if table에서 실패하면 주루룩 실패해야 함.
//                    for(IfOrderMaster ifOrderMaster : ifOrderMasterList){
//                        orderSearch.saveOneIfNo(ifOrderMaster);
//                    }
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
    @Bean
    public JpaItemWriter jpaOrderSearchItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
