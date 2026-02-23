package com.cts.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlaybackSessionRequestDTO {
    private Long userId;
    private Long assetId;
    private Double bitrateAvg;
    private Integer bufferEvents;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
//package com.cts.dto.delivery;
//
//import ch.qos.logback.core.status.Status;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class PlaybackSessionRequestDTO {
//    private Long userId;
//    private Long assetId;
//    private Double bitrateAvg;
//    private Integer bufferEvents;
//    private Status status;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//}
//
