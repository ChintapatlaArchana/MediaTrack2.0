package com.cts.Test.service;

import com.cts.dto.AssetResponseDTO;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.feign.AssetFeignClient;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.PlaybackSessionMapper;
import com.cts.model.PlaybackSession;
import com.cts.repository.PlaybackRepository;
import com.cts.service.PlaybackService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaybackServiceTest {

    @Mock
    private PlaybackRepository playbackRepository;

    @Mock
    private PlaybackSessionMapper mapper;

    @Mock
    private UserFeignClient userClient;

    @Mock
    private AssetFeignClient assetClient;

    @InjectMocks
    private PlaybackService playbackService;

    public PlaybackServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlaybackSession_success() {
        // Arrange
        PlaybackSessionRequestDTO req = new PlaybackSessionRequestDTO();
        req.setAssetId(10L);
        req.setStatus("COMPLETED");

        // Stub identity-service response
        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setUserId(123L);
        when(userClient.getUserById(123L)).thenReturn(ResponseEntity.ok(userDto));

        // Stub asset-service response
        AssetResponseDTO assetDto = new AssetResponseDTO();
        assetDto.setAssetId(10L);
        when(assetClient.getAssetById(10L)).thenReturn(assetDto);

        // Mapper + repository behavior
        PlaybackSession entity = new PlaybackSession();

        // Choose ONE depending on your entity:
        // If your entity has 'id':
        PlaybackSession savedEntity = new PlaybackSession();
        savedEntity.setSessionId(1L); // <-- will be mapped to response.sessionId if your mapper does @Mapping(target="sessionId", source="id")
        // If your entity has 'sessionId' instead, comment the above and use:
        // savedEntity.setSessionId(1L);

        savedEntity.setUserId(123L);
        savedEntity.setAssetId(10L);
        savedEntity.setStatus(PlaybackSession.Status.COMPLETED);

        PlaybackSessionResponseDTO resp = new PlaybackSessionResponseDTO();
        resp.setSessionId(1L);
        resp.setUserId(123L);
        resp.setAssetId(10L);
        resp.setStatus(PlaybackSession.Status.COMPLETED);

        when(mapper.toEntity(req)).thenReturn(entity);
        when(playbackRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(resp);

        // Act
        PlaybackSessionResponseDTO result = playbackService.create(req, "123");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getSessionId());
        assertEquals(123L, result.getUserId());
        assertEquals(10L, result.getAssetId());
        assertEquals(PlaybackSession.Status.COMPLETED, result.getStatus());

        verify(userClient).getUserById(123L);
        verify(assetClient).getAssetById(10L);
        verify(mapper).toEntity(req);
        verify(playbackRepository).save(entity);
        verify(mapper).toDto(savedEntity);
    }

    @Test
    void testGetById_found() {
        PlaybackSession entity = new PlaybackSession();
        // If your entity uses id:
        entity.setSessionId(5L);
        // If your entity uses sessionId instead, use:
        // entity.setSessionId(5L);

        PlaybackSessionResponseDTO dto = new PlaybackSessionResponseDTO();
        dto.setSessionId(5L);

        when(playbackRepository.findById(5L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        PlaybackSessionResponseDTO result = playbackService.getById(5L);

        assertEquals(5L, result.getSessionId());
        verify(playbackRepository).findById(5L);
        verify(mapper).toDto(entity);
    }

    @Test
    void testGetAllPlaybackSessions() {
        PlaybackSession e1 = new PlaybackSession();
        PlaybackSession e2 = new PlaybackSession();

        PlaybackSessionResponseDTO d1 = new PlaybackSessionResponseDTO();
        d1.setSessionId(1L);
        PlaybackSessionResponseDTO d2 = new PlaybackSessionResponseDTO();
        d2.setSessionId(2L);

        when(playbackRepository.findAll()).thenReturn(List.of(e1, e2));
        when(mapper.toDto(e1)).thenReturn(d1);
        when(mapper.toDto(e2)).thenReturn(d2);

        List<PlaybackSessionResponseDTO> result = playbackService.getAll();

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getSessionId());
        assertEquals(2L, result.get(1).getSessionId());
        verify(playbackRepository).findAll();
        verify(mapper, times(2)).toDto(any(PlaybackSession.class));
    }

    @Test
    void testDeletePlaybackSession_success() {
        when(playbackRepository.existsById(7L)).thenReturn(true);

        playbackService.delete(7L);

        verify(playbackRepository).deleteById(7L);
    }
}