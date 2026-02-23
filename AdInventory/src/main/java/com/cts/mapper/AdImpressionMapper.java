package com.cts.mapper;

import com.cts.dto.AdImpressionRequestDTO;
import com.cts.dto.AdImpressionResponseDTO;
import com.cts.model.AdImpression;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdImpressionMapper {

    // DTO → Entity
    @Mapping(target = "impressionId", ignore = true)
    @Mapping(target = "campaign", ignore = true)
    @Mapping(target = "adSlot", ignore = true)
    @Mapping(target = "sessionId", ignore = true)  // we set this in service
    AdImpression toEntity(AdImpressionRequestDTO dto);

    // Entity → DTO
    @Mapping(target = "campaignId", source = "campaign.campaignId")
    @Mapping(target = "slotId", source = "adSlot.slotId")
    @Mapping(target = "sessionId", source = "sessionId")   // since it's just a long field
    AdImpressionResponseDTO toDTO(AdImpression entity);
}