package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.application.services.UserService;
import com.ea.libmanagement.domain.dtos.UserCreateDTO;
import com.ea.libmanagement.domain.dtos.request.LoginRequestDTO;
import com.ea.libmanagement.domain.dtos.response.LoginResponseDTO;
import com.ea.libmanagement.domain.entities.User;
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

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro de negócio: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userDTO) {
        try {
            userService.CreateUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de negócio: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }
}
