package com.cts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdDeliveryReportMetricsDTO {
    private int Impressions;
    private double ViewabilityRate;
    private double CTR;
    private double FillRate;
    @JsonProperty("eCPM") // This ensures it matches your database and design
    private double eCPM;

}
