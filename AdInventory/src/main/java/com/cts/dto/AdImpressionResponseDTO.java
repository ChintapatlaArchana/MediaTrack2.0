package com.cts.dto;

import com.cts.model.AdImpression.Clicked;
import com.cts.model.AdImpression.Viewability;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class AdImpressionResponseDTO {
    private Long impressionId;
    private Long campaignId;
    private Long slotId;
    private Long sessionId;
    private LocalDateTime timestamp;
    private Viewability viewability;
    private Clicked clicked;
}
