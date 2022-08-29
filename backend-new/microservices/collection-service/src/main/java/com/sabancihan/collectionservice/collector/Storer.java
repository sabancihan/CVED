package com.sabancihan.collectionservice.collector;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.core.session.Session;
import com.datastax.oss.protocol.internal.request.Prepare;
import com.sabancihan.collectionservice.model.Vulnerability;
import com.sabancihan.collectionservice.repository.VulnerabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.*;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class Storer {


    private final AsyncCassandraTemplate cassandraTemplate;

    private final CqlSession session;


    private final String preparedStatement = "INSERT INTO vulnerability (id, vendor_name, software_name, affectedversions) VALUES (?, ?, ?, ?)";


    public void store(List<Vulnerability> vulnerabilities) {


        vulnerabilities.forEach(cassandraTemplate::insert);
    }
}
