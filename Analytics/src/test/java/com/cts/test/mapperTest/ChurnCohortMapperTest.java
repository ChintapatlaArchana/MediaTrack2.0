package com.cts.test.mapperTest;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.mapper.ChurnCohortMapper;
import com.cts.model.ChurnCohort;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChurnCohortMapperTest {

    private final ChurnCohortMapper mapper = Mappers.getMapper(ChurnCohortMapper.class);

    @Test
    void testToEntity() {
        ChurnCohortRequestDTO dto = new ChurnCohortRequestDTO();
        dto.setPlanId(1L);
        dto.setStartPeriod(LocalDate.now());
        dto.setRetainedPct(85.5);
        dto.setChurnedPct(14.5);

        ChurnCohort entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getPlanId());
        assertEquals(dto.getStartPeriod(), entity.getStartPeriod());
        assertEquals(85.5, entity.getRetainedPct());
        assertEquals(14.5, entity.getChurnedPct());
    }

    @Test
    void testToDto() {
        ChurnCohort entity = new ChurnCohort();
        entity.setCohortId(1L);
        entity.setPlanId(1L);
        entity.setStartPeriod(LocalDate.now());
        entity.setRetainedPct(85.5);
        entity.setChurnedPct(14.5);

        ChurnCohortResponseDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getCohortId());
        assertEquals(1L, dto.getPlanId());
        assertEquals(entity.getStartPeriod(), dto.getStartPeriod());
        assertEquals(85.5, dto.getRetainedPct());
        assertEquals(14.5, dto.getChurnedPct());
    }
}
