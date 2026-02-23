package com.cts.dto;

import com.cts.model.AdImpression.Clicked;
import com.cts.model.AdImpression.Viewability;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class AdImpressionRequestDTO {

    private Long sessionId;
    private LocalDateTime timestamp;
    //ENUM
    private Viewability viewability;
   //ENUM
    private Clicked clicked;

    private Long campaignId;
    private Long slotId;
}
