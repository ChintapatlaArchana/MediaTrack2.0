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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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

    public PlaybackSessionResponseDTO create(PlaybackSessionRequestDTO dto, String id) {
        try {
            Long userId;
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException nfe) {
                throw new GlobalException("Invalid user id format: " + id);
            }

            // Validate user from identity-service
            ResponseEntity<UserResponseDTO> userResp = userClient.getUserById(userId);
            if (userResp == null || !userResp.getStatusCode().is2xxSuccessful() || userResp.getBody() == null) {
                throw new GlobalException("User not found: " + userId);
            }

            // Validate asset from asset-service
            if (dto.getAssetId() == null) {
                throw new GlobalException("assetId is required");
            }
            AssetResponseDTO asset = assetClient.getAssetById(dto.getAssetId());
            if (asset == null || asset.getAssetId() == null) {
                throw new GlobalException("Asset not found: " + dto.getAssetId());
            }

            // Convert DTO → Entity
            PlaybackSession entity = mapper.toEntity(dto);
            entity.setUserId(userResp.getBody().getUserId());

            // Save
            PlaybackSession saved = playbackRepository.save(entity);

            // Convert back to DTO
            return mapper.toDto(saved);

        } catch (GlobalException ex) {
            throw new GlobalException("Error creating playback session: " + ex.getMessage());
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PlaybackSessionResponseDTO> getAll() {
        // Following your friend’s pattern, simple reads can stay straightforward
        List<PlaybackSession> entities = playbackRepository.findAll();
        return entities.stream().map(mapper::toDto).toList();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public PlaybackSessionResponseDTO getById(Long id) {
        try {
            PlaybackSession entity = playbackRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("PlaybackSession not found: " + id));
            return mapper.toDto(entity);
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching playback session: " + ex.getMessage());
        }
    }

    // Uncomment and adapt if/when you enable update
//    public PlaybackSessionResponseDTO update(Long id, PlaybackSessionRequestDTO dto) {
//        try {
//            PlaybackSession entity = playbackRepository.findById(id)
//                    .orElseThrow(() -> new GlobalException("PlaybackSession not found: " + id));
//
//            // Revalidate only if IDs changed
//            if (dto.getUserId() != null && !dto.getUserId().equals(entity.getUserId())) {
//                ResponseEntity<UserResponseDTO> userResp = userClient.getUserById(dto.getUserId());
//                if (userResp == null || !userResp.getStatusCode().is2xxSuccessful() || userResp.getBody() == null) {
//                    throw new GlobalException("User not found: " + dto.getUserId());
//                }
//            }
//            if (dto.getAssetId() != null && !dto.getAssetId().equals(entity.getAssetId())) {
//                AssetResponseDTO asset = assetClient.getAssetById(dto.getAssetId());
//                if (asset == null || asset.getAssetId() == null) {
//                    throw new GlobalException("Asset not found: " + dto.getAssetId());
//                }
//            }
//
//            // Map fields
//            mapper.updateEntity(entity, dto);
//
//            PlaybackSession saved = playbackRepository.save(entity);
//            return mapper.toDto(saved);
//        } catch (GlobalException ex) {
//            throw new GlobalException("Error updating playback session: " + ex.getMessage());
//        }
//    }

    public void delete(Long id) {
        try {
            if (!playbackRepository.existsById(id)) {
                throw new GlobalException("PlaybackSession not found: " + id);
            }
            playbackRepository.deleteById(id);
        } catch (GlobalException ex) {
            throw new GlobalException("Error deleting playback session: " + ex.getMessage());
        }
    }
}