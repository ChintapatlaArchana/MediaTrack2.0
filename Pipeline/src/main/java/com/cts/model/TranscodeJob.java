package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transcodejob")
public class TranscodeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transcodeId;

    private Long assetId;

    private String profile; // 1080p, 720p, Adaptive

    private LocalDate startedDate;
    private LocalDate completedDate;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TranscodeStatus transcodeStatus;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="ingest_assetId")
//    private AssetResponseDTO assetResponseDTO;

    public enum TranscodeStatus {
        Queued, In_progress, Completed, Failed
    }



}