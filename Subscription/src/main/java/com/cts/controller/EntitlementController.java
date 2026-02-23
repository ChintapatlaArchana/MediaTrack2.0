package com.cts.controller;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.service.EntitlementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entitlement")
public class EntitlementController {

    private final EntitlementService entitlementService;

    public EntitlementController(EntitlementService entitlementService) {
        this.entitlementService = entitlementService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntitlementResponseDTO> create(@RequestBody EntitlementRequestDTO dto, @RequestHeader("X-User-Id") String id) {
        try {
            return new ResponseEntity(entitlementService.create(dto, id), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EntitlementResponseDTO>> getAllEntitlements() {
        try {
            return new ResponseEntity(entitlementService.getAllEntitlements(), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
