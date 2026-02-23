package com.cts.dto;

import jakarta.persistence.Lob;
import lombok.Data;
//import tools.jackson.databind.JsonNode;

@Data
public class PlanResponseDTO {
    private Long planId;
    private String name;
    private Double price;
    private String billingCycle;
    @Lob
    private String entitlementsJSON;
    private String status;
}
