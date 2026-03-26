//package com.cts.controller.ads;
//
//import com.cts.model.Creative;
//import com.cts.service.ads.CreativeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//
//@RestController
//@RequestMapping("/")
//public class CreativeController {
//
//    @Autowired
//    private CreativeService creativeService;
//
//    @PostMapping("/creative")
//    public Creative addCreative(@RequestBody Creative creat) { return creativeService.addCreative(creat); }
//
//    @GetMapping("/creative")
//    public List<Creative> getAllCreatives(){ return creativeService.getAllCreatives(); }
//}
package com.cts.controller;

import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.service.CreativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/creative")
public class CreativeController {

    private final CreativeService service;

    public CreativeController(CreativeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreativeResponseDTO> create(@RequestBody CreativeRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CreativeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreativeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreativeResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CreativeRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/idAdvertiser")
    public ResponseEntity<Long> getCreativeId(@RequestParam String advertiser) {
        Long id = service.getCreativeIdByAdvertiser(advertiser);
        return ResponseEntity.ok(id);
    }
}