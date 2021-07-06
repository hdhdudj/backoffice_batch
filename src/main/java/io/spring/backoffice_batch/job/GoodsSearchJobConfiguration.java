package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import io.spring.main.util.StringFactory;
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
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import io.spring.main.apis.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class GoodsSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final GoodsSearch goodsSearch;
    private static final int chunkSize = 1;
    private int cnt = 0;

    @Bean
    public Job searchGoodsJob(){
        return jobBuilderFactory.get("searchGoodsJob")
                .start(searchGoodsStep1())
                .next(searchGoodsStep2())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    public Step searchGoodsStep1(){
        return stepBuilderFactory.get("searchGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchGoodsStep1");
                    // if table entity 리스트 생성
                    // 트랜잭션1. if table 저장 함수
                    goodsSearch.saveIfTables("", ""); //, ifGoodsOptionList, ifGoodsTextOptionList, ifGoodsAddGoodsList);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step searchGoodsStep2(){
        log.info("----- This is searchGoodsStep2");
        return stepBuilderFactory.get("searchGoodsStep2")
                .<IfGoodsMaster, String>chunk(chunkSize)
                .reader(jpaGoodsSearchItemWriterReader())
                .processor(jpaGoodsSearchItemProcessor())
                .writer(jpaGoodsSearchItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader jpaGoodsSearchItemWriterReader() {

        JpaPagingItemReader r=null;
    try {
        r = new JpaPagingItemReaderBuilder<IfGoodsMaster>()
            .name("jpaGoodsSearchItemWriterReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(1)
            .queryString("SELECT i FROM IfGoodsMaster i where i.uploadStatus='01' order by i.goodsNo asc")
            .build();
    }catch(Exception e){
        e.printStackTrace();
    }
        return r;
    }

    @Bean
    public ItemProcessor<IfGoodsMaster, IfGoodsMaster> jpaGoodsSearchItemProcessor() {
//        System.out.println("----- cnt : " + cnt);
//        cnt++;
        return ifGoodsMaster -> goodsSearch.saveOneGoodsNo(ifGoodsMaster.getGoodsNo(),ifGoodsMaster);
    }

    @Bean
    public JpaItemWriter jpaGoodsSearchItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
//    @Bean
//    public Step searchGoodsStep2(){
//        return stepBuilderFactory.get("searchGoodsStep2")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("----- This is searchGoodsStep2");
////                    goodsSearch.searchGoodsSeq("1999-01-01","2022-01-01");
//                    List<IfGoodsMaster> ifGoodsMasterList = goodsSearch.getIfGoodsMasterListWhereUploadStatus01(); // if_goods_master
//                    // 트랜잭션2. if table의 한 줄을 자체 table에 저장할 때 goodsNo 하나 기준으로 어떤 if table에서 실패하면 주루룩 실패해야 함.
//                    for(IfGoodsMaster ifGoodsMaster : ifGoodsMasterList){
//                        goodsSearch.saveOneGoodsNo(ifGoodsMaster.getGoodsNo(), ifGoodsMaster);
//                    }
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
}
