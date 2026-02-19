package com.cts.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "contentversion")
public class ContentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentversion_assetId")
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private VersionLabel versionLabel;

    private String notes;

    public enum VersionLabel {
        directorCut,
        localized,
        edited
    }
}
