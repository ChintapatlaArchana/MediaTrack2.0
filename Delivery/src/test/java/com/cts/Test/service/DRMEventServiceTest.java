package com.cts.Test.service;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.mapper.DRMEventMapper;
import com.cts.model.DRMEvent;
import com.cts.model.PlaybackSession;
import com.cts.repository.DRMEventRepository;
import com.cts.repository.PlaybackRepository;
import com.cts.service.DRMEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DRMEventServiceTest {

    @Mock private DRMEventRepository drmRepo;
    @Mock private PlaybackRepository playbackRepo;
    @Mock private DRMEventMapper mapper;

    @InjectMocks
    private DRMEventService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        DRMEventRequestDTO req = new DRMEventRequestDTO();
        req.setPlaybackSessionId(1L);

        DRMEvent entity = new DRMEvent();
        DRMEvent saved = new DRMEvent();
        DRMEventResponseDTO dto = new DRMEventResponseDTO();

        PlaybackSession session = new PlaybackSession();
        session.setSessionId(1L);

        when(mapper.toEntity(req)).thenReturn(entity);
        when(playbackRepo.findById(1L)).thenReturn(Optional.of(session));
        when(drmRepo.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        DRMEventResponseDTO result = service.create(req);

        assertNotNull(result);
    }

    @Test
    void testFindById() {
        DRMEvent entity = new DRMEvent();
        DRMEventResponseDTO dto = new DRMEventResponseDTO();

        when(drmRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        assertNotNull(service.findById(1L));
    }

    @Test
    void testDelete() {
        when(drmRepo.existsById(1L)).thenReturn(true);
        doNothing().when(drmRepo).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
    }
}