package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.apis.XmlSave;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.model.order.entity.IfOrderMaster;
import io.spring.main.util.StringFactory;
import io.spring.main.util.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
                .start(searchOrderStep1())
                .next(searchOrderStep2())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    public Step searchOrderStep1(){
        return stepBuilderFactory.get("searchOrderStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchOrderStep1");
                    // 트랜잭션1. if table 저장
                    String startDt = Utilities.getAnotherDate(StringFactory.getDateFormat(),Calendar.DATE, -7);
                    String endDt = Utilities.getDateToString(StringFactory.getDateFormat(), new Date());
                    orderSearch.saveIfTables("", startDt, endDt); //"2106301555509122"
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step searchOrderStep2(){
        log.info("----- This is searchOrderStep2");
        return stepBuilderFactory.get("searchOrderStep2")
                .<IfOrderMaster, String>chunk(chunkSize)
                .reader(jpaItemWriterReader())
                .processor(jpaItemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader jpaItemWriterReader() {
        return new JpaPagingItemReaderBuilder<IfOrderMaster>()
                .name("jpaItemWriterReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT i FROM IfOrderMaster i where i.ifStatus='01'")
                .build();
    }

    @Bean
    public ItemProcessor<IfOrderMaster, IfOrderMaster> jpaItemProcessor() {
        return ifOrderMaster -> orderSearch.saveOneIfNo(ifOrderMaster);
    }

    @Bean
    public JpaItemWriter jpaItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
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
