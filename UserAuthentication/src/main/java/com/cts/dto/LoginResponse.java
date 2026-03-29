package com.cts.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
    private String name;
    private String email;
    private long userId;

    // Constructor
    public LoginResponse(String token, String role, String name, String email, long userId) {
        this.token = token;
        this.role = role;
        this.name = name;
        this.email = email;
        this.userId = userId;
    }
}