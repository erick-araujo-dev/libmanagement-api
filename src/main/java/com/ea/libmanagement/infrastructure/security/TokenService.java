package com.ea.libmanagement.infrastructure.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ea.libmanagement.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    public TokenService() {}

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("libmanagement_api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresDate())
                    .withClaim("name", user.getName())
                    .withClaim("role", user.getRole().toString())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("libmanagement_api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTCreationException exception) {
            return "";
        }
    }

    private Instant expiresDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusHours(8);
        return expiration.toInstant(ZoneOffset.of("-03:00"));
    }
}
