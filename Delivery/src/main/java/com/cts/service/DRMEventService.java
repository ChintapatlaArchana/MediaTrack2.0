package com.cts.service;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.DRMEventMapper;
import com.cts.model.DRMEvent;
import com.cts.model.PlaybackSession;
import com.cts.repository.DRMEventRepository;
import com.cts.repository.PlaybackRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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

    public DRMEventResponseDTO create(DRMEventRequestDTO dto) {
        try {
            DRMEvent entity = drmEventMapper.toEntity(dto);

            if (dto.getPlaybackSessionId() == null) {
                throw new GlobalException("playbackSessionId is required");
            }

            PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                    .orElseThrow(() -> new GlobalException("PlaybackSession not found: " + dto.getPlaybackSessionId()));
            entity.setPlaybackSession(session);

            DRMEvent saved = drmEventRepository.save(entity);
            return drmEventMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error creating DRM event: " + ex.getMessage());
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DRMEventResponseDTO> findAll(Long sessionId) {
        try {
            List<DRMEvent> events = (sessionId == null)
                    ? drmEventRepository.findAll()
                    : drmEventRepository.findByPlaybackSession_SessionId(sessionId);
            return events.stream().map(drmEventMapper::toDto).toList();
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching DRM events: " + ex.getMessage());
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public DRMEventResponseDTO findById(Long id) {
        try {
            DRMEvent entity = drmEventRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("DRMEvent not found: " + id));
            return drmEventMapper.toDto(entity);
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching DRM event: " + ex.getMessage());
        }
    }

    public DRMEventResponseDTO update(Long id, DRMEventRequestDTO dto) {
        try {
            DRMEvent entity = drmEventRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("DRMEvent not found: " + id));

            // Update simple fields via mapper
            drmEventMapper.updateEntity(entity, dto);

            // Optionally update playbackSession if provided
            if (dto.getPlaybackSessionId() != null) {
                PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                        .orElseThrow(() -> new GlobalException("PlaybackSession not found: " + dto.getPlaybackSessionId()));
                entity.setPlaybackSession(session);
            }

            DRMEvent saved = drmEventRepository.save(entity);
            return drmEventMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error updating DRM event: " + ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            if (!drmEventRepository.existsById(id)) {
                throw new GlobalException("DRMEvent not found: " + id);
            }
            drmEventRepository.deleteById(id);
        } catch (GlobalException ex) {
            throw new GlobalException("Error deleting DRM event: " + ex.getMessage());
        }
    }
}