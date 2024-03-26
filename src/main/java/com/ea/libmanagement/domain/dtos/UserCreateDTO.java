package com.ea.libmanagement.domain.dtos;

import com.ea.libmanagement.domain.enums.RoleEnum;
import lombok.Data;
public record UserCreateDTO(String name, String email, String password, String role) {
}
