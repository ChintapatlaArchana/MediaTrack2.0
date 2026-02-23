package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EntitlementRequestDTO {
//    private Long userId;
    private String contentScope;
    private LocalDate grantedDate;
    private LocalDate expiryDate;
}
