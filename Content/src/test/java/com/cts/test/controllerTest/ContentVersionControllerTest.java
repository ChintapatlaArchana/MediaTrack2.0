package com.cts.test.controllerTest;

import com.cts.controller.ContentVersionController;
import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.service.ContentVersionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContentVersionControllerTest {

    @Mock
    private ContentVersionService contentVersionService;

    @InjectMocks
    private ContentVersionController contentVersionController;

    public ContentVersionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContentVersion() {
        ContentVersionRequestDTO requestDTO = new ContentVersionRequestDTO();
        requestDTO.setNotes("Localized");

        ContentVersionResponseDTO responseDTO = new ContentVersionResponseDTO();
        responseDTO.setNotes("Localized");

        when(contentVersionService.createContentVersion(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<ContentVersionResponseDTO> response = contentVersionController.createContentVersion(requestDTO);

        assertEquals("Localized", response.getBody().getNotes());
        verify(contentVersionService, times(1)).createContentVersion(requestDTO);
    }

    @Test
    void testListVersionByAsset() {
        ContentVersionResponseDTO dto = new ContentVersionResponseDTO();
        dto.setNotes("Edited");

        when(contentVersionService.getVersionsByAsset(1L)).thenReturn(List.of(dto));

        ResponseEntity<List<ContentVersionResponseDTO>> response = contentVersionController.listVersionByAsset(1L);

        assertEquals(1, response.getBody().size());
        assertEquals("Edited", response.getBody().get(0).getNotes());
    }
}

