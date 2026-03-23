package com.cts.controller;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.service.DRMEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drm")
public class DRMEventController {

    private final DRMEventService drmEventService;

    public DRMEventController(DRMEventService drmEventService){
        this.drmEventService = drmEventService;
    }

    @PostMapping
    public ResponseEntity<DRMEventResponseDTO> create(@RequestBody DRMEventRequestDTO request) {
        DRMEventResponseDTO saved = drmEventService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<DRMEventResponseDTO>> findAll(@RequestParam(required = false) Long sessionId) {
        return ResponseEntity.ok(drmEventService.findAll(sessionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DRMEventResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(drmEventService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DRMEventResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody DRMEventRequestDTO request) {
        return ResponseEntity.ok(drmEventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        drmEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(drmEventService.getDrmMetrics());
    }

    @GetMapping("/metrics/health")
    public ResponseEntity<Double> getDrmSuccessRate() {
        return ResponseEntity.ok(drmEventService.getDrmSuccessRate());
    }


    @GetMapping("/granted")
    public ResponseEntity<Long> getGrantedCount() {
        return ResponseEntity.ok(drmEventService.getGrantedCount());
    }

    @GetMapping("/denied")
    public ResponseEntity<Long> getDeniedCount() {
        return ResponseEntity.ok(drmEventService.getDeniedCount());
    }

    @GetMapping("/expired")
    public ResponseEntity<Long> getExpiredCount() {
        return ResponseEntity.ok(drmEventService.getExpiredCount());
    }

    @GetMapping("/success-rate")
    public ResponseEntity<Double> getSuccessRate() {
        return ResponseEntity.ok(drmEventService.getSuccessRate());
    }

    @GetMapping("/distribution")
    public ResponseEntity<Map<String, Double>> getDrmTypeDistribution() {
        return ResponseEntity.ok(drmEventService.getDrmTypeDistribution());
    }
}


