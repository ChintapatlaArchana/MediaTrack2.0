package com.cts.test.mapperTest;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.EngagementReportResponseDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EngagementReportMapperTest {

    private final EngagementReportMapper mapper = Mappers.getMapper(EngagementReportMapper.class);

    @Test
    void testToEntity() {
        EngagementReportRequestDTO dto = new EngagementReportRequestDTO();
        dto.setGeneratedDate(LocalDate.now());

        EngagementReport entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getGeneratedDate(), entity.getGeneratedDate());
    }

    @Test
    void testToDto() {
        EngagementReport entity = new EngagementReport();
        entity.setReportId(1L);
        entity.setGeneratedDate(LocalDate.now());

        EngagementReportResponseDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getReportId());
        assertEquals(entity.getGeneratedDate(), dto.getGeneratedDate());
    }
}
