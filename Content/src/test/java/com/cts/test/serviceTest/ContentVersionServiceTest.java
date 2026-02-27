package com.cts.test.serviceTest;


import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.ContentVersionMapper;
import com.cts.model.Asset;
import com.cts.model.ContentVersion;
import com.cts.repository.AssetRepository;
import com.cts.repository.ContentVersionRepository;
import com.cts.service.ContentVersionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentVersionServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private ContentVersionRepository contentVersionRepository;

    @Mock
    private ContentVersionMapper contentVersionMapper;

    @InjectMocks
    private ContentVersionService contentVersionService;

    public ContentVersionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContentVersionSuccess() {
        ContentVersionRequestDTO dto = new ContentVersionRequestDTO();
        dto.setAssetId(1L);
        dto.setNotes("Director's cut");

        Asset asset = new Asset();
        asset.setAssetId(1L);

        ContentVersion version = new ContentVersion();
        version.setNotes("Director's cut");
        version.setAsset(asset);

        ContentVersionResponseDTO responseDTO = new ContentVersionResponseDTO();
        responseDTO.setNotes("Director's cut");

        when(contentVersionMapper.toEntity(dto)).thenReturn(version);
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(contentVersionRepository.save(version)).thenReturn(version);
        when(contentVersionMapper.toDto(version)).thenReturn(responseDTO);

        ContentVersionResponseDTO result = contentVersionService.createContentVersion(dto);

        assertEquals("Director's cut", result.getNotes());
        verify(contentVersionRepository, times(1)).save(version);
    }

    @Test
    void testCreateContentVersionAssetNotFound() {
        ContentVersionRequestDTO dto = new ContentVersionRequestDTO();
        dto.setAssetId(99L);

        when(assetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> contentVersionService.createContentVersion(dto));
    }
}

