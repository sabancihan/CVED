package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.mapstruct.Qualifiers;
import com.sabancihan.managementservice.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {

    List<Server> findAllByUser_Username(String username);


    Optional<Server> findAnnotatedById(UUID id);

}

