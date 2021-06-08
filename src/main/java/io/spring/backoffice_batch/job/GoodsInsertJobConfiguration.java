package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.GoodsInsert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class GoodsInsertJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GoodsInsert goodsInsert;

    @Bean
    public Job insertGoodsJob(){
        return jobBuilderFactory.get("insertGoodsJob")
                .start(insertGoodsStep1())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    public Step insertGoodsStep1(){
        return stepBuilderFactory.get("insertGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is insertGoodsStep1");
                    goodsInsert.insertGoods();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
