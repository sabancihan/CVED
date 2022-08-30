package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SoftwareRepository extends JpaRepository<Software, SoftwareId> {



}
