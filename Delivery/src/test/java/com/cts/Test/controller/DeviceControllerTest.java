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
        DeviceResponseDTO res = new DeviceResponseDTO();

        when(deviceService.create(req, "123")).thenReturn(ResponseEntity.ok(res));

        ResponseEntity<DeviceResponseDTO> response =
                deviceController.create(req, "123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(res, response.getBody());
    }

    @Test
    void findAll_ShouldReturnList() {
        when(deviceService.findAll()).thenReturn(List.of(new DeviceResponseDTO()));

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_ShouldReturnDevice() {
        DeviceResponseDTO dto = new DeviceResponseDTO();

        when(deviceService.getById(1L)).thenReturn(dto);

        ResponseEntity<DeviceResponseDTO> response = deviceController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void update_ShouldReturnUpdatedDevice() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        DeviceResponseDTO updated = new DeviceResponseDTO();

        when(deviceService.update(1L, req)).thenReturn(updated);

        ResponseEntity<DeviceResponseDTO> response =
                deviceController.update(1L, req);

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