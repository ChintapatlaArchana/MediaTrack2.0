package com.cts.dto;

import com.cts.model.Plan.BillingCycle;
import com.cts.model.Plan.Status;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class PlanResponseDTO {
    private Long planId;
    private String name;
    private Double price;
    private BillingCycle billingCycle;
    private JsonNode entitlementsJSON;
    private Status status;
}
