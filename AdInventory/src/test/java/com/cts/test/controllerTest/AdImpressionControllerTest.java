package com.cts.test.controllerTest;

import com.cts.controller.AdImpressionController;
import com.cts.dto.AdImpressionRequestDTO;
import com.cts.dto.AdImpressionResponseDTO;
import com.cts.service.AdImpressionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdImpressionControllerTest {

    private AdImpressionService service;
    private AdImpressionController controller;

    @BeforeEach
    void setup() {
        service = mock(AdImpressionService.class);
        controller = new AdImpressionController(service);
    }

    @Test
    void testCreate() {
        AdImpressionRequestDTO request = new AdImpressionRequestDTO();

        AdImpressionResponseDTO response = new AdImpressionResponseDTO();
        response.setImpressionId(1L);

        when(service.create(10L, 20L, 30L, request)).thenReturn(response);

        var result = controller.create(10L, 20L, 30L, request);

        assertEquals(1L, result.getBody().getImpressionId());
        verify(service).create(10L, 20L, 30L, request);
    }

    @Test
    void testGetAll() {
        when(service.getAll()).thenReturn(java.util.List.of(new AdImpressionResponseDTO()));

        var result = controller.getAll();

        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void testGetById() {
        AdImpressionResponseDTO dto = new AdImpressionResponseDTO();
        dto.setImpressionId(5L);

        when(service.getById(5L)).thenReturn(dto);

        var result = controller.getById(5L);

        assertEquals(5L, result.getBody().getImpressionId());
    }

    @Test
    void testDelete() {
        when(service.delete(1L)).thenReturn("deleted");

        var result = controller.delete(1L);

        assertEquals("deleted", result.getBody());
        verify(service).delete(1L);
    }
}