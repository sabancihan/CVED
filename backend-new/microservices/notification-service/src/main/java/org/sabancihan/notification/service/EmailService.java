package org.sabancihan.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

@Service
@Slf4j
@Configuration
@RequiredArgsConstructor

public class EmailService {
    private final JavaMailSender javaMailSender;

    private final SimpleMailMessage templateMessage;


    @Bean
    public Consumer<Message<VulnerabilityNotificationDTO>> notificationEventSupplier() {
        return message -> {
            try {
                sendEmail(message.getPayload());
            } catch (InterruptedException e) {
                throw new RuntimeException("Something went wrong while sending email");
            }
        };
    }



    @Value("${spring.mail.username}")
    private  String username;


    public void sendSimpleMessage(
            String[] to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendEmail(VulnerabilityNotificationDTO vulnerabilityNotificationDTO) throws InterruptedException {
        String allIds = String.join(",",vulnerabilityNotificationDTO.getVulnerableIds());
        log.info("Sending Email notification for vulnerability - {}", allIds);
        String emailText = String.format(Objects.requireNonNull(templateMessage.getText()),vulnerabilityNotificationDTO.getIpAddress() ,vulnerabilityNotificationDTO.getSoftwareName(), allIds);

        sendSimpleMessage(new String[]{vulnerabilityNotificationDTO.getEmail()}, "Uygulam??n??zda A????k var", emailText);


        log.info("Email Sent!!");
    }
}
