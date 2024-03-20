package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.domain.dtos.UserCreationDTO;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;


@RestController
public class RegisterBookController {

    private final UserRepository userRepository;

    @Autowired
    public RegisterBookController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserCreationDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCreateDt(new Date());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}

