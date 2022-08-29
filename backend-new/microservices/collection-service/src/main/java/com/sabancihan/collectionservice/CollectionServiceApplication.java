package com.sabancihan.collectionservice;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.session.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling


public class CollectionServiceApplication {

    @Bean
    AsyncCassandraTemplate asyncCassandraTemplate(CqlSession session) {
        return new AsyncCassandraTemplate(session);
    }
    public static void main(String[] args) {
        SpringApplication.run(CollectionServiceApplication.class, args);
    }
}
