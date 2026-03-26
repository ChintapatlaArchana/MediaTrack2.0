package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign")
@Data
public class Campaign {

//    public enum TargetingJSON { Geo, Device, Genre}

    public enum Pacing { Even, Asap }
    public enum Status { Planned, Active, Paused, Completed }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campaignId;

    private String name;
    private String advertiser;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;

    @Enumerated(EnumType.STRING)
    private Pacing pacing;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @Lob
//    private String targetingJSON;

//    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
//    private List<AdImpression> adImpression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="campaign_creativeId", nullable = false)
    private Creative creative;

}