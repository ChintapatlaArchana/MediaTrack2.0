package com.cts.controller;


import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.model.ContentVersion;
import com.cts.service.ContentVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/versions")
public class ContentVersionController {

    private final ContentVersionService contentVersionService;

    public ContentVersionController(ContentVersionService contentVersionService) {
        this.contentVersionService = contentVersionService;
    }

    @PostMapping
    public ResponseEntity<ContentVersionResponseDTO> createContentVersion(@RequestBody ContentVersionRequestDTO dto){
        return ResponseEntity.ok(contentVersionService.createContentVersion(dto));
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<ContentVersionResponseDTO>> listVersionByAsset(@PathVariable("id") Long assetId){
        return ResponseEntity.ok(contentVersionService.getVersionsByAsset(assetId));
    }

}
