package com.cts.service;

import com.cts.dto.AssetResponseDTO;
import com.cts.dto.IngestJobRequestDTO;
import com.cts.dto.IngestJobResponseDTO;
import com.cts.mapper.IngestJobMapper;
import com.cts.model.IngestJob;
import com.cts.model.IngestJob.IngestStatus;
import com.cts.repository.IngestJobRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cts.feign.AssetFeignClient;   // <-- use AssetFeignClient


@Service
public class IngestJobService {

    private final IngestJobRepository ingestJobRepository;
    private final IngestJobMapper ingestJobMapper;
    private final AssetFeignClient assetFeignClient; // <-- injected Feign client

    public IngestJobService(IngestJobRepository ingestJobRepository,
                            IngestJobMapper ingestJobMapper,
                            AssetFeignClient assetFeignClient) {
        this.ingestJobRepository = ingestJobRepository;
        this.ingestJobMapper = ingestJobMapper;
        this.assetFeignClient = assetFeignClient;
    }

    // Create a new ingest job tied to an asset (using AssetResponseDTO)
    public IngestJobResponseDTO createIngestJob(IngestJobRequestDTO dto) {
        IngestJob job = ingestJobMapper.toEntity(dto);

        // Validate asset via asset-service
        AssetResponseDTO assetResponse = assetFeignClient.getAssetById(dto.getAssetId());
        if (assetResponse == null) {
            throw new RuntimeException("Asset not found with id: " + dto.getAssetId());
        }

        // Store only the assetId in the job
        job.setAssetId(dto.getAssetId());

        return ingestJobMapper.toDTO(ingestJobRepository.save(job));
    }

    public List<IngestJobResponseDTO> getAllIngestJobs() {
        return ingestJobRepository.findAll()
                .stream()
                .map(ingestJobMapper::toDTO)
                .toList();
    }

    public IngestJobResponseDTO getById(Long id) {
        IngestJob job = ingestJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingest job not found with id: " + id));
        return ingestJobMapper.toDTO(job);
    }

    public IngestJobResponseDTO updateStatus(Long id, IngestJob.IngestStatus status) {
        IngestJob job = ingestJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingest job not found with id: " + id));

        job.setIngestStatus(status);
        job = ingestJobRepository.save(job);

        return ingestJobMapper.toDTO(job);
    }
    public Map<String, Long> getIngestMetrics() {
        long failed = ingestJobRepository.countByIngestStatus(IngestStatus.Failed);
        long queued = ingestJobRepository.countByIngestStatus(IngestStatus.Queued);
        long completed = ingestJobRepository.countByIngestStatus(IngestStatus.Completed);

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("failed", failed);
        metrics.put("queued", queued);
        metrics.put("completed", completed);

        return metrics;
    }
    public double getIngestPipelineHealth() {
        long failed = ingestJobRepository.countByIngestStatus(IngestStatus.Failed);
        long queued = ingestJobRepository.countByIngestStatus(IngestStatus.Queued);
        long completed = ingestJobRepository.countByIngestStatus(IngestStatus.Completed);

        long total = failed + queued + completed;
        return total > 0 ? (completed * 100.0 / total) : 0.0;
    }


}
