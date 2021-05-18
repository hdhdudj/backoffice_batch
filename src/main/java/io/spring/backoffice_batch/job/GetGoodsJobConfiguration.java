package io.spring.backoffice_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GetGoodsJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job getGoodsJob(){
        return jobBuilderFactory.get("getGoodsJob")
                .start(getGoodsStep1())
                .build();
    }

    @Bean
    public Step getGoodsStep1(){
        return stepBuilderFactory.get("getGoodsStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is getGoodsStep1");
                    GoodsSearch.retrieveOrder("","");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
