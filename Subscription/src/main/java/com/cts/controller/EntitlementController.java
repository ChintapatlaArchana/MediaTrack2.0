package com.cts.controller;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.record.ExpiryDistributionDTO;
import com.cts.record.PolicyDriftDTO;
import com.cts.service.EntitlementService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping("/admin/count")
    public ResponseEntity<Long> getCustomEntitlementCount() {
        return ResponseEntity.ok(entitlementService.getUniqueUserCount());
    }

    @GetMapping("/admin/entitlement-distribution")
    public ResponseEntity<Map<String, Long>> getEntitlementScopeDistribution() {
        return ResponseEntity.ok(entitlementService.getScopeDistribution());
    }

    @GetMapping("/admin/expiry-stats")
    public ResponseEntity<ExpiryDistributionDTO> getExpiringSoon() {
        return ResponseEntity.ok(entitlementService.getExpiringSoonStats());
    }

    @GetMapping("/admin/policy-drift")
    public ResponseEntity<Page<PolicyDriftDTO>> getPolicyDrift(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(entitlementService.getPolicyDriftData(page, size));
    }

//    @GetMapping("/admin/entitlements/policy-drift")
//    public ResponseEntity<Page<Entitlement>> getPolicyDrift(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("grantedDate").descending());
//        return ResponseEntity.ok(entitlementService.getEntitlements(pageable));
//    }
}
