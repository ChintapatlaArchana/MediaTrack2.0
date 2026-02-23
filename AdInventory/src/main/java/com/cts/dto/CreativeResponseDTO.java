package com.cts.dto;

import com.cts.model.Campaign;
import com.cts.model.Creative.Status;
import lombok.Data;


import java.util.List;
@Data
public class CreativeResponseDTO {
    private Long creativeId;
    private String advertiser;
    private String mediaUri;
    private Integer duration;
    private String clickThroughUrl;
    private Status status;
    //enum values


//    private List<Campaign> campaignId;
}
