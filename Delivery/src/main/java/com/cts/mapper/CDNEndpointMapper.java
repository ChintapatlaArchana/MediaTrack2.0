package com.cts.mapper;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.model.CDNEndpoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {CDNEndpoint.Status.class})
public interface CDNEndpointMapper {

    @Mapping(target="status", expression="java(Status.Active)")
    CDNEndpoint toEntity(CDNEndpointRequestDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus())")
    CDNEndpointResponseDTO toDto(CDNEndpoint entity);
}

