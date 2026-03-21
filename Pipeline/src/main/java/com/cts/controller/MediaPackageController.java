package com.cts.controller;

import com.cts.dto.MediaPackageRequestDTO;
import com.cts.dto.MediaPackageResponseDTO;
import com.cts.service.MediaPackageService;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/media")
public class MediaPackageController {

    private final MediaPackageService mediaPackageService;

    public MediaPackageController(MediaPackageService mediaPackageService) {
        this.mediaPackageService = mediaPackageService;
    }

    @PostMapping
    public ResponseEntity<MediaPackageResponseDTO> create(@RequestBody MediaPackageRequestDTO dto) {
        return ResponseEntity.ok(mediaPackageService.createMediaPackage(dto));
    }

    @GetMapping
    public ResponseEntity<List<MediaPackageResponseDTO>> getAll() {
        return ResponseEntity.ok(mediaPackageService.getAllMediaPackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaPackageResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mediaPackageService.getById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MediaPackageResponseDTO> updateStatus(@PathVariable Long id,
                                                                @RequestBody MediaPackageRequestDTO dto) {
        return ResponseEntity.ok(mediaPackageService.updateStatus(id, dto.getQcStatus()));
    }
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Long>> getQcMetrics() {
        return ResponseEntity.ok(mediaPackageService.getQcMetrics());
    }
    @GetMapping("/metrics/health")
    public ResponseEntity<Double> getQcPassRate() {
        return ResponseEntity.ok(mediaPackageService.getQcPassRate());
    }


    // NEW: Get all packages for a given asset
//    @GetMapping("/assets/{assetId}/packages")
//    public ResponseEntity<List<MediaPackageResponseDTO>> getPackagesByAsset(@PathVariable Long assetId) {
//        return ResponseEntity.ok(mediaPackageService.getPackagesForAsset(assetId));
//    }
}
