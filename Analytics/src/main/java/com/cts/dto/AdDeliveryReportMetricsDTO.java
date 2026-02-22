package com.cts.dto;

import lombok.Data;

@Data
public class AdDeliveryReportMetricsDTO {
    private int Impressions;
    private double ViewabilityRate;
    private double CTR;
    private double FillRate;
    private double eCPM;
}
