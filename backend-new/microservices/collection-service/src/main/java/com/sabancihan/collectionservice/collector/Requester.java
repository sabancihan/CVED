package com.sabancihan.collectionservice.collector;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabancihan.collectionservice.dto.*;
import com.sabancihan.collectionservice.mapper.VulnerabilityMapper;
import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.model.Vulnerability;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional

public class Requester {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RestResponse {
        public VulnMapping result;
        public Integer resultsPerPage;
        public Integer totalResults;
    }


    public final Integer maxResult = 2000;

    @Value("${nvd.nist.api.key}")
    private  String apiKey;


    private final StreamBridge streamBridge;


    private final ObjectMapper objectMapper;

    private final WebClient.Builder webClientBuilder;
    private final DownloadLogRepository downloadLogRepository;


    private final VulnerabilityMapper vulnerabilityMapper;
    private final Parser parser;
    private final Storer storer;



    public List<VulnMapping> getRestRequest(ZonedDateTime lastModified) throws UnsupportedEncodingException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS z");

        String restUrl = String.format("https://services.nvd.nist.gov/rest/json/cves/1.0/?apiKey=%s&resultsPerPage=%d", apiKey, maxResult);



        //Convert DateTime to pattern yyyy-MM-dd'T'HH:mm:ss:SSS z





        String lastModifiedString = lastModified.format(formatter);
        String nowString = ZonedDateTime.now().format(formatter);




        //List of map entries
        List<Map<String, String>> parameterList = Arrays.asList(
                Map.ofEntries(
                        Map.entry("sortBy", "publishDate"),
                        Map.entry("pubStartDate", URLEncoder.encode(lastModifiedString, StandardCharsets.UTF_8)),
                        Map.entry("pubEndDate", URLEncoder.encode(nowString, StandardCharsets.UTF_8))
                ),
                Map.ofEntries(
                        Map.entry("sortBy", "modifiedDate"),
                        Map.entry("modStartDate", URLEncoder.encode(lastModifiedString, StandardCharsets.UTF_8)),
                        Map.entry("modEndDate", URLEncoder.encode(nowString, StandardCharsets.UTF_8))
                )
        );


       List<VulnMapping>  mappings =  parameterList.stream().map(parameterMap -> {



            try {
                URL url = new URL(String.format("%s&%s", restUrl, parameterMap.entrySet().stream().map
                                    (entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                                    .reduce((a, b) -> String.format("%s&%s", a, b)).orElseThrow()));

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                if (con.getResponseCode() == HttpStatus.SC_OK) {
                    log.info("Vulnerabilities pulled");
                    return objectMapper.readValue(con.getInputStream(), RestResponse.class).result;
                } else {
                    log.error("Vulnerabilities could not be pulled");
                    return null;
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).toList();


       if (mappings.size() == parameterList.size()) {
           return mappings;
       } else {
           return null;

       }
    }

    public void  handleRestRequest(ZonedDateTime lastModified) throws UnsupportedEncodingException {
        ZonedDateTime now = ZonedDateTime.now();
        var vulnMapping = getRestRequest(lastModified);
        if (vulnMapping != null) {

            var vulnList =  vulnMapping.stream().map(parser::parse).toList();
            vulnList.forEach(storer::store);







            log.info("Vulnerabilities from rest api downloaded and saved");
            downloadLogRepository.insert(
                    DownloadLog.builder()
                            .time(Uuids.timeBased())
                            .build());

            //flatting the list
            var vulnListFlat = vulnList.stream().flatMap(Collection::stream).collect(Collectors.groupingBy(
                    Vulnerability::getVendorName,Collectors.groupingBy(Vulnerability::getSoftwareName)
            ));

            List<SoftwareId> uniqueIds = vulnListFlat.keySet().stream().flatMap(vendorName -> {
                var softwareNames = vulnListFlat.get(vendorName).keySet();
                return softwareNames.stream().map(softwareName -> SoftwareId.builder()
                        .vendor_name(vendorName)
                        .product_name(softwareName)
                        .build());

            }).toList();

            if (uniqueIds.size()  == 0) {
                return;
            }

            List<ManagementUpdateDTO> managementUpdateDTOs = webClientBuilder.build()
                    .post()
                    .uri("http://management-service/api/management/softwareVersioned/software/ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(uniqueIds))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ManagementUpdateDTO>>() {
                    })
                    .block();

            if (managementUpdateDTOs == null || managementUpdateDTOs.size() == 0) {
                return;
            }


            managementUpdateDTOs.forEach(managementUpdateDTO -> {
                streamBridge.send("detectionEventSupplier-out-0", MessageBuilder.withPayload(

                        DetectionRequestDTO.builder()
                                .email(managementUpdateDTO.getEmail())
                                .ipAddress(managementUpdateDTO.getIpAddress())
                                .vulnerabilities(managementUpdateDTO.getVulnerabilities().stream().map(
                                        vulnerability -> DetectionVulnerabiltiyDTO.builder()
                                                .vendorName(vulnerability.getVendorName())
                                                .softwareName(vulnerability.getSoftwareName())
                                                .usedVersion(vulnerability.getVersion())
                                                .affected_versions(vulnListFlat.get(vulnerability.getVendorName()).get(vulnerability.getSoftwareName()).stream().map(
                                                        vulnerabilityMapper::vulnerabilityToSoftwareVulnerabilityDTO
                                                ).toList())
                                                .build()
                                ).toList())
                                .build()));            });


        }
    }

    public void handleInitializeRequest() {
    }







    public ZonedDateTime lastModified(String templateURL, List<String> recentUpdates) throws MalformedURLException {


        return recentUpdates.stream().map(recentUpdate -> {
            String urlString = String.format(templateURL, recentUpdate, "meta");
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                if (con.getResponseCode() == HttpStatus.SC_OK) {

                    var  br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    var body = br.readLine();


                    var properties = body.split("Date:");

                    if (properties[0].equals("lastModified")) {
                        return ZonedDateTime.parse(properties[1]);
                    }

                }

                return ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());

            } catch (IOException e) {
                e.printStackTrace();
                return  ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
            }





        }).min(ZonedDateTime::compareTo).orElseGet(ZonedDateTime::now);






    }


}
