package com.cts.service;

import com.cts.dto.AssetResponseDTO;
import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.feign.AssetFeignClient;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.PlaybackSessionMapper;
import com.cts.model.PlaybackSession;
import com.cts.repository.PlaybackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

        public PlaybackSessionResponseDTO create(PlaybackSessionRequestDTO dto) {

            // Feign client validation (NO var here)
            ResponseEntity<UserResponseDTO> user = userClient.getUserById(dto.getUserId());
            AssetResponseDTO asset = assetClient.getAssetById(dto.getAssetId());

            // Convert DTO → Entity
            PlaybackSession entity = mapper.toEntity(dto);

            // Save in DB
            PlaybackSession saved = playbackRepository.save(entity);

            // Convert back to DTO
            PlaybackSessionResponseDTO response = mapper.toDto(saved);

            return response;
        }

        @Transactional(readOnly = true)
        public List<PlaybackSessionResponseDTO> getAll() {

            List<PlaybackSession> entities = playbackRepository.findAll();

            List<PlaybackSessionResponseDTO> dtos = entities
                    .stream()
                    .map(mapper::toDto)
                    .toList();

            return dtos;
        }

        @Transactional(readOnly = true)
        public PlaybackSessionResponseDTO getById(Long id) {

            PlaybackSession entity = playbackRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("PlaybackSession not found: " + id));

            PlaybackSessionResponseDTO dto = mapper.toDto(entity);

            return dto;
        }

//        public PlaybackSessionResponseDTO update(Long id, PlaybackSessionRequestDTO dto) {
//
//            PlaybackSession entity = playbackRepository.findById(id)
//                    .orElseThrow(() -> new IllegalArgumentException("PlaybackSession not found: " + id));
//
//            // Revalidate only if IDs changed
//            if (dto.getUserId() != null) {
//                UserResponseDTO user = userClient.getUserById(dto.getUserId());
//            }
//
//            if (dto.getAssetId() != null) {
//                AssetResponseDTO asset = assetClient.getAssetById(dto.getAssetId());
//            }
//
//            // Update fields on entity
//            mapper.updateEntity(entity, dto);
//
//            PlaybackSession saved = playbackRepository.save(entity);
//
//            PlaybackSessionResponseDTO response = mapper.toDto(saved);
//
//            return response;
//        }

        public void delete(Long id) {
            playbackRepository.deleteById(id);
        }
}


