package com.cts.controller;


import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.model.Asset;
import com.cts.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetResponseDTO> createAsset(@RequestBody AssetRequestDTO dto){
        return ResponseEntity.ok(assetService.createAsset(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> updateAsset(@PathVariable("id") Long assetId,@RequestBody AssetRequestDTO dto){
        return ResponseEntity.ok(assetService.updateAsset(assetId,dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getAssetById(@PathVariable("id") Long id){
        return ResponseEntity.ok(assetService.getAsset(id));
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<AssetResponseDTO>> listAssetsByTitle(@PathVariable("titleId") Long titleId){
        return ResponseEntity.ok(assetService.getAssetsByTitle(titleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

}
