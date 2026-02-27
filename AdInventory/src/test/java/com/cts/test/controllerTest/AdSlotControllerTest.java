package com.cts.test.controllerTest;

import com.cts.controller.AdSlotController;
import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.service.AdSlotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdSlotControllerTest {

    @Mock
    private AdSlotService service;

    @InjectMocks
    private AdSlotController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------ CREATE ----------------------
    @Test
    void testCreate() {
        AdSlotRequestDTO requestDTO = new AdSlotRequestDTO();
        AdSlotResponseDTO responseDTO = new AdSlotResponseDTO();

        when(service.create(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<AdSlotResponseDTO> response = controller.create(requestDTO);

        assertEquals(responseDTO, response.getBody());
        verify(service, times(1)).create(requestDTO);
    }

    // ------------------ GET ALL ----------------------
    @Test
    void testGetAll() {
        AdSlotResponseDTO dto1 = new AdSlotResponseDTO();
        AdSlotResponseDTO dto2 = new AdSlotResponseDTO();
        List<AdSlotResponseDTO> list = Arrays.asList(dto1, dto2);

        when(service.getAll()).thenReturn(list);

        ResponseEntity<List<AdSlotResponseDTO>> response = controller.getAll();

        assertEquals(2, response.getBody().size());
        verify(service, times(1)).getAll();
    }

    // ------------------ GET BY ID ----------------------
    @Test
    void testGetById() {
        Long id = 1L;
        AdSlotResponseDTO dto = new AdSlotResponseDTO();

        when(service.getById(id)).thenReturn(dto);

        ResponseEntity<AdSlotResponseDTO> response = controller.getById(id);

        assertEquals(dto, response.getBody());
        verify(service, times(1)).getById(id);
    }

    // ------------------ UPDATE ----------------------
    @Test
    void testUpdate() {
        Long id = 1L;
        AdSlotRequestDTO requestDTO = new AdSlotRequestDTO();
        AdSlotResponseDTO responseDTO = new AdSlotResponseDTO();

        when(service.update(id, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<AdSlotResponseDTO> response = controller.update(id, requestDTO);

        assertEquals(responseDTO, response.getBody());
        verify(service, times(1)).update(id, requestDTO);
    }

    // ------------------ DELETE ----------------------
    @Test
    void testDelete() {
        Long id = 1L;
        String message = "Deleted successfully";

        when(service.delete(id)).thenReturn(message);

        ResponseEntity<String> response = controller.delete(id);

        assertEquals(message, response.getBody());
        verify(service, times(1)).delete(id);
    }
}