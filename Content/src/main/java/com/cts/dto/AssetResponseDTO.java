package com.cts.dto;

import com.cts.model.*;
import lombok.Data;

import java.time.LocalDate;

import java.util.List;
@Data
public class AssetResponseDTO {
    private Long assetId;
    private String assetType;
    private Integer duration;
    private String language;
    private List<String> subtitleLanguages;
    private LocalDate availabilityStart;
    private LocalDate availabilityEnd;
    private Long titleId;

    //private List<ContentVersionResponseDTO> versionIds;

}
