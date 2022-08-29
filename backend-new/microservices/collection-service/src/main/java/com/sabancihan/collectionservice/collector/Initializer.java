package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.collector.Downloader;
import com.sabancihan.collectionservice.collector.Parser;
import com.sabancihan.collectionservice.collector.Storer;
import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.model.Vulnerability;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import com.sabancihan.collectionservice.repository.VulnerabilityRepository;
import jnr.constants.platform.Local;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final Integer endYear = LocalDateTime.now().getYear();

    private final String templateUrl = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-%s.%s";




    private final Downloader downloader;
    private final Parser parser;
    private final Storer storer;

    private final Listener listener;

    private final Requester requester;

    private final DownloadLogRepository downloadLogRepository;


    public LocalDateTime initialize() {

        LocalDateTime lastModified = LocalDateTime.now();


        List<String> years = IntStream.rangeClosed(startYear, endYear).mapToObj(String::valueOf).collect(Collectors.toList());

        var recent = Arrays.asList("modified", "recent");




        try {
            var lastModifiedRecent = requester.lastModified(templateUrl, recent);
            lastModified = lastModifiedRecent == LocalDateTime.MIN ? LocalDateTime.now() : lastModifiedRecent;
        } catch (MalformedURLException e) {
            lastModified = LocalDateTime.now();
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
                            .id(UUID.randomUUID().toString())
                            .date(lastModified)

                            .build());


        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);

        }

        return lastModified;
    }

    public LocalDateTime handleInitialized() {

        Optional<DownloadLog>  lastUpdate = downloadLogRepository.findTopBy();

        if (lastUpdate.isPresent()) {
            log.info("Database is already initialized");
            return lastUpdate.get().getDate();
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

                LocalDateTime lastModified = vulnerabilityRepository.findTopBy().isPresent() ? handleInitialized() : initialize();


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

