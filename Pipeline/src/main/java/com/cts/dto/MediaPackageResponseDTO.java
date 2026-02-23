package com.cts.dto;

import com.cts.model.MediaPackage.QCStatus;
import lombok.Data;

@Data
public class MediaPackageResponseDTO {
    private Long packageId;
    private Long assetId;
    private String format;
    private String drm;
    private String cdnPath;
    private QCStatus qcStatus;
}
