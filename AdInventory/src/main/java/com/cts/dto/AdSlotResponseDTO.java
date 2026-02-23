package com.cts.dto;

import com.cts.model.AdImpression;
import com.cts.model.AdSlot.Placement;
import tools.jackson.databind.JsonNode;

import lombok.Data;

import java.util.List;

@Data
public class AdSlotResponseDTO {
    private Long slotId;
    private Placement placement;
    private Integer duration;
    private JsonNode constraintsJson;

    // One-to-Many children from AdSlot
//

}
