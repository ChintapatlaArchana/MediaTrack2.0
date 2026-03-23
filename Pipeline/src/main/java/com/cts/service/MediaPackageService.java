package com.cts.service;

import com.cts.dto.MediaPackageRequestDTO;
import com.cts.dto.MediaPackageResponseDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.feign.AssetFeignClient;
import com.cts.mapper.MediaPackageMapper;
import com.cts.model.MediaPackage;
import com.cts.model.MediaPackage.QCStatus;
import com.cts.repository.MediaPackageRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MediaPackageService {

    private final MediaPackageRepository mediaPackageRepository;
    private final MediaPackageMapper mediaPackageMapper;
    private final AssetFeignClient assetFeignClient;

    public MediaPackageService(MediaPackageRepository mediaPackageRepository,
                               MediaPackageMapper mediaPackageMapper,
                               AssetFeignClient assetFeignClient) {
        this.mediaPackageRepository = mediaPackageRepository;
        this.mediaPackageMapper = mediaPackageMapper;
        this.assetFeignClient = assetFeignClient;
    }

    // Create a new media package tied to an asset
    public MediaPackageResponseDTO createMediaPackage(MediaPackageRequestDTO dto) {
        MediaPackage pkg = mediaPackageMapper.toEntity(dto);

        // Validate asset via asset-service
        AssetResponseDTO assetResponse = assetFeignClient.getAssetById(dto.getAssetId());
        if (assetResponse == null) {
            throw new RuntimeException("Asset not found with id: " + dto.getAssetId());
        }

        pkg.setAssetId(dto.getAssetId());

        return mediaPackageMapper.toDTO(mediaPackageRepository.save(pkg));
    }

    public List<MediaPackageResponseDTO> getAllMediaPackages() {
        return mediaPackageRepository.findAll()
                .stream()
                .map(mediaPackageMapper::toDTO)
                .toList();
    }

    public MediaPackageResponseDTO getById(Long id) {
        MediaPackage pkg = mediaPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MediaPackage not found with id: " + id));
        return mediaPackageMapper.toDTO(pkg);
    }

    public MediaPackageResponseDTO updateStatus(Long id, QCStatus status) {
        MediaPackage pkg = mediaPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media package not found with id: " + id));
        pkg.setQcStatus(status);
        pkg = mediaPackageRepository.save(pkg);
        return mediaPackageMapper.toDTO(pkg);
    }
    public Map<String, Long> getQcMetrics() {
        long passed = mediaPackageRepository.countByQcStatus(QCStatus.Passed);
        long failed = mediaPackageRepository.countByQcStatus(QCStatus.Failed);
        long pending = mediaPackageRepository.countByQcStatus(QCStatus.Pending);

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("passed", passed);
        metrics.put("failed", failed);
        metrics.put("pending", pending);

        return metrics;
    }
    public double getQcPassRate() {
        long passed = mediaPackageRepository.countByQcStatus(QCStatus.Passed);
        long failed = mediaPackageRepository.countByQcStatus(QCStatus.Failed);
        long pending = mediaPackageRepository.countByQcStatus(QCStatus.Pending);

        long total = passed + failed + pending;
        return total > 0 ? (passed * 100.0 / total) : 0.0;
    }

    public Map<String, Double> getQcStatusDistribution() {
        long passed = mediaPackageRepository.countByQcStatus(QCStatus.Passed);
        long failed = mediaPackageRepository.countByQcStatus(QCStatus.Failed);
        long pending = mediaPackageRepository.countByQcStatus(QCStatus.Pending);

        long total = passed + failed + pending;

        Map<String, Double> distribution = new HashMap<>();
        if (total > 0) {
            distribution.put("passed", (passed * 100.0) / total);
            distribution.put("failed", (failed * 100.0) / total);
            distribution.put("pending", (pending * 100.0) / total);
        } else {
            distribution.put("passed", 0.0);
            distribution.put("failed", 0.0);
            distribution.put("pending", 0.0);
        }

        return distribution;
    }

    public Map<String, Long> getFormatDistribution() {
        Map<String, Long> formatCounts = new HashMap<>();
        formatCounts.put("HLS", mediaPackageRepository.countByFormat("HLS"));
        formatCounts.put("DASH", mediaPackageRepository.countByFormat("DASH"));
        formatCounts.put("MSS", mediaPackageRepository.countByFormat("MSS"));
        return formatCounts;
    }


}
