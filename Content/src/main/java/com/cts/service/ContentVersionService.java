package com.cts.service;

import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.mapper.ContentVersionMapper;
import com.cts.model.Asset;
import com.cts.model.ContentVersion;
import com.cts.repository.AssetRepository;
import com.cts.repository.ContentVersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentVersionService {

    public ContentVersionService(ContentVersionRepository contentVersionRepository, ContentVersionMapper contentVersionMapper,AssetRepository assetRepository) {
        this.contentVersionRepository = contentVersionRepository;
        this.contentVersionMapper = contentVersionMapper;
        this.assetRepository = assetRepository;
    }

    private final AssetRepository assetRepository;
    private final ContentVersionRepository contentVersionRepository;
    public final ContentVersionMapper contentVersionMapper;

    public ContentVersionResponseDTO createContentVersion(ContentVersionRequestDTO dto){
        ContentVersion contentVersion = contentVersionMapper.toEntity(dto);

        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(()->new RuntimeException("Asset not found"));
        contentVersion.setAsset(asset);

        return contentVersionMapper.toDto(contentVersionRepository.save(contentVersion));
    }

    public List<ContentVersionResponseDTO> getVersionsByAsset(Long assetId){
        return contentVersionRepository.findByAsset_AssetId(assetId).stream().map(contentVersionMapper::toDto).toList();
    }
}