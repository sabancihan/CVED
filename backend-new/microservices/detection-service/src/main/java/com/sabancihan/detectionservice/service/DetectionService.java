package com.sabancihan.detectionservice.service;


import com.sabancihan.detectionservice.dto.DetectionRequestDTO;
import com.sabancihan.detectionservice.dto.DetectionSoftwareVulnerabilityDTO;
import com.sabancihan.detectionservice.dto.VulnerabilityDTO;
import com.sabancihan.detectionservice.dto.VulnerabilityNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ModuleDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DetectionService   {

    private final StreamBridge streamBridge;

    //streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(new VulnerabilityNotificationDTO("CVE-2018-5461")).build());




    @Bean
    public Consumer<Message<DetectionRequestDTO>> detectionEventSupplier() {
        return message -> {
            DetectionRequestDTO request =  message.getPayload();;

            log.info("Detection request received {}", request);

            request.getVulnerabilities().forEach(
                    vulnerability -> {
                        var vulnerabilities = detectVulnerability(vulnerability.getUsedVersion(),vulnerability.getAffected_versions());



                        if (!vulnerabilities.isEmpty()) {
                            log.info("Vulnerabilities found sending notification email");
                            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(VulnerabilityNotificationDTO.builder()
                                    .ipAddress(request.getIpAddress())
                                    .email(request.getEmail())
                                    .softwareName(vulnerability.getSoftwareName())
                                    .vulnerableIds(vulnerabilities)
                                    .build()
                            ).build());
                        }
                    }
            );



        };
    }


    private Set<String> detectVulnerability(String usedVersionString, List<DetectionSoftwareVulnerabilityDTO> detectionSoftwareVulnerabilityDTOList) {
        ModuleDescriptor.Version usedVersion = ModuleDescriptor.Version.parse(usedVersionString);

        Set<String> affectedCVEs = new HashSet<>();


        detectionSoftwareVulnerabilityDTOList.forEach(
                detectionSoftwareVulnerabilityDTO -> {

                    String id = detectionSoftwareVulnerabilityDTO.getId();

                    detectionSoftwareVulnerabilityDTO.getAffected_versions().forEach(
                            affectedVersion -> {
                                var versionRange = affectedVersion.split(":");
                                if (versionRange.length == 2) {
                                    String min = versionRange[0];
                                    String max = versionRange[1];

                                    if (min.equals("null")) {

                                        if (max.equals("null")) {
                                            affectedCVEs.add(id);
                                        } else {
                                            if (usedVersion.compareTo(ModuleDescriptor.Version.parse(max)) <= 0) {
                                                affectedCVEs.add(id);
                                            }
                                        }
                                    } else {
                                        if (max.equals("null")) {
                                            if (usedVersion.compareTo(ModuleDescriptor.Version.parse(min)) >= 0) {
                                                affectedCVEs.add(id);
                                            }
                                        } else {
                                            if (usedVersion.compareTo(ModuleDescriptor.Version.parse(min)) >= 0 && usedVersion.compareTo(ModuleDescriptor.Version.parse(max)) <= 0) {
                                                affectedCVEs.add(id);
                                            }
                                        }




                                    }


                                } else {
                                    log.error("Invalid affected version range: {}", affectedVersion);
                                }
                            }
                    );






                }
        );

        return affectedCVEs;




    }


}
