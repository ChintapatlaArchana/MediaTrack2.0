package com.cts.dto;




import jakarta.persistence.Lob;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CampaignResponseDTO {
    private Long campaignId;
    private String name;
    private String advertiser;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private String pacing;
    private String status;
    @Lob
    private String targetingJSON;//ask archana

//    // one to many
//    private List<AdImpression> impressionIds;

    //Many to one
//    private Long creativeId;
}
