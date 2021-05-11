package io.spring.backoffice_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BackofficeBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackofficeBatchApplication.class, args);
    }

}
