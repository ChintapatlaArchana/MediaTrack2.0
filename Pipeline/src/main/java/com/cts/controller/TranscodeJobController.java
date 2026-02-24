package com.cts.controller;

import com.cts.dto.TranscodeJobRequestDTO;
import com.cts.dto.TranscodeJobResponseDTO;
import com.cts.service.TranscodeJobService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transcode")
public class TranscodeJobController {

    private final TranscodeJobService transcodeJobService;

    public TranscodeJobController(TranscodeJobService transcodeJobService) {
        this.transcodeJobService = transcodeJobService;
    }

    @PostMapping
    public ResponseEntity<TranscodeJobResponseDTO> create(@RequestBody TranscodeJobRequestDTO dto) {
        return ResponseEntity.ok(transcodeJobService.createTranscodeJob(dto));
    }

    @GetMapping
    public ResponseEntity<List<TranscodeJobResponseDTO>> getAll() {
        return ResponseEntity.ok(transcodeJobService.getAllTranscodeJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranscodeJobResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transcodeJobService.getById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TranscodeJobResponseDTO> updateTranscodeStatusAndCompletedDate(
            @PathVariable Long id, @RequestBody TranscodeJobRequestDTO dto) {
        return ResponseEntity.ok(
                transcodeJobService.updateStatusAndCompletedDate(id, dto.getTranscodeStatus(), dto.getCompletedDate())
        );
    }

//    // NEW: Get all transcode jobs for a given asset
//    @GetMapping("/assets/{assetId}/transcode-jobs")
//    public ResponseEntity<List<TranscodeJobResponseDTO>> getJobsByAsset(@PathVariable Long assetId) {
//        return ResponseEntity.ok(transcodeJobService.getJobsForAsset(assetId));
//    }
}
