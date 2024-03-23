package com.ea.libmanagement.domain.dtos;

import com.ea.libmanagement.domain.enums.RoleEnum;
import lombok.Data;

@Data

public class UserCreatenDTO {
    private String username;
    private String email;
    private String password;
    private RoleEnum role;
}
