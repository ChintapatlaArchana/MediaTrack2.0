package com.cts.Test.service;

import com.cts.dto.AssetResponseDTO;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.AssetFeignClient;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.PlaybackSessionMapper;
import com.cts.model.PlaybackSession;
import com.cts.repository.PlaybackRepository;
import com.cts.service.PlaybackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    private PlaybackSessionRequestDTO requestDto;
    private PlaybackSessionResponseDTO responseDto;
    private UserResponseDTO userDto;
    private AssetResponseDTO assetDto;

    @BeforeEach
    void setUp() {
        // Prepare request DTO (use your builder/ctor if setters are not available)
        requestDto = new PlaybackSessionRequestDTO();
        try {
            requestDto.getClass().getMethod("setStatus", String.class).invoke(requestDto, "ACTIVE");
            requestDto.getClass().getMethod("setAssetId", Long.class).invoke(requestDto, 100L);
        } catch (Exception ignored) {
        }

        // Prepare the response DTO we will assert on (mapper.toDto returns this)
        responseDto = new PlaybackSessionResponseDTO();
        try {
            responseDto.getClass().getMethod("setId", Long.class).invoke(responseDto, 1L);
            // Try to set status (enum or string depending on your DTO)
            try {
                responseDto.getClass().getMethod("setStatus", String.class).invoke(responseDto, "ACTIVE");
            } catch (NoSuchMethodException e) {
                // If status is enum type in response DTO, you can skip or adapt if needed.
            }
            responseDto.getClass().getMethod("setUserId", Long.class).invoke(responseDto, 10L);
            responseDto.getClass().getMethod("setAssetId", Long.class).invoke(responseDto, 100L);
        } catch (Exception ignored) {
        }

        userDto = new UserResponseDTO();
        try {
            userDto.getClass().getMethod("setUserId", Long.class).invoke(userDto, 10L);
        } catch (Exception ignored) {
        }

        assetDto = new AssetResponseDTO();
        try {
            assetDto.getClass().getMethod("setAssetId", Long.class).invoke(assetDto, 100L);
        } catch (Exception ignored) {
        }
    }

    @Test
    @DisplayName("create(): success path")
    void create_success() {
        // given
        when(userClient.getUserById(10L)).thenReturn(ResponseEntity.ok(userDto));
        when(assetClient.getAssetById(100L)).thenReturn(assetDto);

        // Use Mockito mock of PlaybackSession to avoid setters/getters
        PlaybackSession entityMock = mock(PlaybackSession.class);

        when(mapper.toEntity(any(PlaybackSessionRequestDTO.class))).thenReturn(entityMock);
        when(playbackRepository.save(any(PlaybackSession.class))).thenReturn(entityMock);
        when(mapper.toDto(entityMock)).thenReturn(responseDto);

        // when
        PlaybackSessionResponseDTO result = playbackService.create(requestDto, "10");

        // then
        assertThat(result).isSameAs(responseDto);
        verify(userClient).getUserById(10L);
        verify(assetClient).getAssetById(100L);
        verify(mapper).toEntity(any(PlaybackSessionRequestDTO.class));
        verify(playbackRepository).save(entityMock);
        verify(mapper).toDto(entityMock);
    }

    @Test
    @DisplayName("create(): invalid user id format")
    void create_invalidUserIdFormat() {
        GlobalException ex = assertThrows(GlobalException.class,
                () -> playbackService.create(requestDto, "abc"));

        assertThat(ex.getMessage()).contains("Invalid user id format");
        verifyNoInteractions(userClient, assetClient, playbackRepository, mapper);
    }

    @Test
    @DisplayName("create(): user not found (non-2xx ResponseEntity)")
    void create_userNotFound() {
        when(userClient.getUserById(10L)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        GlobalException ex = assertThrows(GlobalException.class,
                () -> playbackService.create(requestDto, "10"));

        assertThat(ex.getMessage()).contains("Error creating playback session: User not found");
        verify(userClient).getUserById(10L);
        verifyNoInteractions(assetClient, playbackRepository, mapper);
    }

    @Test
    @DisplayName("create(): assetId missing in request")
    void create_missingAssetId() {
        try {
            requestDto.getClass().getMethod("setAssetId", Long.class).invoke(requestDto, new Object[]{null});
        } catch (Exception ignored) {
        }

        when(userClient.getUserById(10L)).thenReturn(ResponseEntity.ok(userDto));

        GlobalException ex = assertThrows(GlobalException.class,
                () -> playbackService.create(requestDto, "10"));

        assertThat(ex.getMessage()).contains("Error creating playback session: assetId is required");
        verify(userClient).getUserById(10L);
        verifyNoInteractions(assetClient, playbackRepository, mapper);
    }

    @Test
    @DisplayName("create(): asset not found")
    void create_assetNotFound() {
        when(userClient.getUserById(10L)).thenReturn(ResponseEntity.ok(userDto));
        when(assetClient.getAssetById(100L)).thenReturn(null);

        GlobalException ex = assertThrows(GlobalException.class,
                () -> playbackService.create(requestDto, "10"));

        assertThat(ex.getMessage()).contains("Error creating playback session: Asset not found");
        verify(userClient).getUserById(10L);
    }
}