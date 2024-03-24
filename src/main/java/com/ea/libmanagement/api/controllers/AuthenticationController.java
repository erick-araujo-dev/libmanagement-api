package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.application.services.AuthorizationService;
import com.ea.libmanagement.domain.dtos.UserCreateDTO;
import com.ea.libmanagement.domain.dtos.request.LoginRequestDTO;
import com.ea.libmanagement.domain.dtos.response.LoginResponseDTO;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.repositories.UserRepository;
import com.ea.libmanagement.infrastructure.security.TokenService;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthorizationService loginService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO > login(@RequestBody LoginRequestDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());


            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userDTO) {
        User user = new User();
        user.setName(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(userDTO.role());
        user.setCreateDt(new Date());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
