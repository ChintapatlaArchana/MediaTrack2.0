package com.cts.service;


import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.feign.CampaignFeignClient;
import com.cts.mapper.AdDeliveryReportMapper;
import com.cts.model.AdDeliveryReport;
import com.cts.repository.AdDeliveryReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdDeliveryReportService {

    private final AdDeliveryReportRepository adDeliveryReportRepository;
    private final AdDeliveryReportMapper adDeliveryReportMapper;
    private final CampaignFeignClient campaignFeignClient;

    public AdDeliveryReportService(AdDeliveryReportRepository adDeliveryReportRepository,
                                   AdDeliveryReportMapper adDeliveryReportMapper,
                                   CampaignFeignClient campaignFeignClient) {
        this.adDeliveryReportRepository = adDeliveryReportRepository;
        this.adDeliveryReportMapper = adDeliveryReportMapper;
        this.campaignFeignClient = campaignFeignClient;
    }

    public AdDeliveryReportResponseDTO generateAdDeliveryReport(AdDeliveryReportRequestDTO dto) {
        try {
            AdDeliveryReport adDeliveryReport = adDeliveryReportMapper.toEntity(dto);

            CampaignResponseDTO campaignResponseDTO = campaignFeignClient.getCampaignById(dto.getCampaignId());
            if (campaignResponseDTO == null) {
                throw new RuntimeException("Campaign not found with id: " + dto.getCampaignId());
            }

            adDeliveryReport.setCampaignId(campaignResponseDTO.getCampaignId());
            return adDeliveryReportMapper.toDto(adDeliveryReportRepository.save(adDeliveryReport));
        } catch (Exception ex) {
            throw new RuntimeException("Error generating ad delivery report: " + ex.getMessage(), ex);
        }
    }

    public List<AdDeliveryReportResponseDTO> getAllAdDeliveryReports() {
        try {
            List<AdDeliveryReportResponseDTO> reports = adDeliveryReportRepository.findAll()
                    .stream()
                    .map(adDeliveryReportMapper::toDto)
                    .toList();

            if (reports.isEmpty()) {
                throw new RuntimeException("No ad delivery reports found");
            }
            return reports;
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching ad delivery reports: " + ex.getMessage(), ex);
        }
    }

    public void deleteAdDeliveryReport(Long id) {
        try {
            if (adDeliveryReportRepository.existsById(id)) {
                adDeliveryReportRepository.deleteById(id);
            } else {
                throw new RuntimeException("Ad Delivery report does not exist with id: " + id);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting ad delivery report: " + ex.getMessage(), ex);
        }
    }
}
