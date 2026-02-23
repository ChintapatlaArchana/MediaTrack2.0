package com.cts.mapper;

import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.model.AdSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Mapper(componentModel = "spring", imports = {AdSlot.Placement.class, ObjectMapper.class})
public interface AdSlotMapper {

    // Request DTO → Entity
    @Mapping(target = "slotId", ignore = true)
    @Mapping(target = "placement", expression = "java(Placement.valueOf(dto.getPlacement()))")
    @Mapping(target = "constraintsJson", expression = "java(dto.getConstraintsJson() == null ? null : dto.getConstraintsJson().toString())")
    AdSlot toEntity(AdSlotRequestDTO dto);

    // Entity → Response DTO
    @Mapping(target = "placement", expression = "java(entity.getPlacement())")
    @Mapping(target = "constraintsJson", expression = "java(readJson(entity.getConstraintsJson()))")
    AdSlotResponseDTO toDTO(AdSlot entity);

    default JsonNode readJson(String json) {
        try {
            return json == null ? null : new ObjectMapper().readTree(json);
        }
        catch (Exception e) {
            return null;
        }
    }
}