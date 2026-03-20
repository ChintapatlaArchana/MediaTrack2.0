package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="churncohort")
public class ChurnCohort {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cohortId;

    private Long planId;

    private LocalDate startPeriod;

    private double retainedPct;

    private double churnedPct;

}
