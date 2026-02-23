package com.cts.mapper;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.model.ChurnCohort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChurnCohortMapper {

    ChurnCohort toEntity(ChurnCohortRequestDTO dto);

    @Mapping(target = "planId",source = "planId")
    ChurnCohortResponseDTO toDto(ChurnCohort churnCohort);
}
