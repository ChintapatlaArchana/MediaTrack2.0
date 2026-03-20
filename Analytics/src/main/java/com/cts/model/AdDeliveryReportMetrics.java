package com.cts.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AdDeliveryReportMetrics {
    private int impressions;
    private double viewabilityRate;
    private double CTR;
    private double fillRate;
    private double eCPM;
}
