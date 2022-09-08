package com.sabancihan.managementservice.service;

import com.sabancihan.managementservice.model.User;
import com.sabancihan.managementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        log.info("Getting all users");

        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        log.info("Getting user with username {}", username);

        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

    }
}
