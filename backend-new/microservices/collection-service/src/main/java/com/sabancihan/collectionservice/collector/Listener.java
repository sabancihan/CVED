package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.model.DownloadLog;
import com.sabancihan.collectionservice.repository.DownloadLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;


import java.util.Optional;

@RequiredArgsConstructor
public class Listener {

    private final TaskScheduler taskScheduler;
    private final DownloadLogRepository downloadLogRepository;

    private final Requester requester;

    public final Integer waitingTime = 1000 * 60 * 60;






    public void scheduleRest() {



            taskScheduler.scheduleAtFixedRate(() -> {
                Optional<DownloadLog> lastDownloadLog = downloadLogRepository.findTopBy();

                lastDownloadLog.ifPresent(downloadLog -> requester.handleRestRequest(downloadLog.getDate()));



            }, waitingTime);



        }










    }




