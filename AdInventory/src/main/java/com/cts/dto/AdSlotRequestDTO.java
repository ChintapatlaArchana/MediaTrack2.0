package com.cts.dto;
import com.cts.model.AdSlot.Placement;
import tools.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class AdSlotRequestDTO {

    private String placement;
    private Integer duration;
    private JsonNode constraintsJson;//ask archana for json attribute
}




