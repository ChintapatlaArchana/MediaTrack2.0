package com.cts.test.mapperTest;

import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.mapper.CreativeMapper;
import com.cts.model.Creative;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CreativeMapperTest {

    private final CreativeMapper mapper = Mappers.getMapper(CreativeMapper.class);

    @Test
    void testToEntity() {
        CreativeRequestDTO dto = new CreativeRequestDTO();
        dto.setAdvertiser("Nike");
        dto.setMediaUri("video.mp4");
        dto.setDuration(30);

        Creative entity = mapper.toEntity(dto);

        assertEquals("Nike", entity.getAdvertiser());
        assertEquals("video.mp4", entity.getMediaUri());
        assertEquals(30, entity.getDuration());
    }

    @Test
    void testToDTO() {
        Creative entity = new Creative();
        entity.setCreativeId(1L);
        entity.setAdvertiser("Coke");
        entity.setMediaUri("ad.mp4");
        entity.setDuration(15);
        entity.setStatus(Creative.Status.Active);

        CreativeResponseDTO dto = mapper.toDTO(entity);

        assertEquals(1L, dto.getCreativeId());
        assertEquals("Coke", dto.getAdvertiser());
        assertEquals("ad.mp4", dto.getMediaUri());
        assertEquals(15, dto.getDuration());
        assertEquals(Creative.Status.Active, dto.getStatus());
    }
}
