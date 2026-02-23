package com.cts.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="drmevent")
public class DRMEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drmEventID; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drmevent_sessionId")
    private PlaybackSession playbackSession; // Foreign Key to PlaybackSession

    private String drmType; // e.g., Widevine, PlayReady
    private LocalDateTime eventTime;

    @Enumerated(EnumType.STRING)
    private LicenseStatus licenseStatus;

    public enum LicenseStatus { Granted, Denied, Expired }
}
