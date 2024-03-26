package com.ea.libmanagement.application.services;

import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void Create
}
