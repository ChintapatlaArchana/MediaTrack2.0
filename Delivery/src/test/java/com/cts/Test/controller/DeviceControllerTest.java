package com.cts.Test.controller;

import com.cts.controller.DeviceController;
import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturn200() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        DeviceResponseDTO dto = new DeviceResponseDTO();

        // Service returns ResponseEntity (because your controller wraps it again)
        ResponseEntity<DeviceResponseDTO> serviceResponse = ResponseEntity.ok(dto);

        when(deviceService.create(req, "123")).thenReturn(serviceResponse);

        // Call controller
        ResponseEntity<?> response = deviceController.create(req, "123");

        // OUTER response
        assertEquals(200, response.getStatusCodeValue());

        // OUTER BODY is inner ResponseEntity
        assertTrue(response.getBody() instanceof ResponseEntity);

        ResponseEntity<DeviceResponseDTO> inner =
                (ResponseEntity<DeviceResponseDTO>) response.getBody();

        // INNER checks
        assertEquals(200, inner.getStatusCodeValue());
        assertEquals(dto, inner.getBody());

        verify(deviceService, times(1)).create(req, "123");
    }

    @Test
    void findAll_ShouldReturnList() {
        DeviceResponseDTO dto = new DeviceResponseDTO();
        when(deviceService.findAll()).thenReturn(List.of(dto));

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_ShouldReturn200() {
        DeviceResponseDTO dto = new DeviceResponseDTO();
        when(deviceService.getById(1L)).thenReturn(dto);

        ResponseEntity<DeviceResponseDTO> response = deviceController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void update_ShouldReturn200() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        DeviceResponseDTO updated = new DeviceResponseDTO();

        when(deviceService.update(1L, req)).thenReturn(updated);

        ResponseEntity<DeviceResponseDTO> response = deviceController.update(1L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void delete_ShouldReturn204() {
        doNothing().when(deviceService).delete(1L);

        ResponseEntity<DeviceResponseDTO> response = deviceController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(deviceService).delete(1L);
    }
}