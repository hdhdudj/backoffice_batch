package io.spring.backoffice_batch.job;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.apis.XmlSave;
import io.spring.main.model.goods.entity.IfGoodsMaster;
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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class OrderSearchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderSearch orderSearch;
    private final XmlSave xmlSave;


    @Bean
    public Job searchGoodsJob(){
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
                    //
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step searchOrderStep2(){
        return stepBuilderFactory.get("searchOrderStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("----- This is searchOrderStep2");
                    //
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
