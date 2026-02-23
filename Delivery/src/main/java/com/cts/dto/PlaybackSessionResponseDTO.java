package com.cts.dto;

import com.cts.model.PlaybackSession.Status;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlaybackSessionResponseDTO {

    private Long sessionId;
    private Long userId;
    private Long assetId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double bitrateAvg;
    private int bufferEvents;
    private Status status;
//    private List<Long> drmEventID;
//    private List<Long> adImpressionID;
}
//package com.cts.dto.delivery;
//
//import ch.qos.logback.core.status.Status;
//import lombok.Data;
//import java.time.LocalDateTime;
//import java.util.List;
//@Data
//public class PlaybackSessionResponseDTO {
//
//        private long sessionId;
//        private Long userId;
//        private Long assetId;
//        private LocalDateTime startTime;
//        private LocalDateTime endTime;
//        private double bitrateAvg;
//        private int bufferEvents;
//        private Status status;
//        private List<Long> drmEventID;
//        private List<Long> adImpressionID;
//}
//
