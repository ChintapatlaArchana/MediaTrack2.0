package com.cts.test.controllerTest;


import com.cts.controller.AssetController;
import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetController assetController;

    public AssetControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAsset() {
        AssetRequestDTO requestDTO = new AssetRequestDTO();
        requestDTO.setLanguage("English");

        AssetResponseDTO responseDTO = new AssetResponseDTO();
        responseDTO.setLanguage("English");

        when(assetService.createAsset(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<AssetResponseDTO> response = assetController.createAsset(requestDTO);

        assertEquals("English", response.getBody().getLanguage());
        verify(assetService, times(1)).createAsset(requestDTO);
    }

    @Test
    void testGetAssetById() {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setLanguage("French");
        when(assetService.getAsset(1L)).thenReturn(dto);

        ResponseEntity<AssetResponseDTO> response = assetController.getAssetById(1L);
        assertEquals("French", response.getBody().getLanguage());
    }

    @Test
    void testListAssetsByTitle() {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setLanguage("Spanish");

        when(assetService.getAssetsByTitle(2L)).thenReturn(List.of(dto));

        ResponseEntity<List<AssetResponseDTO>> response = assetController.listAssetsByTitle(2L);

        assertEquals(1, response.getBody().size());
        assertEquals("Spanish", response.getBody().get(0).getLanguage());
    }
}

