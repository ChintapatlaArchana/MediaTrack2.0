package com.cts.service;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.repository.EngagementReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Map<String, Object> getAverageMetrics() {

        List<Object[]> resultsList = engagementReportRepository.findGlobalAverages();

        Map<String, Object> metrics = new HashMap<>();

        if (resultsList != null && !resultsList.isEmpty() && resultsList.get(0) != null) {
            Object[] results = resultsList.get(0);

            metrics.put("avgDau", formatToTwoDecimals(results[0]));
            metrics.put("avgMau", formatToTwoDecimals(results[1]));
            metrics.put("avgWatchTime", convertToMinutes(results[2]));
            metrics.put("avgCompletion", formatToTwoDecimals(results[3]));
        } else {
            metrics.put("avgDau", 0.0);
            metrics.put("avgMau", 0.0);
            metrics.put("avgWatchTime", 0.0);
            metrics.put("avgCompletion", 0.0);
        }

        return metrics;
    }

    private Double formatToTwoDecimals(Object val) {
        if (val == null) return 0.00;

        double doubleVal = (val instanceof Number) ? ((Number) val).doubleValue() : 0.0;

        return BigDecimal.valueOf(doubleVal)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Long convertToMinutes(Object val) {
        if (val == null) return 0L;
        double seconds = (val instanceof Number) ? ((Number) val).doubleValue() : 0.0;
        return Math.round(seconds / 60.0); // Rounds to the nearest whole minute
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
            Number seconds = (result[1] != null) ? (Number) result[1] : 0;
            long minutes = Math.round(seconds.doubleValue() / 60.0);
            map.put("watchTime", minutes);
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
