package com.cts.mapper;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.model.Entitlement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Entitlement.ContentScope.class})
public interface EntitlementMapper {

    @Mapping(target = "contentScope", expression = "java(ContentScope.valueOf(dto.getContentScope()))")
    Entitlement toEntity(EntitlementRequestDTO dto);

    @Mapping(target = "contentScope", expression = "java(entity.getContentScope())")
    EntitlementResponseDTO toDto(Entitlement entity);
}
