package com.cts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanDistributionDTO {
    private String planName;
    private String billingCycle;
    private Long count;

    public PlanDistributionDTO(String planName, String billingCycle, Long count) {
        this.planName = planName;
        this.billingCycle = billingCycle;
        this.count = count;
    }
}