//package com.cts.controller.ads;
//
//import com.cts.model.AdSlot;
//import com.cts.service.ads.AdSlotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//
//@RestController
//@RequestMapping("/")
//public class AdSlotController {
//
//    @Autowired
//    private AdSlotService adSlotService;
//
//    @PostMapping("/adslot")
//    public AdSlot addAdSlot(@RequestBody AdSlot ad) { return adSlotService.addAdSlot(ad); }
//
//    @GetMapping("/adslot")
//    public List<AdSlot> getAllAdSlots(){return adSlotService.getAllAdSlots(); }
//}
//

package com.cts.controller;

import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.service.AdSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adslot")
public class AdSlotController {

    private final AdSlotService service;

    public AdSlotController(AdSlotService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AdSlotResponseDTO> create(@RequestBody AdSlotRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AdSlotResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdSlotResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdSlotResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody AdSlotRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
