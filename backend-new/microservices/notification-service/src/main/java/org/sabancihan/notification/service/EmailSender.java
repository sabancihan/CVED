package org.sabancihan.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSender {
    public void sendEmail(VulnerabilityDTO vulnerabilityDTO) throws InterruptedException {
        log.info("Sending Email notification for vulnerability - {}", vulnerabilityDTO.getId());
        Thread.sleep(100);
        log.info("Email Sent!!");
    }
}
