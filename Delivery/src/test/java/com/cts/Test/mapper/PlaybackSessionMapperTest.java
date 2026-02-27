package com.cts.mapper;

import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.model.PlaybackSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaybackSessionMapperBasicTest {

    // Use real MapStruct mapper
    private final PlaybackSessionMapper mapper =
            Mappers.getMapper(PlaybackSessionMapper.class);

    @Test
    void test_toDto_basic() {
        // Mock entity so we never call entity.setId() etc.
        PlaybackSession entity = Mockito.mock(PlaybackSession.class);

        when(entity.getId()).thenReturn(1L);
        when(entity.getStatus()).thenReturn(PlaybackSession.Status.ACTIVE);
        when(entity.getUserId()).thenReturn(10L);
        when(entity.getAssetId()).thenReturn(100L);

        // When
        PlaybackSessionResponseDTO dto = mapper.toDto(entity);

        // Then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStatus()).isEqualTo(PlaybackSession.Status.ACTIVE);
        assertThat(dto.getUserId()).isEqualTo(10L);
        assertThat(dto.getAssetId()).isEqualTo(100L);
    }

    @Test
    void test_toEntity_basic() {
        PlaybackSessionRequestDTO req = new PlaybackSessionRequestDTO();
        req.setStatus("ACTIVE");
        req.setAssetId(100L);

        PlaybackSession entity = mapper.toEntity(req);

        // Basic assertions (entity uses real getters)
        assertThat(entity.getStatus()).isEqualTo(PlaybackSession.Status.ACTIVE);
        assertThat(entity.getAssetId()).isEqualTo(100L);
    }
}