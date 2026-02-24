package com.cts.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ingestjob")
public class IngestJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ingestId;

    private String sourceUri;

    private LocalDate submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private IngestStatus ingestStatus;

    private long assetId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="ingest_assetId")
//    private AssetResponseDTO assetResponseDTO;

    public enum IngestStatus {
        Queued, In_progress, Completed, Failed
    }


 public Long getAssetId() { return assetId; }
 public void setAssetId(Long assetId) { this.assetId = assetId; }



}