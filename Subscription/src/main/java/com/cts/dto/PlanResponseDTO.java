package com.cts.dto;

import com.cts.model.Plan.BillingCycle;
import com.cts.model.Plan.Status;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

@Data
public class PlanResponseDTO {
    private Long planId;
    private String name;
    private Double price;
    private BillingCycle billingCycle;

    @JsonRawValue
    private String entitlementsJSON;

    private Status status;
}
