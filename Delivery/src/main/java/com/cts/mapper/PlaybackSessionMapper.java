package com.cts.mapper;

import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.model.PlaybackSession;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports= {PlaybackSession.Status.class})
public interface PlaybackSessionMapper {

    /* ===== DTO (request) -> Entity ===== */
    // Relations (user, asset) should be resolved in the service using userId/assetId
    @Mapping(target="status",expression = "java(Status.valueOf(dto.getStatus()))")
    PlaybackSession toEntity(PlaybackSessionRequestDTO dto);

    /* ===== Optional: update for PUT/PATCH ===== */
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "status", source = "status")
    void updateEntity(@MappingTarget PlaybackSession entity, PlaybackSessionRequestDTO dto);

    /* ===== Entity -> DTO (response) ===== */

    @Mapping(target="status",expression = "java(entity.getStatus())")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "assetId", source = "assetId")
    PlaybackSessionResponseDTO toDto(PlaybackSession entity);

}
