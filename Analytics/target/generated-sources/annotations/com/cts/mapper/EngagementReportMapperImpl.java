package com.cts.mapper;

import com.cts.dto.EngagementReportMetricsDTO;
import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.EngagementReportResponseDTO;
import com.cts.dto.ScopeDTO;
import com.cts.model.EngagementReport;
import com.cts.model.EngagementReportMetrics;
import com.cts.model.Scope;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-22T21:46:18+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class EngagementReportMapperImpl implements EngagementReportMapper {

    @Override
    public EngagementReportResponseDTO toDto(EngagementReport engagementReport) {
        if ( engagementReport == null ) {
            return null;
        }

        EngagementReportResponseDTO engagementReportResponseDTO = new EngagementReportResponseDTO();

        engagementReportResponseDTO.setReportId( engagementReport.getReportId() );
        engagementReportResponseDTO.setScope( toDto( engagementReport.getScope() ) );
        engagementReportResponseDTO.setEngagementReportMetrics( engagementReportMetricsToEngagementReportMetricsDTO( engagementReport.getEngagementReportMetrics() ) );
        engagementReportResponseDTO.setGeneratedDate( engagementReport.getGeneratedDate() );

        return engagementReportResponseDTO;
    }

    @Override
    public EngagementReport toEntity(EngagementReportRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EngagementReport engagementReport = new EngagementReport();

        engagementReport.setScope( toEntity( dto.getScope() ) );
        engagementReport.setEngagementReportMetrics( engagementReportMetricsDTOToEngagementReportMetrics( dto.getEngagementReportMetrics() ) );
        engagementReport.setGeneratedDate( dto.getGeneratedDate() );

        return engagementReport;
    }

    @Override
    public Scope toEntity(ScopeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Scope scope = new Scope();

        scope.setTitle( dto.getTitle() );
        scope.setCategory( dto.getCategory() );
        scope.setPeriod( dto.getPeriod() );

        return scope;
    }

    @Override
    public ScopeDTO toDto(Scope scope) {
        if ( scope == null ) {
            return null;
        }

        ScopeDTO scopeDTO = new ScopeDTO();

        scopeDTO.setTitle( scope.getTitle() );
        scopeDTO.setCategory( scope.getCategory() );
        scopeDTO.setPeriod( scope.getPeriod() );

        return scopeDTO;
    }

    protected EngagementReportMetricsDTO engagementReportMetricsToEngagementReportMetricsDTO(EngagementReportMetrics engagementReportMetrics) {
        if ( engagementReportMetrics == null ) {
            return null;
        }

        EngagementReportMetricsDTO engagementReportMetricsDTO = new EngagementReportMetricsDTO();

        engagementReportMetricsDTO.setWatchTimeSeconds( engagementReportMetrics.getWatchTimeSeconds() );
        engagementReportMetricsDTO.setMau( engagementReportMetrics.getMau() );
        engagementReportMetricsDTO.setDau( engagementReportMetrics.getDau() );
        engagementReportMetricsDTO.setCompletionRate( engagementReportMetrics.getCompletionRate() );
        engagementReportMetricsDTO.setAvgBitrate( engagementReportMetrics.getAvgBitrate() );

        return engagementReportMetricsDTO;
    }

    protected EngagementReportMetrics engagementReportMetricsDTOToEngagementReportMetrics(EngagementReportMetricsDTO engagementReportMetricsDTO) {
        if ( engagementReportMetricsDTO == null ) {
            return null;
        }

        EngagementReportMetrics engagementReportMetrics = new EngagementReportMetrics();

        engagementReportMetrics.setWatchTimeSeconds( engagementReportMetricsDTO.getWatchTimeSeconds() );
        engagementReportMetrics.setMau( engagementReportMetricsDTO.getMau() );
        engagementReportMetrics.setDau( engagementReportMetricsDTO.getDau() );
        engagementReportMetrics.setCompletionRate( engagementReportMetricsDTO.getCompletionRate() );
        engagementReportMetrics.setAvgBitrate( engagementReportMetricsDTO.getAvgBitrate() );

        return engagementReportMetrics;
    }
}
