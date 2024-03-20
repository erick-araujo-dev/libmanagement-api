package com.ea.libmanagement.api.controllers;

import com.ea.libmanagement.application.services.LoginService;
import com.ea.libmanagement.domain.dtos.request.LoginRequestDTO;
import com.ea.libmanagement.domain.dtos.response.LoginResponseDTO;
import com.ea.libmanagement.infrastructure.security.TokenService;
import com.ea.libmanagement.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("login")
public class AuthenticationController {
    private final LoginService loginService;

    @Autowired
    public AuthenticationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO > authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO loginResponse = loginService.authenticate(loginRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}