package com.cts.service;

import com.cts.dto.TranscodeJobRequestDTO;
import com.cts.dto.TranscodeJobResponseDTO;
import com.cts.feign.AssetFeignClient;
import com.cts.mapper.TranscodeJobMapper;
import com.cts.model.TranscodeJob;
import com.cts.model.TranscodeJob.TranscodeStatus;
import com.cts.repository.TranscodeJobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cts.dto.AssetResponseDTO;


@Service
public class TranscodeJobService {

    private final TranscodeJobRepository transcodeJobRepository;
    private final TranscodeJobMapper transcodeJobMapper;
    private final AssetFeignClient assetFeignClient;

    public TranscodeJobService(TranscodeJobRepository transcodeJobRepository,
                               TranscodeJobMapper transcodeJobMapper,
                               AssetFeignClient assetFeignClient) {
        this.transcodeJobRepository = transcodeJobRepository;
        this.transcodeJobMapper = transcodeJobMapper;
        this.assetFeignClient = assetFeignClient;
    }

    // Create a new transcode job tied to an asset
    public TranscodeJobResponseDTO createTranscodeJob(TranscodeJobRequestDTO dto) {
        TranscodeJob job = transcodeJobMapper.toEntity(dto);

        // Validate asset via asset-service
        AssetResponseDTO assetResponse = assetFeignClient.getAssetById(dto.getAssetId());
        if (assetResponse == null) {
            throw new RuntimeException("Asset not found with id: " + dto.getAssetId());
        }

        job.setAssetId(dto.getAssetId());

        return transcodeJobMapper.toDTO(transcodeJobRepository.save(job));
    }

    public List<TranscodeJobResponseDTO> getAllTranscodeJobs() {
        return transcodeJobRepository.findAll()
                .stream()
                .map(transcodeJobMapper::toDTO)
                .toList();
    }

    public TranscodeJobResponseDTO getById(Long id) {
        TranscodeJob job = transcodeJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transcode job not found with id: " + id));
        return transcodeJobMapper.toDTO(job);
    }

    public TranscodeJobResponseDTO updateStatusAndCompletedDate(Long id, TranscodeStatus status, LocalDate completedDate) {
        TranscodeJob job = transcodeJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transcode job not found with id: " + id));
        job.setTranscodeStatus(status);
        job.setCompletedDate(completedDate);
        job = transcodeJobRepository.save(job);
        return transcodeJobMapper.toDTO(job);
    }

    public Map<String, Long> getTranscodeMetrics() {
        long active = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Failed);
        long queued = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Queued);
        long completed = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Completed);

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("active", active);
        metrics.put("queued", queued);
        metrics.put("completed", completed);

        return metrics;
    }
    public double getTranscodePipelineHealth() {
        long failed = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Failed);
        long queued = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Queued);
        long completed = transcodeJobRepository.countByTranscodeStatus(TranscodeStatus.Completed);

        long total = failed + queued + completed;
        return total > 0 ? (completed * 100.0 / total) : 0.0;
    }


}
