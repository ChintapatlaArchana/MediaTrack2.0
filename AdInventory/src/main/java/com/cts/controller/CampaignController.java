//package com.cts.controller.ads;
//
//
//import com.cts.model.Campaign;
//
//import com.cts.service.ads.CampaignService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//
//@RestController
//@RequestMapping("/")
//public class CampaignController {
//
//    @Autowired
//    private CampaignService campaignService;
//
//    @PostMapping("/campaign")
//    public Campaign addCampaign(@RequestBody Campaign camp) { return campaignService.addCampaign(camp); }
//
//    @GetMapping("/campaign")
//    public List<Campaign> getAllCampaigns(){return campaignService.getAllCampaigns(); }
//}
package com.cts.controller;

import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.service.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CampaignResponseDTO> create(@RequestBody CampaignRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }
    //frontend

    @GetMapping("/active-count")
    public ResponseEntity<Long> getActiveCount() {
        return ResponseEntity.ok(service.getActiveCount());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<CampaignResponseDTO>> getDashboardCampaigns() {
        return ResponseEntity.ok(service.getDashboardList());
    }


    @GetMapping
    public ResponseEntity<List<CampaignResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CampaignRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    // for frontend



    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}