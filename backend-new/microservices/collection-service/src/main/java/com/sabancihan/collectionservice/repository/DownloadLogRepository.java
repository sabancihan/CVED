package com.sabancihan.collectionservice.repository;

import com.sabancihan.collectionservice.model.DownloadLog;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface DownloadLogRepository extends CassandraRepository<DownloadLog, String> {


    Optional<DownloadLog> findTopBy();
}
