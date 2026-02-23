package com.cts.dto;
import com.cts.model.TranscodeJob.TranscodeStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TranscodeJobResponseDTO {
    private Long transcodeId;
    private Long assetId;
    private String profile;
    private LocalDateTime startedDate;
    private LocalDateTime completedDate;
    private TranscodeStatus transcodeStatus;

   }

