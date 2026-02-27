package com.cts.test.mapperTest;

import com.cts.dto.AdImpressionRequestDTO;
import com.cts.dto.AdImpressionResponseDTO;
import com.cts.mapper.AdImpressionMapper;
import com.cts.model.AdImpression;
import com.cts.model.AdSlot;
import com.cts.model.Campaign;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AdImpressionMapperTest {

    private final AdImpressionMapper mapper = Mappers.getMapper(AdImpressionMapper.class);

    @Test
    void testToEntity() {
        AdImpressionRequestDTO dto = new AdImpressionRequestDTO();
        dto.setTimestamp(LocalDateTime.now());

        AdImpression entity = mapper.toEntity(dto);

        assertEquals(dto.getTimestamp(), entity.getTimestamp());
    }

    @Test
    void testToDTO() {
        Campaign c = new Campaign();
        c.setCampaignId(33L);

        AdSlot s = new AdSlot();
        s.setSlotId(44L);

        AdImpression entity = new AdImpression();
        entity.setImpressionId(1L);
        entity.setCampaign(c);
        entity.setAdSlot(s);
        entity.setSessionId(55L);

        AdImpressionResponseDTO dto = mapper.toDTO(entity);

        assertEquals(33L, dto.getCampaignId());
        assertEquals(44L, dto.getSlotId());
        assertEquals(55L, dto.getSessionId());
    }
}