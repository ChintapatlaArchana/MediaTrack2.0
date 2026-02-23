package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="addeliveryreport")
public class AdDeliveryReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long AdReportID;


    private Long campaignId;

    @Embedded
    private AdDeliveryReportMetrics adDeliveryReportMetrics;

    private LocalDate GeneratedDate;
}
