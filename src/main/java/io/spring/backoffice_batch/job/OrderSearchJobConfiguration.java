package io.spring.backoffice_batch.job;

import java.time.LocalDateTime;
import java.util.Calendar;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
				.start(searchOrderStep1(null, null, null, null))
                .next(searchOrderStep2())
                .next(searchOrderStep3())
				// .next(searchOrderStep4())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    /**
     * 가져온 if table을 전부 저장하는 step
     */
    @Bean
    @JobScope
	public Step searchOrderStep1(@Value("#{jobParameters[page]}") String page,
			@Value("#{jobParameters[mode]}") String mode, @Value("#{jobParameters[next]}") String nexts, @Value("#{jobParameters[orderNo]}") String orderNo) {
        return stepBuilderFactory.get("searchOrderStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchOrderStep1");

                    boolean next = nexts == null || nexts.equals("false")? false : true; // false가 기본값
                    
					// if(mode==null) {
//                    	mode="modify";
					// }
                    
                    // 트랜잭션1. if table 저장
                    int n = page == null || page.trim().equals("")? -1 : Integer.parseInt(page);
                    int day = 7; // 긁어올 날짜 단위(일)

                    String orderNum = orderNo == null? "": orderNo;

                    String startDt;
                    String endDt;
                    String dateType;
                    if(n >= 0 && !next){
                        startDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, day * -(n+1));
                        endDt = Utilities.getAnotherDate(null, StringFactory.getDateFormat(),Calendar.DATE, day * -n);//Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
                    }
                    else if(n >= 0 && next){
                        startDt = Utilities.getAnotherDate("2017-01-01",StringFactory.getDateFormat(),Calendar.DATE, day * n);
                        endDt = Utilities.getAnotherDate("2017-01-01",StringFactory.getDateFormat(),Calendar.DATE, day * (n+1));//Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
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
    public Step searchOrderStep2(){
        log.info("----- This is searchOrderStep2");
        return stepBuilderFactory.get("searchOrderStep2")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader())
                .processor(jpaOrderSearchItemProcessor())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }

    /**
     * step2에서 저장한 주문들의 상태를 판단하는 step (p1 -> A01)
     */
    @Bean
    public Step searchOrderStep3(){
        log.info("----- This is searchOrderStep3");
        return stepBuilderFactory.get("searchOrderStep3")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader2())
                .processor(jpaOrderSearchItemProcessor2())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }
    /**
     * step2에서 저장한 주문들의 상태를 판단하는 step (A01 -> 서버)
     */
    @Bean
    public Step searchOrderStep4(){
        log.info("----- This is searchOrderStep3");
        return stepBuilderFactory.get("searchOrderStep4")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaOrderSearchItemWriterReader3())
                .processor(jpaOrderSearchItemProcessor3())
                .writer(jpaOrderSearchItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader jpaOrderSearchItemWriterReader() {
        String now = Utilities.removeTAndTransToStr(LocalDateTime.now());
        String yesterday = Utilities.removeTAndTransToStr(LocalDateTime.now().minusDays(1));
        JpaPagingItemReader<IfOrderMaster> jpaPagingItemReader = new JpaPagingItemReader<IfOrderMaster>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        jpaPagingItemReader.setQueryString("SELECT i FROM IfOrderMaster i where i.ifStatus='01' and i.updDt between '"+yesterday+"' and '"+now+"'");
        return jpaPagingItemReader;
    }
    @Bean
    public ItemProcessor<IfOrderMaster, IfOrderMaster> jpaOrderSearchItemProcessor() {
        return ifOrderMaster -> orderSearch.saveOneIfNo(ifOrderMaster);
    }

    @Bean
    public JpaPagingItemReader jpaOrderSearchItemWriterReader2() {
        String now = Utilities.removeTAndTransToStr(LocalDateTime.now());
        String yesterday = Utilities.removeTAndTransToStr(LocalDateTime.now().minusDays(1));
        JpaPagingItemReader<TbOrderDetail> jpaPagingItemReader = new JpaPagingItemReader<TbOrderDetail>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where statusCd='p1' and td.updDt between '"+yesterday+"' and '"+now +"' order by td.orderId asc ");
//        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' order by t.orderId asc");
//        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' and t.orderId='O00000363' and t.orderSeq='0001'");
        return jpaPagingItemReader;
    }
    @Bean
    public JpaPagingItemReader jpaOrderSearchItemWriterReader3() {
        String now = Utilities.removeTAndTransToStr(LocalDateTime.now());
        String yesterday = Utilities.removeTAndTransToStr(LocalDateTime.now().minusDays(1));
        JpaPagingItemReader<TbOrderDetail> jpaPagingItemReader = new JpaPagingItemReader<TbOrderDetail>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        jpaPagingItemReader.setQueryString("select td from TbOrderDetail td where orderId in (select iom.orderId from IfOrderMaster iom where iom.ifStatus='02') and td.updDt between '"+yesterday+"' and '"+now+"' order by td.orderId asc");
//        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' order by t.orderId asc");
//        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' and t.orderId='O00000363' and t.orderSeq='0001'");
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
