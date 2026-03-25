package com.cts.controller;


import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.service.AdDeliveryReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adDeliveryReport")
public class AdDeliveryReportController {


    public AdDeliveryReportController(AdDeliveryReportService adDeliveryReportService) {
        this.adDeliveryReportService = adDeliveryReportService;
    }

    private final AdDeliveryReportService adDeliveryReportService;

    @PostMapping
    public ResponseEntity<AdDeliveryReportResponseDTO> generateAdDeliveryReport(@RequestBody AdDeliveryReportRequestDTO dto){
        return ResponseEntity.ok(adDeliveryReportService.generateAdDeliveryReport(dto));
    }

    @GetMapping
    public ResponseEntity<List<AdDeliveryReportResponseDTO>> ListAdDeliveryReports(){
        return ResponseEntity.ok(adDeliveryReportService.getAllAdDeliveryReports());
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAdDeliveryReport(@PathVariable("id") Long id){
        adDeliveryReportService.deleteAdDeliveryReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/monthlyAdRevenue")
    public ResponseEntity<BigDecimal> getMonthlyAdRevenue() {
        return ResponseEntity.ok(adDeliveryReportService.getMonthlyAdRevenue());
    }
    //for frontend

    // Add to AdDeliveryReportController.java

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(adDeliveryReportService.getDashboardStats());
    }

    // AdDeliveryReportController.java

    @GetMapping("/stats/chart")
    public ResponseEntity<List<Map<String, Object>>> getPerformanceChart() {
        return ResponseEntity.ok(adDeliveryReportService.getChartData());
    }

    @GetMapping("/stats/summary")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        return ResponseEntity.ok(adDeliveryReportService.getDashboardWithGrowth());
    }
    // Inside AdDeliveryReportController.java

}
