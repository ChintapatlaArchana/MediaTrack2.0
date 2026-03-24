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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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


    // FOR FRONTEND DASHBOARD
    // Add to AdDeliveryReportService.java

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        Long totalImpressions = adDeliveryReportRepository.getTotalImpressions();
        Double avgCTR = adDeliveryReportRepository.getAverageCTR();
        Double avgECPM = adDeliveryReportRepository.getAverageECPM();
        Long activeCampaigns = adDeliveryReportRepository.getActiveCampaignCount();

        // Handling nulls in case the database is empty
        stats.put("totalImpressions", totalImpressions != null ? totalImpressions : 0);
        stats.put("ctr", avgCTR != null ? avgCTR : 0.0);
        stats.put("ecpm", avgECPM != null ? avgECPM : 0.0);
        stats.put("activeCampaigns", activeCampaigns != null ? activeCampaigns : 0);

        return stats;
    }
    // AdDeliveryReportService.java

    public List<Map<String, Object>> getChartData() {
        List<Object[]> results = adDeliveryReportRepository.getDailyChartData();
        List<Map<String, Object>> chartData = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("date", row[0]);
            dataPoint.put("impressions", row[1]);
            dataPoint.put("ctr", row[2]);
            chartData.add(dataPoint);
        }
        return chartData;
    }

    public Map<String, Object> getDashboardWithGrowth() {
        LocalDate now = LocalDate.now();

        // 1. Fetch the lists
        List<Object[]> currentList = adDeliveryReportRepository.getStatsForPeriod(now.minusDays(30), now);
        List<Object[]> previousList = adDeliveryReportRepository.getStatsForPeriod(now.minusDays(60), now.minusDays(31));

        // 2. Extract the actual data row (Object[]) from the list
        Object[] currentData = (currentList != null && !currentList.isEmpty()) ? currentList.get(0) : null;
        Object[] previousData = (previousList != null && !previousList.isEmpty()) ? previousList.get(0) : null;

        Map<String, Object> response = new HashMap<>();

        // 3. Use the extracted "Data" row, NOT the list
        long currImp = getAsLong(currentData, 0);
        response.put("totalImpressions", currImp);
        response.put("ctr", getAsDouble(currentData, 1));
        response.put("ecpm", getAsDouble(currentData, 2));
        response.put("activeCampaigns", getAsLong(currentData, 3));

        long prevImp = getAsLong(previousData, 0);
        if (prevImp > 0) {
            double growth = ((double) (currImp - prevImp) / prevImp) * 100;
            response.put("impressionGrowth", String.format("%+.1f%%", growth));
        } else {
            response.put("impressionGrowth", "+0%");
        }

        return response;
    }
    private long getAsLong(Object[] result, int index) {
        if (result == null || result.length <= index || result[index] == null) return 0L;
        return ((Number) result[index]).longValue();
    }

    private double getAsDouble(Object[] result, int index) {
        if (result == null || result.length <= index || result[index] == null) return 0.0;
        return ((Number) result[index]).doubleValue();
    }
}








