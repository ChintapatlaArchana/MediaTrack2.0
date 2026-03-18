package com.cts.controller;

import com.cts.dto.AdImpressionRequestDTO;
import com.cts.dto.AdImpressionResponseDTO;
import com.cts.service.AdImpressionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adimpression")
public class AdImpressionController {

    private final AdImpressionService service;

    public AdImpressionController(AdImpressionService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<AdImpressionResponseDTO> create(@RequestBody AdImpressionRequestDTO request) {
        // Pull IDs from the JSON body and pass them to the existing service method signature
        return ResponseEntity.ok(
                service.create(
                        request.getCampaignId(),
                        request.getSlotId(),
                        request.getSessionId(),
                        request
                )
        );
    }


    @GetMapping
    public ResponseEntity<List<AdImpressionResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdImpressionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}