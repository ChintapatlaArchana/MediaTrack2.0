package com.cts.dto;

import com.cts.model.ContentVersion.VersionLabel;
import lombok.Data;

@Data
public class ContentVersionResponseDTO{

    private Long versionId;
    private Long assetId;
    private VersionLabel versionLabel;
    private String notes;
    //private Long assetIds;
}
