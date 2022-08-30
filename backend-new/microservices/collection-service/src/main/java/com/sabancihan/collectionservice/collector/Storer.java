package com.sabancihan.collectionservice.collector;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.google.common.collect.Lists;
import com.sabancihan.collectionservice.model.Vulnerability;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Cluster;
import org.springframework.data.cassandra.core.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor

public class Storer {


    private final AsyncCassandraTemplate cassandraTemplate;

    private final CqlSession session;

    private final Lock lock = new ReentrantLock(true);




    private final String preparedStatement = "INSERT INTO vulnerability (id, vendor_name, software_name,affected_versions) VALUES (?, ?, ?,?)";


    private final Integer maxSize = 5000;

    public void store(List<Vulnerability> vulnerabilities) {

        if (vulnerabilities.isEmpty())
            return;



        PreparedStatement preparedStatement = session.prepare(this.preparedStatement);

        var boundStatements = vulnerabilities.stream().map(vulnerability -> preparedStatement.bind(
                        vulnerability.getId(),
                        vulnerability.getVendorName(),
                        vulnerability.getSoftwareName(),
                        vulnerability.getAffectedVersions())).


                toList();


        var loopCount = Math.ceilDiv(boundStatements.size(),maxSize);





        lock.lock();



        try {
            Lists.partition(boundStatements, loopCount).forEach(partition -> {
                session.execute(BatchStatement.newInstance(
                        BatchType.UNLOGGED,
                        partition.toArray(BoundStatement[]::new)));

            });




        }
        catch (Exception e) {
            e.printStackTrace();
        }


        finally {
                lock.unlock();
        }













    }
}
