//package com.cts.controller.ads;
//
//
//import com.cts.model.AdImpression;
//import com.cts.model.AdSlot;
//import com.cts.service.ads.AdImpressionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping
//public class AdImpressionController {
//    @Autowired
//    private AdImpressionService adImpressionService;
//
//    @PostMapping("/adimpression")
//    public AdImpression addAdImpression(@RequestBody AdImpression adImp) { return adImpressionService.addAdImpression(adImp); }
//
//    @GetMapping("/adimpression")
//    public List<AdImpression> getAllAdImpressions(){ return adImpressionService.getAllAdImpression(); }
//}
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

    @PostMapping("/{campaignId}/{slotId}/{sessionId}")
    public ResponseEntity<AdImpressionResponseDTO> create(
            @PathVariable Long campaignId,
            @PathVariable Long slotId,
            @PathVariable Long sessionId,
            @RequestBody AdImpressionRequestDTO dto
    ) {
        return ResponseEntity.ok(service.create(campaignId, slotId, sessionId, dto));
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