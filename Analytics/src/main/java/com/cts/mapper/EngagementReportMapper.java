package com.cts.mapper;

import com.cts.dto.EngagementReportMetricsDTO;
import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.EngagementReportResponseDTO;
import com.cts.dto.ScopeDTO;
import com.cts.model.EngagementReport;
import com.cts.model.EngagementReportMetrics;
import com.cts.model.Scope;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EngagementReportMapper {

    EngagementReportResponseDTO toDto(EngagementReport engagementReport);
    EngagementReport toEntity(EngagementReportRequestDTO dto );

    Scope toEntity(ScopeDTO dto);
    ScopeDTO toDto(Scope scope);

//    EngagementReportMetrics toEntity(EngagementReportMetricsDTO dto);
//    EngagementReportMetricsDTO toDto(EngagementReportMetrics engagementReportMetrics);
//

}
