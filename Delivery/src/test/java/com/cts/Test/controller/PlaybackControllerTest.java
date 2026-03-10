package com.cts.Test.controller;

import com.cts.controller.PlaybackController;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.model.PlaybackSession;
import com.cts.service.PlaybackService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlaybackControllerTest {

    @Mock
    private PlaybackService playbackService;

    @InjectMocks
    private PlaybackController playbackController;

    public PlaybackControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlaybackSession() {
        // Arrange
        PlaybackSessionRequestDTO requestDTO = new PlaybackSessionRequestDTO();
        requestDTO.setAssetId(10L);
        requestDTO.setStatus("ACTIVE");

        PlaybackSessionResponseDTO responseDTO = new PlaybackSessionResponseDTO();
        responseDTO.setUserId(123L);
        responseDTO.setAssetId(10L);
        responseDTO.setStatus(PlaybackSession.Status.ACTIVE);

        // Controller expects X-User-Id header value (as String)
        when(playbackService.create(requestDTO, "123")).thenReturn(responseDTO);

        // Act
        ResponseEntity<PlaybackSessionResponseDTO> response =
                playbackController.create(requestDTO, "123");

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(123L, response.getBody().getUserId());
        assertEquals(10L, response.getBody().getAssetId());
        assertEquals("ACTIVE", response.getBody().getStatus().name());
        verify(playbackService, times(1)).create(requestDTO, "123");
    }

    @Test
    void testGetAllPlaybackSessions() {
        // Arrange
        PlaybackSessionResponseDTO dto1 = new PlaybackSessionResponseDTO();
        dto1.setAssetId(1L);
        PlaybackSessionResponseDTO dto2 = new PlaybackSessionResponseDTO();
        dto2.setAssetId(2L);

        when(playbackService.getAll()).thenReturn(List.of(dto1, dto2));

        // Act
        ResponseEntity<List<PlaybackSessionResponseDTO>> response =
                playbackController.getAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getAssetId());
        assertEquals(2L, response.getBody().get(1).getAssetId());
        verify(playbackService, times(1)).getAll();
    }

    @Test
    void testGetPlaybackSessionById() {
        // Arrange
        PlaybackSessionResponseDTO dto = new PlaybackSessionResponseDTO();
        dto.setUserId(321L);
        dto.setAssetId(9L);
        dto.setStatus(PlaybackSession.Status.COMPLETED);

        when(playbackService.getById(5L)).thenReturn(dto);

        // Act
        ResponseEntity<PlaybackSessionResponseDTO> response =
                playbackController.getById(5L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(321L, response.getBody().getUserId());
        assertEquals(9L, response.getBody().getAssetId());
        assertEquals("COMPLETED", response.getBody().getStatus().name());
        verify(playbackService, times(1)).getById(5L);
    }

    @Test
    void testDeletePlaybackSession() {
        // Act
        ResponseEntity<Void> response = playbackController.delete(7L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(playbackService, times(1)).delete(7L);
    }
}