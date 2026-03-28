package com.cts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdDeliveryReportMetricsDTO {
    private int impressions;
    private double viewabilityRate;
    private double CTR;
    private double fillRate;
    @JsonProperty("eCPM") // This ensures it matches your database and design
    private double eCPM;

}
