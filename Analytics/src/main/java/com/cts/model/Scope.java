package com.cts.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Embeddable
public class Scope {
    private String title;
    private String category;
    private String period;
}
