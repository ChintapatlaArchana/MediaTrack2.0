package com.cts.test.mapperTest;

import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.mapper.AdDeliveryReportMapper;
import com.cts.model.AdDeliveryReport;
import com.cts.model.AdDeliveryReportMetrics;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdDeliveryReportMapperTest {

    private final AdDeliveryReportMapper mapper = Mappers.getMapper(AdDeliveryReportMapper.class);

    @Test
    void testToEntity() {
        AdDeliveryReportRequestDTO dto = new AdDeliveryReportRequestDTO();
        dto.setCampaignId(1L);
        dto.setGeneratedDate(LocalDate.now());

        AdDeliveryReportMetricsDTO metricsDTO = new AdDeliveryReportMetricsDTO();
        metricsDTO.setImpressions(100);
        dto.setAdDeliveryReportMetrics(metricsDTO);

        AdDeliveryReport entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getCampaignId());
        assertEquals(dto.getGeneratedDate(), entity.getGeneratedDate());
        assertNotNull(entity.getMetrics());
        assertEquals(100, entity.getMetrics().getImpressions());
    }

    @Test
    void testToDto() {
        AdDeliveryReport entity = new AdDeliveryReport();
        entity.setAdReportID(1L);
        entity.setCampaignId(1L);
        entity.setGeneratedDate(LocalDate.now());

        AdDeliveryReportMetrics metrics = new AdDeliveryReportMetrics();
        metrics.setImpressions(100);
        entity.setMetrics(metrics);

        AdDeliveryReportResponseDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getAdReportID());
        assertEquals(1L, dto.getCampaignId());
        assertEquals(entity.getGeneratedDate(), dto.getGeneratedDate());
        assertNotNull(dto.getAdDeliveryReportMetrics());
        assertEquals(100, dto.getAdDeliveryReportMetrics().getImpressions());
    }
}
