package com.cts.dto;

import com.cts.model.Entitlement.ContentScope;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EntitlementResponseDTO {
    private Long entitlementId;
    private Long userId;
    private ContentScope contentScope;
    private LocalDate grantedDate;
    private LocalDate expiryDate;
}
