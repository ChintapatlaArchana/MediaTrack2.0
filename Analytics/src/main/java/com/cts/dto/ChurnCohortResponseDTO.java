package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChurnCohortResponseDTO {
    private Long cohortId;
    private Long planId;
    private LocalDate startPeriod;

    private double retainedPct;

    private double churnedPct;
}
