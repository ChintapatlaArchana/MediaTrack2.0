package com.cts.mapper;


import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.model.AdDeliveryReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdDeliveryReportMapper {

    AdDeliveryReport toEntity(AdDeliveryReportRequestDTO dto);

    @Mapping(target = "campaignId" ,source = "campaignId")
    AdDeliveryReportResponseDTO toDto(AdDeliveryReport adDeliveryReport);
}
