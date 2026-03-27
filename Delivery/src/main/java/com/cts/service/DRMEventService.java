package com.cts.service;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.DRMEventMapper;
import com.cts.model.DRMEvent;
import com.cts.model.DRMEvent.LicenseStatus;
import com.cts.model.PlaybackSession;
import com.cts.repository.DRMEventRepository;
import com.cts.repository.PlaybackRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class DRMEventService {

    private final DRMEventRepository drmEventRepository;
    private final PlaybackRepository playbackSessionRepository;
    private final DRMEventMapper drmEventMapper;

    public DRMEventService(DRMEventRepository drmEventRepository,
                           PlaybackRepository playbackSessionRepository,
                           DRMEventMapper drmEventMapper) {
        this.drmEventRepository = drmEventRepository;
        this.playbackSessionRepository = playbackSessionRepository;
        this.drmEventMapper = drmEventMapper;
    }

    // Create
    public DRMEventResponseDTO create(DRMEventRequestDTO dto) {
        log.info("Creating DRM event for playbackSessionId: {}", dto.getPlaybackSessionId());
        try {
            DRMEvent entity = drmEventMapper.toEntity(dto);

            if (dto.getPlaybackSessionId() == null) {
                log.error("playbackSessionId is required");
                throw new GlobalException("playbackSessionId is required");
            }

            PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                    .orElseThrow(() -> {
                        log.error("PlaybackSession not found: {}", dto.getPlaybackSessionId());
                        return new GlobalException("PlaybackSession not found: " + dto.getPlaybackSessionId());
                    });

            entity.setPlaybackSession(session);

            DRMEvent saved = drmEventRepository.save(entity);
            log.info("DRM event created successfully with id: {}", saved.getDrmEventID());
            return drmEventMapper.toDto(saved);
        } catch (GlobalException ex) {
            log.error("Error creating DRM event: {}", ex.getMessage());
            throw new GlobalException("Error creating DRM event: " + ex.getMessage());
        }
    }

    // Read All (optionally filtered by playbackSessionId)
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DRMEventResponseDTO> findAll(Long sessionId) {
        try {
            List<DRMEvent> events = (sessionId == null)
                    ? drmEventRepository.findAll()
                    : drmEventRepository.findByPlaybackSession_SessionId(sessionId);

            if (events.isEmpty()) {
                if (sessionId == null) {
                    log.error("No DRM events found");
                    throw new GlobalException("No DRM events found");
                } else {
                    log.error("No DRM events found for playbackSessionId: {}", sessionId);
                    throw new GlobalException("No DRM events found for playbackSessionId: " + sessionId);
                }
            }

            log.info("Retrieved {} DRM events{}", events.size(),
                    sessionId == null ? "" : (" for playbackSessionId: " + sessionId));
            return events.stream().map(drmEventMapper::toDto).toList();
        } catch (GlobalException ex) {
            log.error("Error fetching DRM events: {}", ex.getMessage());
            throw new GlobalException("Error fetching DRM events: " + ex.getMessage());
        }
    }

    // Read by ID
    @Transactional(Transactional.TxType.SUPPORTS)
    public DRMEventResponseDTO findById(Long id) {
        try {
            DRMEvent entity = drmEventRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("DRMEvent not found: {}", id);
                        return new GlobalException("DRMEvent not found: " + id);
                    });
            log.info("Found DRM event with id: {}", id);
            return drmEventMapper.toDto(entity);
        } catch (GlobalException ex) {
            log.error("Error fetching DRM event id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error fetching DRM event: " + ex.getMessage());
        }
    }

    // Update
    public DRMEventResponseDTO update(Long id, DRMEventRequestDTO dto) {
        try {
            DRMEvent entity = drmEventRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Cannot update. DRMEvent not found: {}", id);
                        return new GlobalException("DRMEvent not found: " + id);
                    });

            // Update simple fields via mapper
            drmEventMapper.updateEntity(entity, dto);

            // Optionally update playbackSession if provided
            if (dto.getPlaybackSessionId() != null) {
                PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                        .orElseThrow(() -> {
                            log.error("PlaybackSession not found: {}", dto.getPlaybackSessionId());
                            return new GlobalException("PlaybackSession not found: " + dto.getPlaybackSessionId());
                        });
                entity.setPlaybackSession(session);
            }

            DRMEvent saved = drmEventRepository.save(entity);
            log.info("DRM event updated successfully with id: {}", id);
            return drmEventMapper.toDto(saved);
        } catch (GlobalException ex) {
            log.error("Error updating DRM event id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error updating DRM event: " + ex.getMessage());
        }
    }

    // Delete
    public void delete(Long id) {
        try {
            if (!drmEventRepository.existsById(id)) {
                log.error("Cannot delete. DRMEvent not found: {}", id);
                throw new GlobalException("DRMEvent not found: " + id);
            }
            drmEventRepository.deleteById(id);
            log.info("Deleted DRM event with id: {}", id);
        } catch (GlobalException ex) {
            log.error("Error deleting DRM event id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error deleting DRM event: " + ex.getMessage());
        }
    }
    public Map<String, Object> getDrmMetrics() {
        long granted = drmEventRepository.countByLicenseStatus(LicenseStatus.Granted);
        long denied = drmEventRepository.countByLicenseStatus(LicenseStatus.Denied);
        long total = granted + denied;

        double rate = (total > 0) ? ((double) granted / total) * 100 : 0.0;

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("granted", granted);
        metrics.put("denied", denied);
        metrics.put("rate", rate);

        return metrics;
    }
    public double getDrmSuccessRate() {
        long granted = drmEventRepository.countByLicenseStatus(DRMEvent.LicenseStatus.Granted);
        long denied = drmEventRepository.countByLicenseStatus(DRMEvent.LicenseStatus.Denied);

        long total = granted + denied;
        return total > 0 ? (granted * 100.0 / total) : 0.0;
    }

    // Licenses Granted
    public long getGrantedCount() {
        return drmEventRepository.countByLicenseStatus(LicenseStatus.Granted);
    }

    //  Licenses Denied
    public long getDeniedCount() {
        return drmEventRepository.countByLicenseStatus(LicenseStatus.Denied);
    }

    //Licenses Expired
    public long getExpiredCount() {
        return drmEventRepository.countByLicenseStatus(LicenseStatus.Expired);
    }

    //  Success Rate
    public double getSuccessRate() {
        long granted = drmEventRepository.countByLicenseStatus(LicenseStatus.Granted);
        long denied = drmEventRepository.countByLicenseStatus(LicenseStatus.Denied);
        long expired = drmEventRepository.countByLicenseStatus(LicenseStatus.Expired);

        long total = granted + denied + expired;
        return total > 0 ? (granted * 100.0 / total) : 0.0;
    }

    // DRM Type Distribution
    public Map<String, Double> getDrmTypeDistribution() {
        long total = drmEventRepository.count();

        Map<String, Double> distribution = new HashMap<>();
        long widevine = drmEventRepository.countByDrmType("Widevine");
        long playReady = drmEventRepository.countByDrmType("PlayReady");
        long fairPlay = drmEventRepository.countByDrmType("FairPlay");

        if (total > 0) {
            distribution.put("Widevine", (widevine * 100.0) / total);
            distribution.put("PlayReady", (playReady * 100.0) / total);
            distribution.put("FairPlay", (fairPlay * 100.0) / total);
        }
        return distribution;
    }




}