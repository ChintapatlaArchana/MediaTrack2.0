package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mediapackage")
public class MediaPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    private Long assetId;

    private String format; // HLS, DASH
    private String drm;    // Widevine, FairPlay, etc.
    private String cdnPath;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private QCStatus qcStatus;


    public enum QCStatus {
        Pending, Passed, Failed
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="ingest_assetId")
//    private AssetResponseDTO assetResponseDTO;

}