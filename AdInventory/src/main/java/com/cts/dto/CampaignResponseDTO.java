package com.cts.dto;

import com.cts.model.Campaign.Pacing;
import com.cts.model.Campaign.Status;


import lombok.Data;
import tools.jackson.databind.JsonNode;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CampaignResponseDTO {
    private Long campaignId;
    private Long creativeId;
    private String name;
    private String advertiser;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private Pacing pacing;
    private Status status;
    //private JsonNode targetingJSON;



//    // one to many
//    private List<AdImpression> impressionIds;

 //Many to one
//
}
