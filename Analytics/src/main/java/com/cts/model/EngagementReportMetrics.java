package com.cts.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EngagementReportMetrics {

    private Long watchTimeSeconds; // or watchTimeMinutes, etc.
    private Long mau;              // Monthly Active Users
    private Long dau;              // Daily Active Users
    private Double completionRate; // e.g., 0.87 for 87%
    private Long avgBitrate;

}
