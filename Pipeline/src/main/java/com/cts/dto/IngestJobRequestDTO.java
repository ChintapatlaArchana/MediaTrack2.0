package com.cts.dto;

import com.cts.model.IngestJob.IngestStatus;
import lombok.Data;

@Data
public class IngestJobRequestDTO {

    private Long assetId;
    private String sourceUri;
    private IngestStatus ingestStatus;
}
