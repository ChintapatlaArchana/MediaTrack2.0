package com.cts.service;

import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.exception.GlobalException;
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

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper, TitleRepository titleRepository) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
        this.titleRepository = titleRepository;
    }

    public AssetResponseDTO createAsset(AssetRequestDTO dto) {
        try {
            Asset asset = assetMapper.toEntity(dto);

            Title title = titleRepository.findById(dto.getTitleId())
                    .orElseThrow(() -> new GlobalException("Title not found with id: " + dto.getTitleId()));
            asset.setTitle(title);

            return assetMapper.toDto(assetRepository.save(asset));
        } catch (GlobalException ex) {
            throw new GlobalException("Error creating asset: " + ex.getMessage());
        }
    }

    public AssetResponseDTO getAsset(Long assetId) {
        try {
            Asset asset = assetRepository.findById(assetId)
                    .orElseThrow(() -> new GlobalException("Asset not found with id: " + assetId));
            return assetMapper.toDto(asset);
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching asset: " + ex.getMessage());
        }
    }

    public AssetResponseDTO updateAsset(Long assetId, AssetRequestDTO dto) {
        try {
            Asset exist = assetRepository.findById(assetId)
                    .orElseThrow(() -> new GlobalException("Asset not found with id: " + assetId));

            Asset updated = assetMapper.toEntity(dto);
            updated.setAssetId(exist.getAssetId());

            return assetMapper.toDto(assetRepository.save(updated));
        } catch (GlobalException ex) {
            throw new GlobalException("Error updating asset: " + ex.getMessage());
        }
    }

    public List<AssetResponseDTO> getAssetsByTitle(Long titleId) {
        try {
            if (!titleRepository.existsById(titleId)) {
                throw new GlobalException("Title not found with id: " + titleId);
            }
            return assetRepository.findByTitle_TitleId(titleId)
                    .stream()
                    .map(assetMapper::toDto)
                    .toList();
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching assets for title id: " + titleId);
        }
    }

    public void deleteAsset(Long id) {
        try {
            if (!assetRepository.existsById(id)) {
                throw new GlobalException("Asset not found with id: " + id);
            }
            assetRepository.deleteById(id);
        } catch (GlobalException ex) {
            throw new GlobalException("Error deleting asset: " + ex.getMessage());
        }
    }
}
