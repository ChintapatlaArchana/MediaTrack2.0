package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;

    // Constructor, Getters, and Setters
    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}