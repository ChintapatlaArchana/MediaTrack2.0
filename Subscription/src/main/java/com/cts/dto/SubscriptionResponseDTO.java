package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscriptionResponseDTO {
    private Long subscriptionId;
    private Long userId;
    private Long planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
