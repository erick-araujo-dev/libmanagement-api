package com.ea.libmanagement.application.services;

import com.ea.libmanagement.domain.dtos.request.LoginRequestDTO;
import com.ea.libmanagement.domain.dtos.response.LoginResponseDTO;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import com.ea.libmanagement.infrastructure.security.TokenService;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthorizationService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        String email = request.email();
        String password = request.password();

        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null || !verifyPassword(password, user.getPassword())) {
            throw new BusinessException("Usuário ou senha inválidos");
        }

        String token = tokenService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    private boolean verifyPassword(String typedPassword, String storedPassword) {
        return typedPassword.equals(storedPassword);
    }
}
