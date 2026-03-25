package com.cts.controller;


import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.EngagementReportResponseDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.service.EngagementReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/engagementReport")
public class EngagementReportController {
    public EngagementReportController(EngagementReportService engagementReportService, EngagementReportMapper engagementReportMapper) {
        this.engagementReportService = engagementReportService;
        this.engagementReportMapper = engagementReportMapper;
    }

    private final EngagementReportService engagementReportService;
    private final EngagementReportMapper engagementReportMapper;

    @PostMapping
    public EngagementReportResponseDTO generateEngagementReport(@RequestBody EngagementReportRequestDTO dto){
        EngagementReport report = engagementReportService.generateEngagementReport(dto);
        return engagementReportMapper.toDto(report);
    }

    @GetMapping
    public List<EngagementReportResponseDTO> listEngagementReports(){
        return engagementReportService.getAllEngagementReports()
                .stream()
                .map(engagementReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngagementReport(@PathVariable Long id){
        engagementReportService.deleteEngagementReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/DAU-MAUStats")
    public ResponseEntity<Double> getDAUMAUStats() {
        return ResponseEntity.ok(engagementReportService.getDAUMAUStats());
    }
}
