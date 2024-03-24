package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterBookController {

    private final UserRepository userRepository;

    @Autowired
    public RegisterBookController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}

