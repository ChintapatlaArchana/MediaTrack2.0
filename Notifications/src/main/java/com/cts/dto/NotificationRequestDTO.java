package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationRequestDTO {
//    private Long userId;
    private String message;
    private String category;
    private String status;
    private LocalDate createdDate;
}
