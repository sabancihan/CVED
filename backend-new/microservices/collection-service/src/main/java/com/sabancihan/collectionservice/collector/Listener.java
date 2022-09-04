package com.sabancihan.collectionservice.collector;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class Listener {

    private final TaskScheduler taskScheduler;
    private final DownloadLogRepository downloadLogRepository;

    private final Requester requester;

    public final Integer waitingTime = 1000 * 60 * 60;






    public void scheduleRest() {



            taskScheduler.scheduleAtFixedRate(() -> {
                Optional<DownloadLog> lastDownloadLog = downloadLogRepository.findTopByOrderByTimeDesc();

                lastDownloadLog.ifPresent(downloadLog -> {

                    try {
                        log.info("Scheduled task started");

                        long timeStamp =  Uuids.unixTimestamp(downloadLog.getTime());



                        requester.handleRestRequest(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault()));

                        log.info("Scheduled task finished");

                    } catch (UnsupportedEncodingException e) {
                        log.error("Scheduled task failed");

                        throw new RuntimeException(e);
                    }
                });



            }, waitingTime);



        }










    }




