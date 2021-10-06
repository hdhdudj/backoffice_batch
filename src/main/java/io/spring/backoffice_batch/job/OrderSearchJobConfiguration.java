package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.model.order.entity.TbOrderDetail;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class OrderSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final OrderSearch orderSearch;

    private static final int chunkSize = 1;


    @Bean
    public Job searchOrderJob(){
        return jobBuilderFactory.get("searchOrderJob")
                .start(searchOrderStep1(null))
                .next(searchOrderStep2())
                .next(searchOrderStep3())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step searchOrderStep1(@Value("#{jobParameters[page]}") String page){
        return stepBuilderFactory.get("searchOrderStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchOrderStep1");
                    // 트랜잭션1. if table 저장
                    int n = page == null? -1 : Integer.parseInt(page);
                    String startDt;
                    String endDt;
                    if(n >= 0){
                        startDt = Utilities.getAnotherDate(StringFactory.getDateFormat(),Calendar.DATE, -7 * (n+1));
                        endDt = Utilities.getAnotherDate(StringFactory.getDateFormat(),Calendar.DATE, -7 * n);//Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
                    }
                    else{
                        startDt = null;
                        endDt = null;
                    }
                    orderSearch.saveIfTables("2110061315569293", startDt, endDt); //"2106301555509122","2107021751024711", "2101081407020195"(addGoods 정렬 테스트용), "2110061315569293"(최신)
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

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

    @Bean
    public JpaPagingItemReader jpaOrderSearchItemWriterReader() {
        JpaPagingItemReader<IfOrderMaster> jpaPagingItemReader = new JpaPagingItemReader<IfOrderMaster>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        jpaPagingItemReader.setQueryString("SELECT i FROM IfOrderMaster i where i.ifStatus='01'");
        return jpaPagingItemReader;
    }
    @Bean
    public ItemProcessor<IfOrderMaster, IfOrderMaster> jpaOrderSearchItemProcessor() {
        return ifOrderMaster -> orderSearch.saveOneIfNo(ifOrderMaster);
    }
    @Bean
    public JpaItemWriter jpaOrderSearchItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public JpaPagingItemReader jpaOrderSearchItemWriterReader2() {
        JpaPagingItemReader<TbOrderDetail> jpaPagingItemReader = new JpaPagingItemReader<TbOrderDetail>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        jpaPagingItemReader.setName("jpaOrderSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' order by t.orderId asc");
//        jpaPagingItemReader.setQueryString("SELECT t FROM TbOrderDetail t join fetch t.ifOrderMaster i where i.ifStatus='02' and t.orderId='O00000363' and t.orderSeq='0001'");
        return jpaPagingItemReader;
    }
    @Bean
    public ItemProcessor<TbOrderDetail, TbOrderDetail> jpaOrderSearchItemProcessor2() {
        return tbOrderDetail -> orderSearch.changeOneToStatusCd(tbOrderDetail);
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
}
