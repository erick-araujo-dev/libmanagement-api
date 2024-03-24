package com.ea.libmanagement.domain.dtos;

import com.ea.libmanagement.domain.enums.RoleEnum;
import lombok.Data;

@Data

public record UserCreateDTO(String username, String email, String password, RoleEnum role) {
}
