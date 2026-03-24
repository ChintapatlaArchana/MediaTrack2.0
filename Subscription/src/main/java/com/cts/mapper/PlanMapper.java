package com.cts.mapper;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.model.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Mapper(componentModel = "spring", imports = {Plan.BillingCycle.class, Plan.Status.class, ObjectMapper.class})

public interface PlanMapper {

    @Mapping(target = "planId", ignore = true)
    @Mapping(target = "billingCycle", expression = "java(BillingCycle.valueOf(dto.getBillingCycle()))")
    @Mapping(target = "status", expression = "java(Status.valueOf(dto.getStatus()))")
    @Mapping(target = "entitlementsJSON", expression = "java(dto.getEntitlements() == null ? null : dto.getEntitlements().toString())")

    Plan toEntity(PlanRequestDTO dto);

    @Mapping(target = "billingCycle", expression = "java(entity.getBillingCycle())")
    @Mapping(target = "status", expression = "java(entity.getStatus())")
    @Mapping(target = "entitlementsJSON", source = "entitlementsJSON")

    PlanResponseDTO toDTO(Plan entity);
}
