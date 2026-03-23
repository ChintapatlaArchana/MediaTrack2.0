package com.cts.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AdDeliveryReportRequestDTO
{
    private Long campaignId;
    private com.cts.dto.AdDeliveryReportMetricsDTO adDeliveryReportMetrics;
    private LocalDate generatedDate;
}
