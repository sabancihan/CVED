package com.sabancihan.managementservice.repository;

import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
