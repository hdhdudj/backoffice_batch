package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderStatus;
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
public class OrderStatusJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderStatus orderStatus;

    @Bean
    public Job orderStatus(){
        return jobBuilderFactory.get("orderStatusJob")
                .start(orderStatusStep1())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    public Step orderStatusStep1(){
        return stepBuilderFactory.get("orderStatusStep1")
                .tasklet((contribution, chunkContext) -> {
                    //
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
