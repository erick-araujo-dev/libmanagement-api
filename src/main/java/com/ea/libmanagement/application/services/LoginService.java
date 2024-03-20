package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.LoginRequestDTO;
import com.ea.libmanagement.domain.dtos.response.LoginResponseDTO;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import com.ea.libmanagement.infrastructure.security.TokenService;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public LoginService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null || !verifyPassword(password, user.getPassword())) {
            throw new BusinessException("Usuário ou senha inválidos");
        }

        String token = tokenService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setName(user.getName());
        response.setRole(user.getRole());


        return response;
    }

    private boolean verifyPassword(String typedPassword, String storedPassword) {
        return typedPassword.equals(storedPassword);
    }
}
