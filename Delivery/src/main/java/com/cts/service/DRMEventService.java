package com.cts.service;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.mapper.DRMEventMapper;
import com.cts.model.DRMEvent;
import com.cts.model.PlaybackSession;
import com.cts.repository.DRMEventRepository;
import com.cts.repository.PlaybackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DRMEventService {

    private final DRMEventRepository drmEventRepository;
    private final PlaybackRepository playbackSessionRepository;
    private final DRMEventMapper drmEventMapper;

    public DRMEventService(DRMEventRepository drmEventRepository, PlaybackRepository playbackSessionRepository, DRMEventMapper drmEventMapper) {
        this.drmEventRepository = drmEventRepository;
        this.playbackSessionRepository = playbackSessionRepository;
        this.drmEventMapper = drmEventMapper;
    }

    public DRMEventResponseDTO create(DRMEventRequestDTO dto) {
        DRMEvent entity = drmEventMapper.toEntity(dto);

        if (dto.getPlaybackSessionId() == null) {
            throw new RuntimeException("playbackSessionId is required");
        }
        PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                .orElseThrow(() -> new RuntimeException("PlaybackSession not found: " + dto.getPlaybackSessionId()));
        entity.setPlaybackSession(session);

        DRMEvent saved = drmEventRepository.save(entity);
        return drmEventMapper.toDto(saved);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DRMEventResponseDTO> findAll(Long sessionId) {
        List<DRMEvent> events = (sessionId == null)
                ? drmEventRepository.findAll()
                : drmEventRepository.findByPlaybackSession_SessionId(sessionId);
        return events.stream().map(drmEventMapper::toDto).toList();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public DRMEventResponseDTO findById(Long id) {
        DRMEvent entity = drmEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DRMEvent not found: " + id));
        return drmEventMapper.toDto(entity);
    }

    public DRMEventResponseDTO update(Long id, DRMEventRequestDTO dto) {
        DRMEvent entity = drmEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DRMEvent not found: " + id));

        // Update simple fields via mapper
        drmEventMapper.updateEntity(entity, dto);

        // Optionally update playbackSession if provided
        if (dto.getPlaybackSessionId() != null) {
            PlaybackSession session = playbackSessionRepository.findById(dto.getPlaybackSessionId())
                    .orElseThrow(() -> new RuntimeException("PlaybackSession not found: " + dto.getPlaybackSessionId()));
            entity.setPlaybackSession(session);
        }

        DRMEvent saved = drmEventRepository.save(entity);
        return drmEventMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!drmEventRepository.existsById(id)) {
            throw new RuntimeException("DRMEvent not found: " + id);
        }
        drmEventRepository.deleteById(id);
    }
}