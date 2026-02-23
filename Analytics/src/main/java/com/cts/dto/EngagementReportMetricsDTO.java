package com.cts.dto;

import lombok.Data;

@Data
public class EngagementReportMetricsDTO {
    private Long watchTimeSeconds; // or watchTimeMinutes, etc.
    private Long mau;              // Monthly Active Users
    private Long dau;              // Daily Active Users
    private Double completionRate; // e.g., 0.87 for 87%
    private Long avgBitrate;
}
