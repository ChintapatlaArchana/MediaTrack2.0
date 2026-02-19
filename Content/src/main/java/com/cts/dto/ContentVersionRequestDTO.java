package com.cts.dto;

import com.cts.model.Asset;
import com.cts.model.ContentVersion.VersionLabel;
import lombok.Data;

@Data
public class ContentVersionRequestDTO {
    //private Long versionId;
    private Long assetId;
    private VersionLabel versionLabel;
    private String notes;

}
