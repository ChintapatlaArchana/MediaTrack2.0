package com.cts.service;
import com.cts.dto.*;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdImpressionService {

    private final AdImpressionRepository repo;
    private final CampaignRepository campaignRepo;
    private final AdSlotRepository adSlotRepo;
    private final AdImpressionMapper mapper;
    private final PlaybackSessionFeign playbackClient;

    public AdImpressionService(
            AdImpressionRepository repo,
            CampaignRepository campaignRepo,
            AdSlotRepository adSlotRepo,
            AdImpressionMapper mapper,
            PlaybackSessionFeign playbackClient
    ) {
        this.repo = repo;
        this.campaignRepo = campaignRepo;
        this.adSlotRepo = adSlotRepo;
        this.mapper = mapper;
        this.playbackClient = playbackClient;
    }

    // CREATE
    public AdImpressionResponseDTO create(Long campaignId, Long slotId, Long id, AdImpressionRequestDTO dto) {

        // Fetch campaign
        Campaign campaign = campaignRepo.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        // Fetch ad slot
        AdSlot slot = adSlotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("AdSlot not found"));

        //FEIGN CLIENT CALL
        PlaybackSessionResponseDTO session = playbackClient.getById(id);

        if (session == null) {
            throw new RuntimeException("Session not found in Delivery-Service");
        }

        // Convert DTO → Entity
        AdImpression entity = mapper.toEntity(dto);

        // Set relationships
        entity.setCampaign(campaign);
        entity.setAdSlot(slot);

        // Store only ID — microservice design
        entity.setSessionId(session.getSessionId());

        // Save
        AdImpression saved = repo.save(entity);

        // Convert Entity → DTO
        return mapper.toDTO(saved);
    }

    // GET ALL
    public List<AdImpressionResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public AdImpressionResponseDTO getById(Long id) {
        AdImpression entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("AdImpression not found"));
        return mapper.toDTO(entity);
    }

    // DELETE
    public String delete(Long id) {
        repo.deleteById(id);
        return "AdImpression deleted successfully";
    }
}