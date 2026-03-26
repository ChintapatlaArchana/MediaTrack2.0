package com.cts.service;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.repository.EngagementReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EngagementReportService {

    private final EngagementReportRepository engagementReportRepository;
    private final EngagementReportMapper engagementReportMapper;

    public EngagementReportService(EngagementReportRepository engagementReportRepository,
                                   EngagementReportMapper engagementReportMapper) {
        this.engagementReportRepository = engagementReportRepository;
        this.engagementReportMapper = engagementReportMapper;
    }

    public EngagementReport generateEngagementReport(EngagementReportRequestDTO dto) {
        try {
            EngagementReport report = engagementReportMapper.toEntity(dto);
            return engagementReportRepository.save(report);
        } catch (Exception ex) {
            throw new RuntimeException("Error creating engagement report: " + ex.getMessage(), ex);
        }
    }

    public List<EngagementReport> getAllEngagementReports() {
        try {
            List<EngagementReport> reports = engagementReportRepository.findAll();
            if (reports.isEmpty()) {
                throw new RuntimeException("No engagement reports found");
            }
            return reports;
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching engagement reports: " + ex.getMessage(), ex);
        }
    }

    public void deleteEngagementReport(Long id) {
        try {
            if (engagementReportRepository.existsById(id)) {
                engagementReportRepository.deleteById(id);
            } else {
                throw new RuntimeException("Engagement report does not exist with id: " + id);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting engagement report: " + ex.getMessage(), ex);
        }
    }

    public Map<String, Double> getAverageMetrics() {
        Object[] results = (Object[]) engagementReportRepository.findGlobalAverages();

        Map<String, Double> metrics = new HashMap<>();
        // results[0] = avgDau, [1] = avgMau, [2] = avgWatchTime, [3] = avgCompletion
        metrics.put("avgDau", results[0] != null ? (Double) results[0] : 0.0);
        metrics.put("avgMau", results[1] != null ? (Double) results[1] : 0.0);
        metrics.put("avgWatchTime", results[2] != null ? (Double) results[2] : 0.0);
        metrics.put("avgCompletion", results[3] != null ? (Double) results[3] : 0.0);

        return metrics;
    }

    public List<Map<String, Object>> getDailyActiveTrends() {
        LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
        List<EngagementReport> reports = engagementReportRepository.findReportsInDateRange(ninetyDaysAgo);

        return reports.stream().map(report -> {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("date", report.getGeneratedDate().toString());
            dataPoint.put("dau", report.getEngagementReportMetrics().getDau());
            dataPoint.put("mau", report.getEngagementReportMetrics().getMau());
            return dataPoint;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getWatchTimeTrend() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<Object[]> results = engagementReportRepository.findWatchTimeTrend(thirtyDaysAgo);

        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", result[0].toString());
            // Convert seconds to minutes for a better chart display if needed
            map.put("watchTime", result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    public Double getDAUMAUStats() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        Double ratio = engagementReportRepository.getLatestRatio();

        return ratio;
    }
}
