package com.cts.dto;

import com.cts.model.*;
import com.cts.model.Asset.AssetType;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;

@Data
public class AssetRequestDTO {

    //private Long assetId;
    private Long titleId;
    private AssetType assetType;
    private int duration;
    private String language;
    private List<String> subtitleLanguages;

    private LocalDate availabilityStart;
    private LocalDate availabilityEnd;


}
