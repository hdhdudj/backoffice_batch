package io.spring.backoffice_batch.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.spring.backoffice_batch.util.UniqueRunIdIncrementer;
import io.spring.main.apis.OrderSearch;
import io.spring.main.model.order.entity.IfOrderMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(value = "io.spring.main.apis")
public class TestJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;
	private final OrderSearch orderSearch;

	private static final int chunkSize = 100;

	@Bean
	public Job testJob() {
		

		
		return jobBuilderFactory.get("testJob12").start(testStep1())
				// .incrementer(new UniqueRunIdIncrementer())
				.incrementer(new UniqueRunIdIncrementer())
				.build();

	}

	/**
	 * step1에서 저장한 if 테이블에서 한 줄씩 가져와 trdst DB에 쪼개 넣는 step
	 */
	@Bean
	public Step testStep1() {
		log.info("----- This is testStep1");

		return stepBuilderFactory.get("testStep1").<IfOrderMaster, IfOrderMaster>chunk(chunkSize)
				.reader(testReader())
				// .processor(testprocessor())
				.writer(testWriter())
				.build();
	}

    @Bean
	public JpaCustomPagingItemReader testReader() {
		JpaCustomPagingItemReader<IfOrderMaster> jpaPagingItemReader = new JpaCustomPagingItemReader<IfOrderMaster>() {
            @Override
            public int getPage() {
                return 0;
			}



        };
		jpaPagingItemReader.setName("testReader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(chunkSize);
		jpaPagingItemReader.setQueryString("SELECT i FROM IfOrderMaster i where i.ifStatus='01'");
        return jpaPagingItemReader;
	}

	@Bean
	public JpaItemWriter testWriter() {
		JpaItemWriter jpaItemWriter = new JpaItemWriter();
		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaItemWriter;
	}

}
