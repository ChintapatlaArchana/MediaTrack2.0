package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "adimpression")
@Data
public class AdImpression {

    public enum Viewability { Viewable, NotViewable }
    public enum Clicked { Yes, No}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long impressionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adimpression_campaignId")
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adimpression_slotId")
    private AdSlot adSlot;

    private long sessionId;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private Viewability viewability;

    @Enumerated(EnumType.STRING)
    private Clicked clicked;

     // Representing Yes/No
}