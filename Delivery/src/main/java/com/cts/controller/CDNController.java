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

@RestController
@RequestMapping("/")
public class CDNController {

    private final CDNService cdnService;

    public CDNController(CDNService cdnService) {
        this.cdnService = cdnService;
    }

    @PostMapping("/cdn")
    public ResponseEntity<CDNEndpointResponseDTO> create(@RequestBody CDNEndpointRequestDTO request) {
        CDNEndpointResponseDTO created = cdnService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/cdn")
    public ResponseEntity<List<CDNEndpointResponseDTO>> findAll() {
        return ResponseEntity.ok(cdnService.findAll());
    }

    @GetMapping("/cdn/{id}")
    public ResponseEntity<CDNEndpointResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cdnService.findById(id));
    }

    @PutMapping("/cdn/{id}")
    public ResponseEntity<CDNEndpointResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody CDNEndpointRequestDTO request) {
        return ResponseEntity.ok(cdnService.update(id, request));
    }

    @PatchMapping("/cdn/{id}/status")
    public ResponseEntity<CDNEndpointResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam("value") CDNEndpoint.Status status
    ) {
        return ResponseEntity.ok(cdnService.updateStatus(id, status));
    }

    @DeleteMapping("/cdn/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cdnService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


