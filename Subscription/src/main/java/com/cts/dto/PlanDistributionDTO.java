package com.cts.dto;

import com.cts.model.Plan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlanDistributionDTO {
    private String planName;
    private Plan.BillingCycle billingCycle;
    private Long count;

    public PlanDistributionDTO(String planName, Plan.BillingCycle billingCycle, Long count) {
        this.planName = planName;
        this.billingCycle = billingCycle;
        this.count = count;
    }
}