package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="engagementreport")
public class EngagementReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;

    @Embedded
    private Scope scope;

    @Embedded
    private EngagementReportMetrics engagementReportMetrics;

    private LocalDate generatedDate;

}
