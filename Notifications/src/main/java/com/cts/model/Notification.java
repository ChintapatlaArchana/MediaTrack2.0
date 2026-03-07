package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

//    @JsonIgnoreProperties("notification")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "notification_userId", nullable = false)
    private Long userId;

    private String message;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdDate;

    public enum Category {
        Subscription,Content,Delivery,AdOps
    }

    public enum Status {
        Unread,Read,Dismissed
    }
}
