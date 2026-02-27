package com.cts.test.mapperTest;

import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.mapper.AssetMapper;
import com.cts.model.Asset;
import com.cts.model.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssetMapperTest {

    @Autowired
    private AssetMapper mapper;

    @Test
    void testMapAssetTypeToString() {
        String type = mapper.mapAssetType(Asset.AssetType.clip);
        assertEquals("clip", type);
    }

    @Test
    void testMapAssetTypeNull() {
        assertNull(mapper.mapAssetType((String) null));
        assertNull(mapper.mapAssetType((Asset.AssetType) null));
    }

    @Test
    void testEntityToDtoMapping() {
        Title title = new Title();
        title.setTitleId(5L);

        Asset asset = new Asset();
        asset.setAssetId(10L);
        asset.setLanguage("English");
        asset.setAssetType(Asset.AssetType.movie);
        asset.setSubtitleLanguages(List.of("French", "Spanish"));
        asset.setAvailabilityStart(LocalDate.of(2025, 1, 1));
        asset.setAvailabilityEnd(LocalDate.of(2025, 12, 31));
        asset.setTitle(title);

        AssetResponseDTO dto = mapper.toDto(asset);

        assertEquals(10L, dto.getAssetId());
        assertEquals("English", dto.getLanguage());
        assertEquals(5L, dto.getTitleId());
        assertEquals(2, dto.getSubtitleLanguages().size());
    }

    @Test
    void testDtoToEntityMapping() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setLanguage("German");
        dto.setAssetType(Asset.AssetType.episode);
        dto.setSubtitleLanguages(List.of("English", "French"));
        dto.setAvailabilityStart(LocalDate.of(2026, 2, 1));
        dto.setAvailabilityEnd(LocalDate.of(2026, 3, 1));

        Asset entity = mapper.toEntity(dto);

        assertEquals("German", entity.getLanguage());
        assertEquals(Asset.AssetType.episode, entity.getAssetType());
        assertEquals(2, entity.getSubtitleLanguages().size());
    }

    @Test
    void testRoundTripMapping() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setLanguage("Japanese");
        dto.setAssetType(Asset.AssetType.clip);

        Asset entity = mapper.toEntity(dto);
        AssetResponseDTO response = mapper.toDto(entity);

        assertEquals("Japanese", response.getLanguage());

    }
}
