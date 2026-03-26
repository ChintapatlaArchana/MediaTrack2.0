package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {
    private long totalImpressions;
    private double averageCTR;
    private double averageECPM;
    private long activeCampaigns;
}