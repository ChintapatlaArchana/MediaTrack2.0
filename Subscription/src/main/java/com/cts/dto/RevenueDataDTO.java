package com.cts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RevenueDataDTO {
    String month;
    String category;
    Double amount;

    public RevenueDataDTO(String month, String category, Double amount) {
        this.month = month;
        this.category = category;
        this.amount = amount;
    }
}
