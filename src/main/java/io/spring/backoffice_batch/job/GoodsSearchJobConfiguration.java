package io.spring.backoffice_batch.job;

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
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import io.spring.main.apis.GoodsSearch;
import io.spring.main.model.goods.entity.IfGoodsMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class GoodsSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final GoodsSearch goodsSearch;
	private static final int chunkSize = 100;
    private static final int pageSize = 100;
    private int cnt = 0;

    @Bean
    public Job searchGoodsJob(){
        return jobBuilderFactory.get("searchGoodsJob")
                .start(searchGoodsStep1(null, null, null, null))
                .next(searchGoodsStep2())
                .next(searchGoodsStep3())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    @JobScope
    public Step searchGoodsStep1(@Value("#{jobParameters[page]}") String page, @Value("#{jobParameters[goodsNo]}") String goodsNo,
                                 @Value("#{jobParameters[searchDateType]}") String searchDateType,
                                 @Value("#{jobParameters[dateParam]}")String dateParam){
        return stepBuilderFactory.get("searchGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchGoodsStep1");
                    // if table entity 리스트 생성
                    // 트랜잭션1. if table 저장 함수

//					goodsNo = "1000003553";

					goodsSearch.saveIfTables(goodsNo, searchDateType, dateParam, page); // , ifGoodsOptionList, ifGoodsTextOptionList,
																			// ifGoodsAddGoodsList);
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
        log.debug("실행됨.");
        JpaPagingItemReader<IfGoodsMaster> jpaPagingItemReader = new JpaPagingItemReader<IfGoodsMaster>(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        String goodsNo = null;
        jpaPagingItemReader.setName("jpaGoodsSearchItemWriterReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(pageSize);
        jpaPagingItemReader.setQueryString("SELECT i FROM IfGoodsMaster i where i.uploadStatus='01' and ("+goodsNo+" is null or i.goodsNo='"+goodsNo+"') order by i.goodsNo asc");
        return jpaPagingItemReader;
    }

    @Bean
    public ItemProcessor<IfGoodsMaster, IfGoodsMaster> jpaGoodsSearchItemProcessor() {
//        System.out.println("----- cnt : " + cnt);
//        cnt++;
        return ifGoodsMaster -> goodsSearch.saveOneGoodsNo(ifGoodsMaster.getGoodsNo(),ifGoodsMaster);
    }

    @Bean
    public JpaItemWriter<IfGoodsMaster> jpaGoodsSearchItemWriter() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    @JobScope
    public Step searchGoodsStep3(){
        log.info("----- This is searchGoodsStep3");
        return stepBuilderFactory.get("searchGoodsStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchGoodsStep3");
                    goodsSearch.insertTms(); // tmitem, tmmapi 삽입 query 실행
                    return RepeatStatus.FINISHED;
                })
                .build();
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
