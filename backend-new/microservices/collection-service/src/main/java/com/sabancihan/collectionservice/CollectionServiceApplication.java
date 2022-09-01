package com.sabancihan.collectionservice;


import com.datastax.oss.driver.api.core.CqlSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.function.Consumer;

@SpringBootApplication

@EnableEurekaClient
@Slf4j
@EnableScheduling

public class CollectionServiceApplication {

    @Bean
    AsyncCassandraTemplate asyncCassandraTemplate(CqlSession session) {
        return new AsyncCassandraTemplate(session);
    }
    public static void main(String[] args) {
        SpringApplication.run(CollectionServiceApplication.class, args);
    }


    @Bean
    public Consumer<Message<Object>> managementUpdateEventSupplier() {
        return message -> {
            try {
                log.info("Received message: {}", message.getPayload());
            } catch (Exception e) {
                throw new RuntimeException("Something went wrong while receiving data");
            }
        };
    }
}
