package com.cts.mapper;

import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.model.Campaign;
import com.cts.model.Campaign.*;
import com.cts.model.Campaign.Pacing;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    // Remove the expressions. MapStruct will automatically call
    // Campaign.Pacing.valueOf() but with built-in null safety.
    Campaign toEntity(CampaignRequestDTO dto);

    @Mapping(target = "creativeId", source = "creative.creativeId")
    CampaignResponseDTO toDTO(Campaign entity);

    // Keep your custom JSON logic if needed
//    default JsonNode readJson(String json) {
//        if (json == null) return null;
//        try {
//            return new ObjectMapper().readTree(json);
//        } catch (Exception e) {
//            return null;
//        }
//    }
}

