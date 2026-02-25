package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "entitlement")
public class Entitlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long entitlementId;

    @Column(name = "entitlement_userId", nullable = false)
    private long userId;

    @Enumerated(EnumType.STRING)
    private ContentScope contentScope;

    private LocalDate grantedDate;
    private LocalDate expiryDate;

    public enum ContentScope {
        All,Category,Title
    }
}
