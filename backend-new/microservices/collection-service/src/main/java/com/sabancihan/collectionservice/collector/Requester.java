package com.sabancihan.collectionservice.collector;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor

public class Requester {


    public final Integer maxResult = 2000;

    @Value("${nvd.nist.api.key}")
    private String apiKey;

    public final String restUrl = String.format("https://services.nvd.nist.gov/rest/json/cves/1.0/?apiKey=%s&resultsPerPage=%d", apiKey, maxResult);


    private final ObjectMapper objectMapper;

    private final DownloadLogRepository downloadLogRepository;


    private final Parser parser;
    private final Storer storer;



    public List<VulnMapping> getRestRequest(LocalDateTime lastModified) {
        String now = LocalDateTime.now().toString();

        //List of map entries
        List<Map<String, String>> parameterList = Arrays.asList(
                Map.ofEntries(
                        Map.entry("sortBy", "publishDate"),
                        Map.entry("pubStartDate", lastModified.toString()),
                        Map.entry("pubEndDate", now)
                ),
                Map.ofEntries(
                        Map.entry("sortBy", "modifiedDate"),
                        Map.entry("modStartDate", lastModified.toString()),
                        Map.entry("modEndDate", now)
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
                    log.info("Vulnerabilities downloaded from {}", restUrl);
                    return objectMapper.readValue(con.getInputStream(), VulnMapping.class);
                } else {
                    log.error("Vulnerabilities could not be downloaded from {}", restUrl);
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

    public void  handleRestRequest(LocalDateTime lastModified) {
        LocalDateTime now = LocalDateTime.now();
        var vulnMapping = getRestRequest(lastModified);
        if (vulnMapping != null) {

            vulnMapping.stream().map(parser::parse).forEach(storer::store);

            log.info("Vulnerabilities from rest api downloaded and saved");
            downloadLogRepository.insert(
                    DownloadLog.builder()
                            .id(UUID.randomUUID().toString())
                            .date(now)
                            .build());
        }
    }







    public LocalDateTime lastModified(String templateURL, List<String> recentUpdates) throws MalformedURLException {


        return recentUpdates.stream().map(recentUpdate -> {
            String urlString = String.format(templateURL, recentUpdate, "meta");
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                if (con.getResponseCode() == HttpStatus.SC_OK) {
                    var body = con.getResponseMessage();

                    var properties = body.split(":");

                    if (properties[0].equals("lastModifiedDate")) {
                        return LocalDateTime.parse(properties[1]);
                    }

                }

                return LocalDateTime.MIN;

            } catch (IOException e) {
                e.printStackTrace();
                return  LocalDateTime.MIN;
            }




        }).min(LocalDateTime::compareTo).orElseGet(LocalDateTime::now);






    }


}
