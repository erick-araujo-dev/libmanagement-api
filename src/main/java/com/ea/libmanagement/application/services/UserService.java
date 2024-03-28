package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.UserCreateRequestDTO;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import com.ea.libmanagement.shared.utils.FieldValidations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void CreateUser(UserCreateRequestDTO newUser){
        if (userRepository.findByEmail(newUser.email()) != null) {
            throw new BusinessException("Email já está sendo utilizado por outro usuário.");
        }

        fieldsValidate(newUser.name(), newUser.email(), newUser.password());

        String passwordEncoder = encodePassword(newUser.password());
        User user = new User(newUser);
        user.setPassword(passwordEncoder);
        userRepository.save(user);
    }

    private void fieldsValidate(String name, String email, String password){
        FieldValidations.validateName(name);
        FieldValidations.validateEmail(email);
        FieldValidations.validateStrongPassword(password);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
