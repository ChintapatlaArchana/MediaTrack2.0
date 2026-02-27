package com.cts.test.controllerTest;

import com.cts.controller.CreativeController;
import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.service.CreativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreativeControllerTest {

    private CreativeService service;
    private CreativeController controller;

    @BeforeEach
    void setup() {
        service = mock(CreativeService.class);
        controller = new CreativeController(service);
    }

    @Test
    void testCreate() {
        CreativeRequestDTO req = new CreativeRequestDTO();
        CreativeResponseDTO res = new CreativeResponseDTO();
        res.setCreativeId(1L);

        when(service.create(any())).thenReturn(res);

        var response = controller.create(req);

        assertEquals(1L, response.getBody().getCreativeId());
        verify(service).create(req);
    }

    @Test
    void testGetAll() {
        CreativeResponseDTO dto = new CreativeResponseDTO();
        dto.setCreativeId(10L);

        when(service.getAll()).thenReturn(java.util.List.of(dto));

        var response = controller.getAll();

        assertEquals(10L, response.getBody().get(0).getCreativeId());
        verify(service).getAll();
    }

    @Test
    void testGetById() {
        CreativeResponseDTO dto = new CreativeResponseDTO();
        dto.setCreativeId(5L);

        when(service.getById(5L)).thenReturn(dto);

        var response = controller.getById(5L);

        assertEquals(5L, response.getBody().getCreativeId());
        verify(service).getById(5L);
    }
}