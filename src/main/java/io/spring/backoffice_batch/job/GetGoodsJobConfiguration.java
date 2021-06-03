package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import io.spring.main.apis.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class GetGoodsJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private final GoodsSearch goodsSearch;

    @Bean
    public Job getGoodsJob(){
        return jobBuilderFactory.get("getGoodsJob")
                .start(getGoodsStep1())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    public Step getGoodsStep1(){
        return stepBuilderFactory.get("getGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is getGoodsStep1");
                    goodsSearch.getGoodsSeq("1999-01-01","2022-01-01");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
