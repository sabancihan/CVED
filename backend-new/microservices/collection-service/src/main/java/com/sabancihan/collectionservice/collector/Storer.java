package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.model.Vulnerability;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Storer {

        private final CassandraTemplate cassandraTemplate;

        public void store(List<Vulnerability> vulnerabilities) {
                CassandraBatchOperations batchOps = cassandraTemplate.batchOps();
                batchOps.insert(vulnerabilities);
                batchOps.execute();

        }
}
