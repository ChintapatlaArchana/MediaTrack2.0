package com.cts.dto;


import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String phone;
}
