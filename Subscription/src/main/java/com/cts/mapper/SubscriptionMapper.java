package com.cts.mapper;

import com.cts.dto.SubscriptionRequestDTO;
import com.cts.dto.SubscriptionResponseDTO;
import com.cts.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    Subscription toEntity(SubscriptionRequestDTO dto);

    @Mapping(target = "planId", source = "plan.planId")
    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    SubscriptionResponseDTO toDTO(Subscription entity);

}
