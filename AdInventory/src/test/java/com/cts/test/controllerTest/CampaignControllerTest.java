package com.cts.test.controllerTest;

import com.cts.controller.CampaignController;
import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.service.CampaignService;

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

class CampaignControllerTest {

    @Mock
    private CampaignService service;

    @InjectMocks
    private CampaignController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------------- CREATE ----------------
    @Test
    void testCreate() {
        CampaignRequestDTO requestDTO = new CampaignRequestDTO();
        CampaignResponseDTO responseDTO = new CampaignResponseDTO();

        when(service.create(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<CampaignResponseDTO> response = controller.create(requestDTO);

        assertEquals(responseDTO, response.getBody());
        verify(service).create(requestDTO);
    }

    // ---------------- GET ALL ----------------
    @Test
    void testGetAll() {
        List<CampaignResponseDTO> list =
                Arrays.asList(new CampaignResponseDTO(), new CampaignResponseDTO());

        when(service.getAll()).thenReturn(list);

        ResponseEntity<List<CampaignResponseDTO>> response = controller.getAll();

        assertEquals(2, response.getBody().size());
        verify(service).getAll();
    }

    // ---------------- GET BY ID ----------------
    @Test
    void testGetById() {
        Long id = 1L;
        CampaignResponseDTO dto = new CampaignResponseDTO();

        when(service.getById(id)).thenReturn(dto);

        ResponseEntity<CampaignResponseDTO> response = controller.getById(id);

        assertEquals(dto, response.getBody());
        verify(service).getById(id);
    }

    // ---------------- UPDATE ----------------
    @Test
    void testUpdate() {
        Long id = 1L;
        CampaignRequestDTO req = new CampaignRequestDTO();
        CampaignResponseDTO resp = new CampaignResponseDTO();

        when(service.update(id, req)).thenReturn(resp);

        ResponseEntity<CampaignResponseDTO> response = controller.update(id, req);

        assertEquals(resp, response.getBody());
        verify(service).update(id, req);
    }

    // ---------------- DELETE ----------------
    @Test
    void testDelete() {
        Long id = 1L;
        String msg = "Campaign deleted successfully";

        when(service.delete(id)).thenReturn(msg);

        ResponseEntity<String> response = controller.delete(id);

        assertEquals(msg, response.getBody());
        verify(service).delete(id);
    }
}