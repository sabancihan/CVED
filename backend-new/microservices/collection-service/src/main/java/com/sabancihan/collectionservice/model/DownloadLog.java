package com.sabancihan.collectionservice.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class DownloadLog {


    @PrimaryKey
    private UUID time;








}
