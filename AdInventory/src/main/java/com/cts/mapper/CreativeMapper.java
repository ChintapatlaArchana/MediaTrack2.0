package com.cts.mapper;

import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.model.Creative;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreativeMapper {

    Creative toEntity(CreativeRequestDTO dto);

    CreativeResponseDTO toDTO(Creative entity);
}