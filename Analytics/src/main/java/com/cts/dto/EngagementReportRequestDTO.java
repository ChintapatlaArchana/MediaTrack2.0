package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EngagementReportRequestDTO {
    private ScopeDTO scope;
    private com.cts.dto.EngagementReportMetricsDTO engagementReportMetrics;
    private LocalDate generatedDate;
}
