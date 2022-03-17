package io.spring.backoffice_batch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableBatchProcessing
@SpringBootApplication
@EnableJpaRepositories("io.spring.main.jparepos.*")
@EntityScan("io.spring.main.model.*")
@MapperScan(basePackages = {"io.spring.main.mapper"})
public class BackofficeBatchApplication {

    private static final String PROPERTIES =
            "spring.config.location="
                    +"classpath:/application.properties"
                    +",classpath:/godourl.yml";
    public static void main(String[] args) {
//        InetAddress local;
//        try { local = InetAddress.getLocalHost(); String ip = local.getHostAddress(); System.out.println("local ip : "+ip); } catch (
//                UnknownHostException e1) { e1.printStackTrace(); }
//        SpringApplication.run(BackofficeBatchApplication.class, args);
        System.exit(SpringApplication
                .exit(
                        new SpringApplicationBuilder(BackofficeBatchApplication.class)
                                .properties(PROPERTIES)
                                .run(args)
//                        SpringApplication.run(BackofficeBatchApplication.class, args)
                ));
//        int exitCode = SpringApplication.exit(context);
//        System.exit(exitCode);
    }
}
