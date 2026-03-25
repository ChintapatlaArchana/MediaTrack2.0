package com.cts.dto;

import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class PlanRequestDTO {
    private String name;
    private Double price;
    private String billingCycle;
    private String status;
    private JsonNode entitlements;

}
