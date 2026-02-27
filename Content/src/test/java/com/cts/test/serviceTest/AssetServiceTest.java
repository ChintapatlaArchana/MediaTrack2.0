package com.cts.test.serviceTest;



import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.AssetMapper;
import com.cts.model.Asset;
import com.cts.model.Title;
import com.cts.repository.AssetRepository;
import com.cts.repository.TitleRepository;
import com.cts.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private TitleRepository titleRepository;

    @Mock
    private AssetMapper assetMapper;

    @InjectMocks
    private AssetService assetService;

    public AssetServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAssetSuccess() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setTitleId(1L);
        dto.setLanguage("English");

        Title title = new Title();
        title.setTitleId(1L);

        Asset asset = new Asset();
        asset.setLanguage("English");
        asset.setTitle(title);

        AssetResponseDTO responseDTO = new AssetResponseDTO();
        responseDTO.setLanguage("English");

        when(assetMapper.toEntity(dto)).thenReturn(asset);
        when(titleRepository.findById(1L)).thenReturn(Optional.of(title));
        when(assetRepository.save(asset)).thenReturn(asset);
        when(assetMapper.toDto(asset)).thenReturn(responseDTO);

        AssetResponseDTO result = assetService.createAsset(dto);

        assertEquals("English", result.getLanguage());
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testCreateAssetTitleNotFound() {
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setTitleId(99L);

        when(titleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(GlobalException.class, () -> assetService.createAsset(dto));
    }
}

