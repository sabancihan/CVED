package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SoftwareVersionedRepository extends JpaRepository<SoftwareVersioned, UUID> {

    List<SoftwareVersioned> findAllByServer_id(UUID server_id);

}

