package com.cts.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AdDeliveryReportMetrics {
    private int Impressions;
    private double ViewabilityRate;
    private double CTR;
    private double FillRate;
    private double eCPM;
}
