package com.cts.dto;

import com.cts.model.User.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private String status;
}

