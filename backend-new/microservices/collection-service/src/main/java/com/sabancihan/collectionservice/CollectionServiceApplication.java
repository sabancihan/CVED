package com.sabancihan.collectionservice;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
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
    public PreparedStatement preparedStatement(CqlSession session) {
        final String selectStatement = "SELECT id,affected_versions FROM vulnerability WHERE vendor_name = ? AND software_name = ?";
        return session.prepare(selectStatement);
    }


}
