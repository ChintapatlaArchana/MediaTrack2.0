package com.cts.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdDeliveryReportResponseDTO {
    private Long adReportId;
    private Long campaignId;
    private com.cts.dto.AdDeliveryReportMetricsDTO adDeliveryReportMetrics;
    private LocalDate generatedDate;
}
