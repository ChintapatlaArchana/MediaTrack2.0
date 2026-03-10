package com.cts.service;

import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.ContentVersionMapper;
import com.cts.model.Asset;
import com.cts.model.ContentVersion;
import com.cts.repository.AssetRepository;
import com.cts.repository.ContentVersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentVersionService {

    private final AssetRepository assetRepository;
    private final ContentVersionRepository contentVersionRepository;
    private final ContentVersionMapper contentVersionMapper;

    public ContentVersionService(ContentVersionRepository contentVersionRepository,
                                 ContentVersionMapper contentVersionMapper,
                                 AssetRepository assetRepository) {
        this.contentVersionRepository = contentVersionRepository;
        this.contentVersionMapper = contentVersionMapper;
        this.assetRepository = assetRepository;
    }

    public ContentVersionResponseDTO createContentVersion(ContentVersionRequestDTO dto) {
        ContentVersion contentVersion = contentVersionMapper.toEntity(dto);

        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new GlobalException("Asset not found with id: " + dto.getAssetId()));
        contentVersion.setAsset(asset);

        return contentVersionMapper.toDto(contentVersionRepository.save(contentVersion));
    }

    public List<ContentVersionResponseDTO> getVersionsByAsset(Long assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new GlobalException("Asset not found with id: " + assetId);
        }
        return contentVersionRepository.findByAsset_AssetId(assetId)
                .stream()
                .map(contentVersionMapper::toDto)
                .toList();
    }
}
