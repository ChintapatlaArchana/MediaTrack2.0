package com.cts.mapper;

import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.model.AdDeliveryReport;
import com.cts.model.AdDeliveryReportMetrics;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-22T21:46:18+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AdDeliveryReportMapperImpl implements AdDeliveryReportMapper {

    @Override
    public AdDeliveryReport toEntity(AdDeliveryReportRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AdDeliveryReport adDeliveryReport = new AdDeliveryReport();

        adDeliveryReport.setCampaignId( dto.getCampaignId() );
        adDeliveryReport.setAdDeliveryReportMetrics( adDeliveryReportMetricsDTOToAdDeliveryReportMetrics( dto.getAdDeliveryReportMetrics() ) );
        adDeliveryReport.setGeneratedDate( dto.getGeneratedDate() );

        return adDeliveryReport;
    }

    @Override
    public AdDeliveryReportResponseDTO toDto(AdDeliveryReport adDeliveryReport) {
        if ( adDeliveryReport == null ) {
            return null;
        }

        AdDeliveryReportResponseDTO adDeliveryReportResponseDTO = new AdDeliveryReportResponseDTO();

        adDeliveryReportResponseDTO.setCampaignId( adDeliveryReport.getCampaignId() );
        adDeliveryReportResponseDTO.setAdReportID( adDeliveryReport.getAdReportID() );
        adDeliveryReportResponseDTO.setAdDeliveryReportMetrics( adDeliveryReportMetricsToAdDeliveryReportMetricsDTO( adDeliveryReport.getAdDeliveryReportMetrics() ) );
        adDeliveryReportResponseDTO.setGeneratedDate( adDeliveryReport.getGeneratedDate() );

        return adDeliveryReportResponseDTO;
    }

    protected AdDeliveryReportMetrics adDeliveryReportMetricsDTOToAdDeliveryReportMetrics(AdDeliveryReportMetricsDTO adDeliveryReportMetricsDTO) {
        if ( adDeliveryReportMetricsDTO == null ) {
            return null;
        }

        AdDeliveryReportMetrics adDeliveryReportMetrics = new AdDeliveryReportMetrics();

        adDeliveryReportMetrics.setImpressions( adDeliveryReportMetricsDTO.getImpressions() );
        adDeliveryReportMetrics.setViewabilityRate( adDeliveryReportMetricsDTO.getViewabilityRate() );
        adDeliveryReportMetrics.setCTR( adDeliveryReportMetricsDTO.getCTR() );
        adDeliveryReportMetrics.setFillRate( adDeliveryReportMetricsDTO.getFillRate() );
        adDeliveryReportMetrics.setECPM( adDeliveryReportMetricsDTO.getECPM() );

        return adDeliveryReportMetrics;
    }

    protected AdDeliveryReportMetricsDTO adDeliveryReportMetricsToAdDeliveryReportMetricsDTO(AdDeliveryReportMetrics adDeliveryReportMetrics) {
        if ( adDeliveryReportMetrics == null ) {
            return null;
        }

        AdDeliveryReportMetricsDTO adDeliveryReportMetricsDTO = new AdDeliveryReportMetricsDTO();

        adDeliveryReportMetricsDTO.setImpressions( adDeliveryReportMetrics.getImpressions() );
        adDeliveryReportMetricsDTO.setViewabilityRate( adDeliveryReportMetrics.getViewabilityRate() );
        adDeliveryReportMetricsDTO.setCTR( adDeliveryReportMetrics.getCTR() );
        adDeliveryReportMetricsDTO.setFillRate( adDeliveryReportMetrics.getFillRate() );
        adDeliveryReportMetricsDTO.setECPM( adDeliveryReportMetrics.getECPM() );

        return adDeliveryReportMetricsDTO;
    }
}
