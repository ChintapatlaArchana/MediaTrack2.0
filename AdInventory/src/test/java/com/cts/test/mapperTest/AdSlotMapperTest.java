package com.cts.test.mapperTest;

import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.mapper.AdSlotMapper;
import com.cts.model.AdSlot;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AdSlotMapperTest {

    private final AdSlotMapper mapper = Mappers.getMapper(AdSlotMapper.class);

    @Test
    void toEntity_basicMapping() {
        AdSlotRequestDTO dto = new AdSlotRequestDTO();
        dto.setPlacement("PreRoll");
        dto.setDuration(20);
        dto.setConstraintsJson(null);

        AdSlot entity = mapper.toEntity(dto);

        assertEquals(AdSlot.Placement.PreRoll, entity.getPlacement());
        assertEquals(20, entity.getDuration());
        assertNull(entity.getConstraintsJson());
    }

    @Test
    void toEntity_invalidEnum_throws() {
        AdSlotRequestDTO dto = new AdSlotRequestDTO();
        dto.setPlacement("XYZ");  // invalid
        dto.setDuration(10);

        assertThrows(IllegalArgumentException.class,
                () -> mapper.toEntity(dto));
    }

    @Test
    void toDTO_basicMapping_nullJson() {
        AdSlot entity = new AdSlot();
        entity.setSlotId(22L);
        entity.setPlacement(AdSlot.Placement.PostRoll);
        entity.setDuration(45);
        entity.setConstraintsJson(null);

        AdSlotResponseDTO out = mapper.toDTO(entity);

        assertEquals(22L, out.getSlotId());
        assertEquals(AdSlot.Placement.PostRoll, out.getPlacement());
        assertEquals(45, out.getDuration());
        assertNull(out.getConstraintsJson());
    }

    @Test
    void toDTO_validJsonString_parsedCorrectly() {
        AdSlot entity = new AdSlot();
        entity.setConstraintsJson("{\"adType\":\"skippable\"}");

        AdSlotResponseDTO out = mapper.toDTO(entity);

        assertNotNull(out.getConstraintsJson());
        assertEquals("skippable", out.getConstraintsJson().get("adType").asText());
    }
}