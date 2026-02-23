package com.cts.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data

public class PlaybackSessionResponseDTO {

    private Long sessionId;
    private Long userId;
    private Long assetId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double bitrateAvg;
    private int bufferEvents;
    private String status;
}
