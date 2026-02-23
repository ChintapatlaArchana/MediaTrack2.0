package com.cts.service;

import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.mapper.AssetMapper;
import com.cts.model.Asset;
import com.cts.model.Title;
import com.cts.repository.AssetRepository;
import com.cts.repository.TitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final TitleRepository titleRepository;
    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper,TitleRepository titleRepository) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.titleRepository = titleRepository;
    }

    public AssetResponseDTO createAsset(AssetRequestDTO dto){
        Asset asset = assetMapper.toEntity(dto);

        Title title = titleRepository.findById(dto.getTitleId())
                .orElseThrow(()->new RuntimeException("Title not found"));
        asset.setTitle(title);

        return assetMapper.toDto(assetRepository.save(asset));
    }

    public AssetResponseDTO getAsset(Long assetId){
        return assetMapper.toDto(assetRepository.findById(assetId).orElseThrow());
    }

    public AssetResponseDTO updateAsset(Long assetId, AssetRequestDTO dto){
        Asset exist = assetRepository.findById(assetId).orElseThrow();
        Asset updated = assetMapper.toEntity(dto);
        updated.setAssetId(exist.getAssetId());
        return assetMapper.toDto(assetRepository.save(updated));
    }

    public List<AssetResponseDTO> getAssetsByTitle(Long titleId){
        return assetRepository.findByTitle_TitleId(titleId).stream().map(assetMapper::toDto).toList();
    }

    public void deleteAsset(Long id){
        if(!assetRepository.existsById(id)){
            throw new RuntimeException("Asset not found with id: "+id);
        }
        assetRepository.deleteById(id);
    }

}