package com.cts.dto;

import com.cts.model.Notification.Category;
import com.cts.model.Notification.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationResponseDTO {

    private Long notificationId;
    private Long userId;
    private String message;
    private Category category;
    private Status status;
    private LocalDate createdDate;
}
