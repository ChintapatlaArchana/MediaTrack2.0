package com.cts.test.ServiceTest;

import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.mapper.CampaignMapper;
import com.cts.model.Campaign;
import com.cts.model.Creative;
import com.cts.repository.CampaignRepository;
import com.cts.repository.CreativeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    private CampaignRepository campaignRepo;
    private CreativeRepository creativeRepo;
    private CampaignMapper mapper;
    private com.cts.service.CampaignService service;

    @BeforeEach
    void init() {
        campaignRepo = mock(CampaignRepository.class);
        creativeRepo = mock(CreativeRepository.class);
        mapper = mock(CampaignMapper.class);
        service = new com.cts.service.CampaignService(campaignRepo, creativeRepo, mapper);
    }

    // ------------------ CREATE ------------------
    @Test
    void testCreate_Positive() {
        CampaignRequestDTO request = new CampaignRequestDTO();
        request.setCreativeId(10L);

        Campaign entity = new Campaign();
        Creative creative = new Creative();
        CampaignResponseDTO response = new CampaignResponseDTO();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(creativeRepo.findById(10L)).thenReturn(Optional.of(creative));
        when(campaignRepo.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(response);

        CampaignResponseDTO result = service.create(request);

        assertEquals(response, result);
        verify(campaignRepo).save(entity);
    }

    @Test
    void testCreate_WhenCreativeMissing() {
        CampaignRequestDTO request = new CampaignRequestDTO();
        request.setCreativeId(99L);

        when(creativeRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.create(request));
    }

    // ------------------ GET ALL ------------------
    @Test
    void testGetAll() {
        Campaign c1 = new Campaign();
        Campaign c2 = new Campaign();

        when(campaignRepo.findAll()).thenReturn(Arrays.asList(c1, c2));
        when(mapper.toDTO(any())).thenReturn(new CampaignResponseDTO());

        List<CampaignResponseDTO> result = service.getAll();

        assertEquals(2, result.size());
        verify(campaignRepo).findAll();
    }

    // ------------------ GET BY ID ------------------
    @Test
    void testGetById_Positive() {
        Campaign entity = new Campaign();
        CampaignResponseDTO dto = new CampaignResponseDTO();

        when(campaignRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        CampaignResponseDTO result = service.getById(1L);

        assertEquals(dto, result);
    }

    @Test
    void testGetById_NotFound() {
        when(campaignRepo.findById(55L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> service.getById(55L));
        assertEquals("Campaign not found: 55", ex.getMessage());
    }

    // ------------------ UPDATE ------------------
    @Test
    void testUpdate_Positive() {

        Campaign existing = new Campaign();   // ← NO setId needed

        // Prevent NullPointerException
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("city", "NY");

        CampaignRequestDTO request = new CampaignRequestDTO();
        request.setName("Updated Name");
        request.setAdvertiser("Sony");
        request.setPacing("Even");
        request.setStatus("Active");
        request.setTargetingJSON(jsonNode);

        CampaignResponseDTO responseDTO = new CampaignResponseDTO();

        when(campaignRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(campaignRepo.save(existing)).thenReturn(existing);
        when(mapper.toDTO(existing)).thenReturn(responseDTO);

        CampaignResponseDTO result = service.update(1L, request);

        assertEquals(responseDTO, result);
        verify(campaignRepo).save(existing);
    }

    @Test
    void testUpdate_NotFound() {
        when(campaignRepo.findById(88L)).thenReturn(Optional.empty());

        CampaignRequestDTO dto = new CampaignRequestDTO();

        Exception ex = assertThrows(RuntimeException.class,
                () -> service.update(88L, dto));

        assertEquals("Campaign not found: 88", ex.getMessage());
    }

    // ------------------ DELETE ------------------
    @Test
    void testDelete() {
        String msg = service.delete(10L);

        assertEquals("Campaign deleted successfully", msg);
        verify(campaignRepo).deleteById(10L);
    }
}