package com.cts.test.ServiceTest;

import com.cts.dto.AdImpressionRequestDTO;
import com.cts.dto.AdImpressionResponseDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.feign.PlaybackSessionFeign;
import com.cts.mapper.AdImpressionMapper;
import com.cts.model.AdImpression;
import com.cts.model.AdSlot;
import com.cts.model.Campaign;
import com.cts.repository.AdImpressionRepository;
import com.cts.repository.AdSlotRepository;
import com.cts.repository.CampaignRepository;
import com.cts.service.AdImpressionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdImpressionServiceTest {

    private AdImpressionRepository repo;
    private CampaignRepository campaignRepo;
    private AdSlotRepository slotRepo;
    private AdImpressionMapper mapper;
    private PlaybackSessionFeign sessionFeign;
    private AdImpressionService service;

    @BeforeEach
    void setup() {
        repo = mock(AdImpressionRepository.class);
        campaignRepo = mock(CampaignRepository.class);
        slotRepo = mock(AdSlotRepository.class);
        mapper = mock(AdImpressionMapper.class);
        sessionFeign = mock(PlaybackSessionFeign.class);

        service = new AdImpressionService(repo, campaignRepo, slotRepo, mapper, sessionFeign);
    }

    @Test
    void testCreate() {
        Campaign campaign = new Campaign();
        AdSlot slot = new AdSlot();
        PlaybackSessionResponseDTO session = new PlaybackSessionResponseDTO();
        session.setSessionId(99L);

        AdImpressionRequestDTO dto = new AdImpressionRequestDTO();
        AdImpression entity = new AdImpression();
        AdImpression saved = new AdImpression();
        saved.setImpressionId(1L);

        AdImpressionResponseDTO responseDTO = new AdImpressionResponseDTO();
        responseDTO.setImpressionId(1L);

        when(campaignRepo.findById(10L)).thenReturn(Optional.of(campaign));
        when(slotRepo.findById(20L)).thenReturn(Optional.of(slot));
        when(sessionFeign.getById(30L)).thenReturn(session);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(responseDTO);

        AdImpressionResponseDTO result = service.create(10L, 20L, 30L, dto);

        assertEquals(1L, result.getImpressionId());
        verify(repo).save(entity);
    }

    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(List.of(new AdImpression()));
        when(mapper.toDTO(any())).thenReturn(new AdImpressionResponseDTO());

        var list = service.getAll();

        assertEquals(1, list.size());
    }

    @Test
    void testGetById() {
        AdImpression entity = new AdImpression();
        entity.setImpressionId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(new AdImpressionResponseDTO());

        assertNotNull(service.getById(1L));
    }

    @Test
    void testDelete() {
        String result = service.delete(1L);

        assertEquals("AdImpression deleted successfully", result);
        verify(repo).deleteById(1L);
    }
}
