package com.cts.model;
import com.cts.dto.AssetResponseDTO;
import com.cts.dto.UserResponseDTO;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="playbacksession")
public class PlaybackSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId; // Primary Key


    private Long userId; // Foreign Key to Device table



    private Long assetId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double bitrateAvg;
    private int bufferEvents;

    @Enumerated(EnumType.STRING)
    private Status status;

//   Relationship: One Session can have multiple DRM checks
//    @OneToMany(mappedBy = "playbackSession", cascade = CascadeType.ALL)
//    private List<DRMEvent> drmEvent;
//
//    @OneToMany(mappedBy = "playbackSession", cascade = CascadeType.ALL)
//    private List<AdImpression> adImpression;

    public enum Status { ACTIVE, COMPLETED, ABORTED }
}
