package com.cts.service;

import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.mapper.CampaignMapper;
import com.cts.model.Campaign;
import com.cts.model.Creative;
import com.cts.repository.CampaignRepository;
import com.cts.repository.CreativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepo;
    private final CreativeRepository creativeRepo;
    private final CampaignMapper mapper;

    public CampaignService(CampaignRepository campaignRepo,
                           CreativeRepository creativeRepo,
                           CampaignMapper mapper) {
        this.campaignRepo = campaignRepo;
        this.creativeRepo = creativeRepo;
        this.mapper = mapper;
    }

    // CREATE
//    public CampaignResponseDTO create(Long creativeId, CampaignRequestDTO dto) {
//
//        Creative creative = creativeRepo.findById(creativeId)
//                .orElseThrow(() -> new RuntimeException("Creative not found: " + creativeId));
//
//        Campaign entity = mapper.toEntity(dto);
//        entity.setCreative(creative);
//
//        return mapper.toDTO(campaignRepo.save(entity));
//    }
    public CampaignResponseDTO create(CampaignRequestDTO dto){
        Campaign campaign = mapper.toEntity(dto);
        Creative creative = creativeRepo.findById(dto.getCreativeId())
                .orElseThrow(()-> new RuntimeException("Creative not found with id"));
        campaign.setCreative(creative);
        return mapper.toDTO(campaignRepo.save(campaign));
    }

    // GET ALL
    public List<CampaignResponseDTO> getAll() {
        return campaignRepo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public CampaignResponseDTO getById(Long id) {
        Campaign entity = campaignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found: " + id));
        return mapper.toDTO(entity);
    }

    // UPDATE
    public CampaignResponseDTO update(Long id, CampaignRequestDTO dto) {

        Campaign existing = campaignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found: " + id));

        existing.setName(dto.getName());
        existing.setAdvertiser(dto.getAdvertiser());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setBudget(dto.getBudget());
        existing.setPacing(Campaign.Pacing.valueOf(dto.getPacing()));
        existing.setStatus(Campaign.Status.valueOf(dto.getStatus()));
        existing.setTargetingJSON(dto.getTargetingJSON().toString());

        return mapper.toDTO(campaignRepo.save(existing));
    }

    // DELETE
    public String delete(Long id) {
        campaignRepo.deleteById(id);
        return "Campaign deleted successfully";
    }
    // Add this method to your existing CampaignService class for the dashboard in the frontend


    // Inside CampaignService.java
    // Inside CampaignService.java

    public List<CampaignResponseDTO> getDashboardCampaigns() {
        // Define which statuses count as "Dashboard-ready"
        List<Campaign.Status> dashboardStatuses = List.of(
                Campaign.Status.Active,
                Campaign.Status.Paused
        );

        return campaignRepo.findDashboardCampaigns(dashboardStatuses)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }



    public long getActiveCount() {
        return campaignRepo.countByStatus(Campaign.Status.Active);
    }
}