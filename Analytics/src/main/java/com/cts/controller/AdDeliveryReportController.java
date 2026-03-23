package com.cts.controller;


import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.service.AdDeliveryReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    //for frontend
    // Inside AdDeliveryReportController.java

    @GetMapping("/metrics")
    public ResponseEntity<AdDeliveryReportMetricsDTO> getDashboardKpis() {
        // Calling the simple logic we just wrote in the Service
        return ResponseEntity.ok(adDeliveryReportService.getDashboardMetrics());
    }
    // Inside AdDeliveryReportController.java

    @GetMapping("/charts")
    public ResponseEntity<List<Map<String, Object>>> getChartData() {
        return ResponseEntity.ok(adDeliveryReportService.getChartData());
    }
}
