package io.spring.backoffice_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@EnableBatchProcessing
@SpringBootApplication
public class BackofficeBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackofficeBatchApplication.class, args);
    }

}
