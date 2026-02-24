package com.cts.controller;

import com.cts.dto.IngestJobRequestDTO;
import com.cts.dto.IngestJobResponseDTO;
import com.cts.exception.IngestJobException;
import com.cts.service.IngestJobService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingest")
public class IngestJobController {

    private final IngestJobService ingestJobService;

    public IngestJobController(IngestJobService ingestJobService) {
        this.ingestJobService = ingestJobService;
    }

    @PostMapping
    public ResponseEntity<IngestJobResponseDTO> create(@RequestBody IngestJobRequestDTO dto) {
        return ResponseEntity.ok(ingestJobService.createIngestJob(dto));
    }

    @GetMapping
    public ResponseEntity<List<IngestJobResponseDTO>> getAll() {
        return ResponseEntity.ok(ingestJobService.getAllIngestJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngestJobResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ingestJobService.getById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<IngestJobResponseDTO> updateIngestStatus(@PathVariable Long id,
                                                                   @RequestBody IngestJobRequestDTO dto) {
        return ResponseEntity.ok(ingestJobService.updateStatus(id, dto.getIngestStatus()));
    }

    // NEW: Get all ingest jobs for a given asset
//    @GetMapping("/assets/{assetId}/ingest-jobs")
//    public ResponseEntity<List<IngestJobResponseDTO>> getJobsByAsset(@PathVariable Long assetId) {
//        return ResponseEntity.ok(ingestJobService.getJobsForAsset(assetId));
//    }
}
