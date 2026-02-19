package com.cts.dto;

import com.cts.model.User.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private Role role;
    private String email;
    private String phone;
}
