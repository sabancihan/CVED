package org.sabancihan.notification.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import java.util.function.Consumer;

@SpringBootApplication
@EnableEurekaClient
public class NotificationServiceApplication {

        public static void main(String[] args) {
            SpringApplication.run(NotificationServiceApplication.class, args);
        }



    @Bean
    public SimpleMailMessage sprintTemplateMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Dikkat:\n%s ipli serverinizdeki %s uygulamasında %s açığı bulunmuştur\n");
        return message;
    }


}



