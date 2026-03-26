package com.cts.dto;

import com.cts.model.Campaign.Status;
import com.cts.model.Campaign.Pacing;
//import com.cts.model.Campaign.TargetingJSON;
import lombok.Data;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CampaignRequestDTO {

    //only the attributes.
    private String name;
    private String advertiser;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private String pacing;
    private String status;
    //private JsonNode targetingJSON;
    private Long creativeId;//ASK archana related to json attribute
}
