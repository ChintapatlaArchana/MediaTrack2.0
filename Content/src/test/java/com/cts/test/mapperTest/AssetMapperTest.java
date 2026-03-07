package com.cts.test.mapperTest;

import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.mapper.AssetMapper;
import com.cts.model.Asset;
import com.cts.model.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetMapperTest {

    @Mock
    private AssetMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapAssetTypeToString() {
        when(mapper.mapAssetType(Asset.AssetType.clip)).thenReturn("clip");

        String type = mapper.mapAssetType(Asset.AssetType.clip);
        assertEquals("clip", type);

        verify(mapper).mapAssetType(Asset.AssetType.clip);
    }

    @Test
    void testMapAssetTypeNull() {
        when(mapper.mapAssetType((String) null)).thenReturn(null);
        when(mapper.mapAssetType((Asset.AssetType) null)).thenReturn(null);

        assertNull(mapper.mapAssetType((String) null));
        assertNull(mapper.mapAssetType((Asset.AssetType) null));

        verify(mapper).mapAssetType((String) null);
        verify(mapper).mapAssetType((Asset.AssetType) null);
    }

    @Test
    void testEntityToDtoMapping() {
        Asset asset = new Asset();
        asset.setAssetId(10L);
        asset.setLanguage("English");

        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setAssetId(10L);
        dto.setLanguage("English");

        when(mapper.toDto(asset)).thenReturn(dto);

        AssetResponseDTO result = mapper.toDto(asset);

        assertEquals(10L, result.getAssetId());
        assertEquals("English", result.getLanguage());

        verify(mapper).toDto(asset);
    }

    @Test
    void testDtoToEntityMapping() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setLanguage("German");

        Asset entity = new Asset();
        entity.setLanguage("German");

        when(mapper.toEntity(dto)).thenReturn(entity);

        Asset result = mapper.toEntity(dto);

        assertEquals("German", result.getLanguage());

        verify(mapper).toEntity(dto);
    }

    @Test
    void testRoundTripMapping() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setLanguage("Japanese");

        Asset entity = new Asset();
        entity.setLanguage("Japanese");

        AssetResponseDTO response = new AssetResponseDTO();
        response.setLanguage("Japanese");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);

        Asset mappedEntity = mapper.toEntity(dto);
        AssetResponseDTO mappedResponse = mapper.toDto(mappedEntity);

        assertEquals("Japanese", mappedResponse.getLanguage());

        verify(mapper).toEntity(dto);
        verify(mapper).toDto(entity);
    }
}
