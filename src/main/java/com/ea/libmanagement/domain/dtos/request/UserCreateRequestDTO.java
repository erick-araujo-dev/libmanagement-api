package com.ea.libmanagement.domain.dtos.request;

public record UserCreateRequestDTO(String name, String email, String password, String role) {
}
