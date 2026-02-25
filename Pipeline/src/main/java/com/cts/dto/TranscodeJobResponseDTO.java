package com.cts.dto;
import com.cts.model.TranscodeJob.TranscodeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TranscodeJobResponseDTO {
    private Long transcodeId;
    private Long assetId;
    private String profile;
    private LocalDate startedDate;
    private LocalDate completedDate;
    private TranscodeStatus transcodeStatus;

   }

