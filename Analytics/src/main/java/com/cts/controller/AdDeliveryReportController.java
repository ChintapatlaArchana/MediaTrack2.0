package com.cts.controller;


import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.service.AdDeliveryReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/adDeliveryReport")
public class AdDeliveryReportController {
    private final AdDeliveryReportService adDeliveryReportService;

    public AdDeliveryReportController(AdDeliveryReportService adDeliveryReportService) {
        this.adDeliveryReportService = adDeliveryReportService;
    }

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
}
