package com.cts.dto;

import com.cts.model.IngestJob.IngestStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IngestJobResponseDTO {

    private Long ingestId;
    private Long assetId;
    private String sourceUri;
    private LocalDate submittedDate;
    private IngestStatus ingestStatus;
}
