package com.ea.libmanagement.api.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController("book")
public class BookController {


    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserCreationDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setCreateDt(new Date());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
