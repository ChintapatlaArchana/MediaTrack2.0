package com.cts.service;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.repository.EngagementReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


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

    public Double getDAUMAUStats() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        Double ratio = engagementReportRepository.getLatestRatio();

        return ratio;
    }
}
