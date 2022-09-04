package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SoftwareVersionedRepository extends JpaRepository<SoftwareVersioned, UUID> {

    List<SoftwareVersioned> findAllByServer_id(UUID server_id);


    @EntityGraph(value = "SoftwareVersioned.withDetailedServer", type = EntityGraph.EntityGraphType.FETCH)
    List<SoftwareVersioned> findAllBySoftware_idIn(Set<SoftwareId> software_id);

}

