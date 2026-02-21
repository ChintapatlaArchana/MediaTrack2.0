package com.cts.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SubscriptionRequestDTO {
//    private Long userId;
    private Long planId;
    private LocalDate startDate;

}
