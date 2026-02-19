package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titleId")
    private Title title;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private int duration;
    private String language;


    @ElementCollection
    @CollectionTable(name ="asset_subtitle_languages",joinColumns = @JoinColumn(name = "asset_id"))
    @Column(name = "subtitle_language")
    private List<String> subtitleLanguages;
    private LocalDate availabilityStart;
    private LocalDate availabilityEnd;

//    @OneToMany(mappedBy = "asset",cascade = CascadeType.ALL)
//    private List<ContentVersion> contentVersion;




    public enum AssetType {
        movie,
        episode,
        clip
    }
}
