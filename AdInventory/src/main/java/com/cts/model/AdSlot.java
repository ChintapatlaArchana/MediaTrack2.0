package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "adslot")
@Data
public class AdSlot {

    public enum Placement { PreRoll,MidRoll,PostRoll,Display }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long slotId;

    @Enumerated(EnumType.STRING)
    private Placement placement;

    private int duration;

    @Lob
    private String constraintsJson;

//    @OneToMany(mappedBy = "adSlot", cascade = CascadeType.ALL)
//    private List<AdImpression> adImpression;
}




