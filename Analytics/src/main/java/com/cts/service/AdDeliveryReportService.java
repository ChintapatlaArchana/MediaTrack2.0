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

    public AdDeliveryReportService(AdDeliveryReportRepository adDeliveryReportRepository, AdDeliveryReportMapper adDeliveryReportMapper, CampaignFeignClient campaignFeignClient) {
        this.adDeliveryReportRepository = adDeliveryReportRepository;
        this.adDeliveryReportMapper = adDeliveryReportMapper;
        this.campaignFeignClient = campaignFeignClient;
    }

    private final AdDeliveryReportRepository adDeliveryReportRepository;
    private final AdDeliveryReportMapper adDeliveryReportMapper;
    private final CampaignFeignClient campaignFeignClient;

    public AdDeliveryReportResponseDTO generateAdDeliveryReport(AdDeliveryReportRequestDTO dto){
        AdDeliveryReport adDeliveryReport = adDeliveryReportMapper.toEntity(dto);
        CampaignResponseDTO campaignResponseDTO = campaignFeignClient.getCampaignById(dto.getCampaignId());

        adDeliveryReport.setCampaignId(campaignResponseDTO.getCampaignId());

        return adDeliveryReportMapper.toDto(adDeliveryReportRepository.save(adDeliveryReport));
    }

    public List<AdDeliveryReportResponseDTO> getAllAdDeliveryReports(){
        return adDeliveryReportRepository.findAll().stream().map(adDeliveryReportMapper::toDto).toList();
    }

    public void deleteAdDeliveryReport(Long id){
        if(adDeliveryReportRepository.existsById(id)){
            adDeliveryReportRepository.deleteById(id);
        }

        else {
            throw new RuntimeException("Ad Delivery report does not exist with id: "+id);
        }
    }
}
