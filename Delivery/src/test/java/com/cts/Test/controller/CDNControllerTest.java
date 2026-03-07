package com.cts.Test.controller;

import com.cts.controller.CDNController;
import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.model.CDNEndpoint;
import com.cts.service.CDNService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CDNControllerTest {

    @Mock
    private CDNService cdnService;

    @InjectMocks
    private CDNController cdnController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        CDNEndpointRequestDTO req = new CDNEndpointRequestDTO();
        CDNEndpointResponseDTO res = new CDNEndpointResponseDTO();

        when(cdnService.create(req)).thenReturn(res);

        ResponseEntity<CDNEndpointResponseDTO> response = cdnController.create(req);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(res, response.getBody());
    }

    @Test
    void testFindAll() {
        when(cdnService.findAll()).thenReturn(List.of(new CDNEndpointResponseDTO()));

        ResponseEntity<List<CDNEndpointResponseDTO>> response = cdnController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindById() {
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(cdnService.findById(1L)).thenReturn(dto);

        ResponseEntity<CDNEndpointResponseDTO> response = cdnController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        CDNEndpointRequestDTO req = new CDNEndpointRequestDTO();
        CDNEndpointResponseDTO res = new CDNEndpointResponseDTO();

        when(cdnService.update(1L, req)).thenReturn(res);

        ResponseEntity<CDNEndpointResponseDTO> response = cdnController.update(1L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(res, response.getBody());
    }

    @Test
    void testUpdateStatus() {
        CDNEndpointResponseDTO res = new CDNEndpointResponseDTO();

        when(cdnService.updateStatus(1L, CDNEndpoint.Status.Active)).thenReturn(res);

        ResponseEntity<CDNEndpointResponseDTO> response =
                cdnController.updateStatus(1L, CDNEndpoint.Status.Active);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(res, response.getBody());
    }

    @Test
    void testDelete() {
        doNothing().when(cdnService).delete(1L);

        ResponseEntity<Void> response = cdnController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(cdnService, times(1)).delete(1L);
    }
}