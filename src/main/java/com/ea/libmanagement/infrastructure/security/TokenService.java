package com.ea.libmanagement.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ea.libmanagement.domain.entities.User;
import com.ea.libmanagement.infrastructure.configuration.SettingsHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final SettingsHash settingsHash;
    @Autowired
    public TokenService(SettingsHash settingsHash) {
        this.settingsHash = settingsHash;
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(settingsHash.getSecret());
            return JWT.create()
                    .withIssuer("libmanagement_api")
                    .withExpiresAt(expiresDate())
                    .withClaim("name", user.getName())
                    .withClaim("role", user.getRole().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    private Instant expiresDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(1);
        return expiration.toInstant(ZoneOffset.of("-03:00"));
    }
}
