package com.cts.dto;

import com.cts.model.AdImpression;
import com.cts.model.Campaign.TargetingJSON;
import com.cts.model.Campaign.Pacing;
import com.cts.model.Campaign.Status;


import com.cts.model.Creative;
import lombok.Data;
import tools.jackson.databind.JsonNode;


import java.time.LocalDateTime;
import java.util.List;
@Data
public class CampaignResponseDTO {
    private Long campaignId;
    private Long creativeId;
    private String name;
    private String advertiser;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double budget;
    private Pacing pacing;
    private Status status;
    private JsonNode targetingJSON;//ask archana

//    // one to many
//    private List<AdImpression> impressionIds;

 //Many to one
//
}
