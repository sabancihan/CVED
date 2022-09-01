package com.sabancihan.detectionservice.service;


import com.sabancihan.detectionservice.dto.VulnerabilityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DetectionService implements CommandLineRunner {

    private final StreamBridge streamBridge;

        public boolean detectVulnerability() {
            boolean isVulnerable = Math.random() > 0.5;

            if (isVulnerable) {
                log.info("Vulnerability detected");
                streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(new VulnerabilityDTO("CVE-2018-5461")).build());
            }

            return isVulnerable;

        }

    @Override
    public void run(String... args) throws Exception {
        while (!detectVulnerability()) {
            log.info("No vulnerability detected");


        }
    }
}
