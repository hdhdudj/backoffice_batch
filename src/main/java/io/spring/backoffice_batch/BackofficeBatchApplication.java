package io.spring.backoffice_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@SpringBootApplication
@EnableJpaRepositories("io.spring.main.jparepos.*")
@EntityScan("io.spring.main.model.*")
public class BackofficeBatchApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BackofficeBatchApplication.class, args);
        System.exit(SpringApplication.exit(SpringApplication.run(BackofficeBatchApplication.class, args)));
//        int exitCode = SpringApplication.exit(context);
//        System.exit(exitCode);
    }
}
