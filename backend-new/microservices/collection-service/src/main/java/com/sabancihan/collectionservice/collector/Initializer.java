package com.sabancihan.collectionservice.collector;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.model.Vulnerability;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import com.sabancihan.collectionservice.repository.VulnerabilityRepository;
import com.sabancihan.collectionservice.service.VulnerabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class Initializer implements CommandLineRunner {

    private final VulnerabilityRepository vulnerabilityRepository;

    private final Integer startYear = 2002;
    private final Integer endYear = ZonedDateTime.now().getYear();

    private final String templateUrl = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-%s.%s";




    private final Downloader downloader;
    private final Parser parser;
    private final Storer storer;

    private final Listener listener;

    private final Requester requester;

    private final DownloadLogRepository downloadLogRepository;


    private final VulnerabilityService vulnerabilityService;

    public ZonedDateTime initialize() {

        ZonedDateTime lastModified = ZonedDateTime.now();


        List<String> years = IntStream.rangeClosed(startYear, endYear).mapToObj(String::valueOf).collect(Collectors.toList());

        var recent = Arrays.asList("modified", "recent");




        try {
            lastModified = requester.lastModified(templateUrl, recent);
        } catch (MalformedURLException e) {
            lastModified = ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
        }

        years.add("recent");


        var processArray = years.stream().map(year -> processVulnerabilitySet(String.format(templateUrl, year, "json.gz"))).toArray(CompletableFuture[]::new);


        var allProcesses = CompletableFuture.allOf(processArray);


        try {
            allProcesses.get();
            processVulnerabilitySet(String.format(templateUrl, "modified", "json.gz")).get();
            log.info("Vulnerabilities downloaded and saved");
            downloadLogRepository.insert(

                    DownloadLog.builder()
                            .time(Uuids.startOf(lastModified.toInstant().toEpochMilli()))

                            .build());

            vulnerabilityService.handleInitUpdates();


        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);

        }

        return lastModified;
    }

    public ZonedDateTime handleInitialized() {

        Optional<DownloadLog>  lastUpdate = downloadLogRepository.findTopByOrderByTimeDesc();

        if (lastUpdate.isPresent()) {
            log.info("Database is already initialized");
            long timeStamp =  Uuids.unixTimestamp(lastUpdate.get().getTime());

            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timeStamp), ZoneId.systemDefault());

        }

        else {
            log.info("There is no update log in database. Initializing database");
            vulnerabilityRepository.deleteAll();
            return initialize();
        }





    }

    @Override
    public void run(String... args) throws Exception {






            CompletableFuture.runAsync(() -> {

                ZonedDateTime lastModified = vulnerabilityRepository.findTopBy().isPresent() ? handleInitialized() : initialize();


                CompletableFuture.runAsync(listener::scheduleRest);


            });



        }

    private CompletableFuture<Void> processVulnerabilitySet(String format) {

        return CompletableFuture.runAsync(() -> {
           try {
                VulnMapping jsonVulnerabilities = downloader.download(format);
                List<Vulnerability> vulnerabilities =  parser.parse(jsonVulnerabilities);
                storer.store(vulnerabilities);
           }
              catch (Exception e) {
                log.error("Error Collecting vulnerabilities: {}", format);

              }
        });

    }

}

