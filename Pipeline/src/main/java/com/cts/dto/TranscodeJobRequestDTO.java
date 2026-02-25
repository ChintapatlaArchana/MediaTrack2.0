package com.cts.dto;

import com.cts.model.TranscodeJob.TranscodeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TranscodeJobRequestDTO {
    private Long assetId;
    private String profile;
    private TranscodeStatus transcodeStatus;
    private LocalDate completedDate;

}

