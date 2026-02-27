package com.cts.Test.mapper;

import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.mapper.PlaybackSessionMapper;
import com.cts.model.PlaybackSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlaybackSessionMapperTest {

    // Use the real MapStruct implementation as a Spy (so you can stub/verify if needed).
    @Spy
    private PlaybackSessionMapper mapper = Mappers.getMapper(PlaybackSessionMapper.class);

    @Test
    void toDto_mapsAllFieldsCorrectly() {
        // Arrange: build an entity with all fields filled
        PlaybackSession entity = new PlaybackSession();
        entity.setSessionId(1001L); // will map to response.sessionId
        entity.setUserId(2002L);
        entity.setAssetId(3003L);
        entity.setStartTime(LocalDateTime.of(2026, 2, 1, 10, 15, 30));
        entity.setEndTime(LocalDateTime.of(2026, 2, 1, 11, 45, 0));
        entity.setBitrateAvg(3750.5);
        entity.setBufferEvents(3);
        entity.setStatus(PlaybackSession.Status.COMPLETED);

        // Act
        PlaybackSessionResponseDTO dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1001L, dto.getSessionId());           // id -> sessionId
        assertEquals(2002L, dto.getUserId());
        assertEquals(3003L, dto.getAssetId());
        assertEquals(LocalDateTime.of(2026, 2, 1, 10, 15, 30), dto.getStartTime());
        assertEquals(LocalDateTime.of(2026, 2, 1, 11, 45, 0), dto.getEndTime());
        assertEquals(3750.5, dto.getBitrateAvg(), 0.0001);
        assertEquals(3, dto.getBufferEvents());
        assertEquals(PlaybackSession.Status.COMPLETED, dto.getStatus());
    }

    @Test
    void toDto_handlesNullsGracefully() {
        // Arrange: all fields null/defaults
        PlaybackSession entity = new PlaybackSession();

        // Act
        PlaybackSessionResponseDTO dto = mapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertNull(dto.getSessionId());
        assertNull(dto.getUserId());
        assertNull(dto.getAssetId());
        assertNull(dto.getStartTime());
        assertNull(dto.getEndTime());
        assertEquals(0.0, dto.getBitrateAvg(), 0.0); // primitive double defaults to 0.0 in entity if left unset
        assertEquals(0, dto.getBufferEvents());      // primitive int defaults to 0
        assertNull(dto.getStatus());
    }
}