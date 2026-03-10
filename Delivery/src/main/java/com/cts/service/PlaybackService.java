package com.cts.service;

import com.cts.dto.AssetResponseDTO;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.AssetFeignClient;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.PlaybackSessionMapper;
import com.cts.model.PlaybackSession;
import com.cts.repository.PlaybackRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class PlaybackService {

    private final PlaybackRepository playbackRepository;
    private final PlaybackSessionMapper mapper;
    private final UserFeignClient userClient;
    private final AssetFeignClient assetClient;

    public PlaybackService(PlaybackRepository playbackRepository,
                           PlaybackSessionMapper mapper,
                           UserFeignClient userClient,
                           AssetFeignClient assetClient) {
        this.playbackRepository = playbackRepository;
        this.mapper = mapper;
        this.userClient = userClient;
        this.assetClient = assetClient;
    }

    // Create
    public PlaybackSessionResponseDTO create(PlaybackSessionRequestDTO dto, String id) {
        log.info("Creating playback session for user id (string): {}", id);
        try {
            Long userId;
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException nfe) {
                log.error("Invalid user id format: {}", id);
                throw new GlobalException("Invalid user id format: " + id);
            }

            // Validate user from identity-service
            ResponseEntity<UserResponseDTO> userResp = userClient.getUserById(userId);
            if (userResp == null || !userResp.getStatusCode().is2xxSuccessful() || userResp.getBody() == null) {
                log.error("User not found: {}", userId);
                throw new GlobalException("User not found: " + userId);
            }

            // Validate asset from asset-service
            if (dto.getAssetId() == null) {
                log.error("assetId is required");
                throw new GlobalException("assetId is required");
            }
            AssetResponseDTO asset = assetClient.getAssetById(dto.getAssetId());
            if (asset == null || asset.getAssetId() == null) {
                log.error("Asset not found: {}", dto.getAssetId());
                throw new GlobalException("Asset not found: " + dto.getAssetId());
            }

            // Convert DTO → Entity
            PlaybackSession entity = mapper.toEntity(dto);
            entity.setUserId(userResp.getBody().getUserId());

            // Save
            PlaybackSession saved = playbackRepository.save(entity);
            log.info("Playback session created successfully with id: {} for userId: {}", saved.getSessionId(), saved.getUserId());

            // Convert back to DTO
            return mapper.toDto(saved);

        } catch (GlobalException ex) {
            log.error("Error creating playback session: {}", ex.getMessage());
            throw new GlobalException("Error creating playback session: " + ex.getMessage());
        }
    }

    // Read All
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PlaybackSessionResponseDTO> getAll() {
        List<PlaybackSession> entities = playbackRepository.findAll();
        if (entities.isEmpty()) {
            log.error("No playback sessions found");
            throw new GlobalException("No playback sessions found");
        }
        log.info("Retrieved {} playback sessions", entities.size());
        return entities.stream().map(mapper::toDto).toList();
    }

    // Read by ID
    @Transactional(Transactional.TxType.SUPPORTS)
    public PlaybackSessionResponseDTO getById(Long id) {
        try {
            PlaybackSession entity = playbackRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("PlaybackSession not found: {}", id);
                        return new GlobalException("PlaybackSession not found: " + id);
                    });
            log.info("Found playback session with id: {}", id);
            return mapper.toDto(entity);
        } catch (GlobalException ex) {
            log.error("Error fetching playback session id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error fetching playback session: " + ex.getMessage());
        }
    }

    // Delete
    public void delete(Long id) {
        try {
            if (!playbackRepository.existsById(id)) {
                log.error("Cannot delete. PlaybackSession not found: {}", id);
                throw new GlobalException("PlaybackSession not found: " + id);
            }
            playbackRepository.deleteById(id);
            log.info("Deleted playback session with id: {}", id);
        } catch (GlobalException ex) {
            log.error("Error deleting playback session id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error deleting playback session: " + ex.getMessage());
        }
    }
}