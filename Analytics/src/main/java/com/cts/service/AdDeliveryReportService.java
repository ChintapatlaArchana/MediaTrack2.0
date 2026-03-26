package com.cts.service;


import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.feign.CampaignFeignClient;
import com.cts.mapper.AdDeliveryReportMapper;
import com.cts.model.AdDeliveryReport;
import com.cts.repository.AdDeliveryReportRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // Add this to your existing AdDeliveryReportService.java for the frontend

//    public AdDeliveryReportMetricsDTO getDashboardMetrics() {
//        List<AdDeliveryReport> reports = adDeliveryReportRepository.findAll();
//        AdDeliveryReportMetricsDTO metrics = new AdDeliveryReportMetricsDTO();
//
//        // 1. Return empty metrics if no data exists to avoid Division by Zero
//        if (reports.isEmpty()) {
//            return metrics;
//        }
//
//        int totalImpressions = 0;
//        double totalClicks = 0;
//        double sumEcpm = 0;
//        double sumFillRate = 0;
//        double sumViewability = 0;
//
//        for (AdDeliveryReport r : reports) {
//            // 2. Reach into the @Embedded object correctly
//            if (r.getAdDeliveryReportMetrics() != null) {
//                var m = r.getAdDeliveryReportMetrics();
//
//                totalImpressions += m.getImpressions();
//
//                // Logic: (CTR / 100) * Impressions gives us the raw click count
//                totalClicks += (m.getCTR() / 100.0) * m.getImpressions();
//
//                // 3. FIX: Ensure you use getECPM() to match the 'eCPM' field in your DTO
//                sumEcpm += m.getECPM();
//                sumFillRate += m.getFillRate();
//                sumViewability += m.getViewabilityRate();
//            }
//        }
//
//        // 4. Set the final totals and averages into the DTO
//        metrics.setImpressions(totalImpressions);
//
//        int reportCount = reports.size();
//
//        // Calculate Averages
//        metrics.setECPM(sumEcpm / reportCount);
//        metrics.setFillRate(sumFillRate / reportCount);
//        metrics.setViewabilityRate(sumViewability / reportCount);
//
//        // Calculate overall Weighted CTR
//        if (totalImpressions > 0) {
//            metrics.setCTR((totalClicks / totalImpressions) * 100);
//        } else {
//            metrics.setCTR(0.0);
//        }
//
//        return metrics;
//    }

//    public AdDeliveryReportMetricsDTO getDashboardMetrics() {
//        List<AdDeliveryReport> reports = adDeliveryReportRepository.findAll();
//        AdDeliveryReportMetricsDTO metrics = new AdDeliveryReportMetricsDTO();
//
//        if (reports.isEmpty()) return metrics;
//
//        int totalImpressions = 0;
//        double totalClicks = 0;
//        double sumEcpm = 0;
//        double sumFill = 0;
//        double sumView = 0;
//
//        for (AdDeliveryReport r : reports) {
//            // You MUST check the embedded metrics object first
//            if (r.getAdDeliveryReportMetrics() != null) {
//                var m = r.getAdDeliveryReportMetrics();
//
//                totalImpressions += m.getImpressions();
//                // Calculate raw clicks from CTR percentage
//                totalClicks += (m.getCTR() / 100.0) * m.getImpressions();
//
//                // FIX: Use the exact getter for eCPM
//                sumEcpm += m.getECPM();
//                sumFill += m.getFillRate();
//                sumView += m.getViewabilityRate();
//            }
//        }
//
//        metrics.setImpressions(totalImpressions);
//
//        // Set Averages
//        int count = reports.size();
//        metrics.setECPM(sumEcpm / count);
//        metrics.setFillRate(sumFill / count);
//        metrics.setViewabilityRate(sumView / count);
//
//        if (totalImpressions > 0) {
//            metrics.setCTR((totalClicks / totalImpressions) * 100);
//        }
//
//        return metrics;
//    }
    // Inside AdDeliveryReportService.java

//    public List<Map<String, Object>> getChartData() {
//        List<AdDeliveryReport> reports = adDeliveryReportRepository.findAll();
//
//        // We transform the reports into a simple format for the Frontend Chart library
//        return reports.stream().map(report -> {
//            Map<String, Object> dataPoint = new HashMap<>();
//            dataPoint.put("date", report.getGeneratedDate());
//            dataPoint.put("impressions", report.getAdDeliveryReportMetrics().getImpressions());
//            dataPoint.put("ctr", report.getAdDeliveryReportMetrics().getCTR());
//            return dataPoint;
//        }).toList();
    }
}
