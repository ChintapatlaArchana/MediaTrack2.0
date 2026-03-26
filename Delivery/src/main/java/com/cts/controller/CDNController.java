package com.cts.controller;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.model.CDNEndpoint;
import com.cts.service.CDNService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cdn")
public class CDNController {

    private final CDNService cdnService;

    public CDNController(CDNService cdnService) {
        this.cdnService = cdnService;
    }

    @PostMapping
    public ResponseEntity<CDNEndpointResponseDTO> create(@RequestBody CDNEndpointRequestDTO request) {
        CDNEndpointResponseDTO created = cdnService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CDNEndpointResponseDTO>> findAll() {
        return ResponseEntity.ok(cdnService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CDNEndpointResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cdnService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CDNEndpointResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody CDNEndpointRequestDTO request) {
        return ResponseEntity.ok(cdnService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CDNEndpointResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam("value") CDNEndpoint.Status status
    ) {
        return ResponseEntity.ok(cdnService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cdnService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Long>> getMetrics() {
        return ResponseEntity.ok(cdnService.getCdnMetrics());
    }
    @GetMapping("/metrics/health")
    public ResponseEntity<Double> getCdnAvailability() {
        return ResponseEntity.ok(cdnService.getCdnAvailability());
    }


        //  Active Endpoints
        @GetMapping("/active")
        public ResponseEntity<Long> getActiveEndpoints() {
            return ResponseEntity.ok(cdnService.getActiveEndpoints());
        }

        // Total Endpoints
        @GetMapping("/total")
        public ResponseEntity<Long> getTotalEndpoints() {
            return ResponseEntity.ok(cdnService.getTotalEndpoints());
        }

        // Average Latency (availability proxy)
        @GetMapping("/average-latency")
        public ResponseEntity<Double> getAverageLatency() {
            return ResponseEntity.ok(cdnService.getAverageLatency());
        }

        //  Regional Metrics
        @GetMapping("/region/{region}")
        public ResponseEntity<Map<String, Object>> getRegionPerformance(@PathVariable String region) {
            return ResponseEntity.ok(cdnService.getRegionPerformance(region));
        }




}


