package com.cts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AdDeliveryReportMetrics {
    private int impressions;
    @Column(name = "viewability_rate") // Match your MySQL snake_case
    private double viewabilityRate;

    @Column(name = "ctr") // Match your MySQL lowercase
    private double CTR;

    @Column(name = "fill_rate")
    private double fillRate;

    @Column(name = "ecpm")
    private double eCPM;
}
