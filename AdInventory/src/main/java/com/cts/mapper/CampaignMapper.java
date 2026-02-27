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

@Mapper(componentModel = "spring",imports = {ObjectMapper.class})
public interface
CampaignMapper {

    @Mapping(target = "pacing", expression = "java(Campaign.Pacing.valueOf(dto.getPacing()))")
    @Mapping(target = "status", expression = "java(Campaign.Status.valueOf(dto.getStatus()))")
    @Mapping(target = "targetingJSON", expression = "java(dto.getTargetingJSON() == null ? null : dto.getTargetingJSON().toString())")
    Campaign toEntity(CampaignRequestDTO dto);

    @Mapping(target = "creativeId",source = "creative.creativeId")
    @Mapping(target="pacing", expression = "java(entity.getPacing())")
    @Mapping(target="status", expression = "java(entity.getStatus())")
    @Mapping(target = "targetingJSON", expression = "java(readJson(entity.getTargetingJSON()))")
    CampaignResponseDTO toDTO(Campaign entity);

    default JsonNode readJson(String json) {
        try {
            return json == null ? null : new ObjectMapper().readTree(json);
        }
        catch (Exception e) {
            return null;
        }
    }
}
