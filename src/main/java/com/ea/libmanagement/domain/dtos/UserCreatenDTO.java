package com.ea.libmanagement.domain.dtos;

import lombok.Data;

@Data

public class UserCreatenDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
