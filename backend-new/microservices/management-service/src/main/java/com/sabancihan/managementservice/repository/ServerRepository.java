package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {

    List<Server> findAllByUser_Username(String username);

    List<Server> findAll();

}

