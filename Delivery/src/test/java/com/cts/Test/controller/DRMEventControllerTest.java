package com.cts.Test.controller;

import com.cts.controller.DRMEventController;
import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.service.DRMEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DRMEventControllerTest {

    @Mock
    private DRMEventService drmEventService;

    @InjectMocks
    private DRMEventController drmEventController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test POST /drm/drmevent
    @Test
    void testCreate() {
        DRMEventRequestDTO req = new DRMEventRequestDTO();
        DRMEventResponseDTO res = new DRMEventResponseDTO();

        when(drmEventService.create(req)).thenReturn(res);

        ResponseEntity<DRMEventResponseDTO> response = drmEventController.create(req);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(res, response.getBody());
    }

    // Test GET /drm
    @Test
    void testFindAll() {
        List<DRMEventResponseDTO> list = List.of(new DRMEventResponseDTO());

        when(drmEventService.findAll(null)).thenReturn(list);

        ResponseEntity<List<DRMEventResponseDTO>> response = drmEventController.findAll(null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    // Test GET /drm/drmevent/{id}
    @Test
    void testFindById() {
        DRMEventResponseDTO dto = new DRMEventResponseDTO();

        when(drmEventService.findById(1L)).thenReturn(dto);

        ResponseEntity<DRMEventResponseDTO> response = drmEventController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    // Test PUT /drm/{id}
    @Test
    void testUpdate() {
        DRMEventRequestDTO req = new DRMEventRequestDTO();
        DRMEventResponseDTO updated = new DRMEventResponseDTO();

        when(drmEventService.update(1L, req)).thenReturn(updated);

        ResponseEntity<DRMEventResponseDTO> response = drmEventController.update(1L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    // Test DELETE /drm/{id}
    @Test
    void testDelete() {
        doNothing().when(drmEventService).delete(1L);

        ResponseEntity<Void> response = drmEventController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(drmEventService, times(1)).delete(1L);
    }
}