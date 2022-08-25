package com.sabancihan.collectionservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CollectionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectionServiceApplication.class, args);
    }
}
