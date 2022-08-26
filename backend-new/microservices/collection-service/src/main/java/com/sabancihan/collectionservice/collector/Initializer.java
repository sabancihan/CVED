package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.collector.Downloader;
import com.sabancihan.collectionservice.collector.Parser;
import com.sabancihan.collectionservice.collector.Storer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class Initializer implements CommandLineRunner {

    private final Integer startYear = 2002;
    private final Integer endYear = LocalDateTime.now().getYear();

    private final String templateUrl = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-%s.json.gz";

    private final Downloader downloader;
    private final Parser parser;
    private final Storer storer;

    @Override
    public void run(String... args) throws Exception {



            CompletableFuture.runAsync(() -> {

                IntStream years = IntStream.rangeClosed(startYear, endYear);

                var processArray =  years.mapToObj(year -> processVulnerabilitySet(String.format(templateUrl, year))).toArray(CompletableFuture[]::new);


                var allProcesses =  CompletableFuture.allOf(processArray);

                try {
                    allProcesses.get();
                    log.info("Vulnerabilities downloaded and saved");

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);

                }

            });

        }

    private CompletableFuture<Void> processVulnerabilitySet(String format) {

        return CompletableFuture.runAsync(() -> {
           try {
                downloader.download(format);
                parser.parse();
                storer.store();
           }
              catch (Exception e) {
                log.error("Error downloading: {}", format);
              }
        });

    }

}

