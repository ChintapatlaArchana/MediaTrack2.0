package com.cts.service;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.DeviceMapper;
import com.cts.model.Device;
import com.cts.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final UserFeignClient userClient;

    public DeviceService(DeviceRepository deviceRepository,
                         DeviceMapper deviceMapper,
                         UserFeignClient userClient) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.userClient = userClient;
    }

    // Create
    public ResponseEntity<DeviceResponseDTO> create(DeviceRequestDTO dto, String id) {
        log.info("Creating device for user id (string): {}", id);

        try {
            Long userId;
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException ex) {
                log.error("Invalid user id format: {}", id);
                throw new GlobalException("Invalid user id format: " + id);
            }

            // Validate user exists in identity-service
            ResponseEntity<UserResponseDTO> user = userClient.getUserById(userId);

            if (user.getStatusCode().is2xxSuccessful() && user.getBody() != null) {
                Device device = deviceMapper.toEntity(dto);
                device.setUserId(user.getBody().getUserId());

                Device saved = deviceRepository.save(device);
                log.info("Device created successfully with id: {} for userId: {}", saved.getDeviceId(), saved.getUserId());
                return new ResponseEntity<>(deviceMapper.toDto(saved), HttpStatus.OK);
            } else {
                log.error("User Id not found: {}", userId);
                throw new GlobalException("User Id not found");
            }
        } catch (GlobalException ex) {
            log.error("Error creating device: {}", ex.getMessage());
            throw new GlobalException("Error creating device: " + ex.getMessage());
        }
    }

    // Read All
    @Transactional(readOnly = true)
    public List<DeviceResponseDTO> findAll() {
        List<Device> entities = deviceRepository.findAll();
        if (entities.isEmpty()) {
            log.error("No devices found");
            throw new GlobalException("No devices found");
        }
        log.info("Retrieved {} devices", entities.size());
        return entities.stream()
                .map(deviceMapper::toDto)
                .toList();
    }

    // Read by ID
    @Transactional(readOnly = true)
    public DeviceResponseDTO getById(Long id) {
        try {
            Device entity = deviceRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Device not found: {}", id);
                        return new GlobalException("Device not found: " + id);
                    });
            log.info("Found device with id: {}", id);
            return deviceMapper.toDto(entity);
        } catch (GlobalException ex) {
            log.error("Error fetching device id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error fetching device: " + ex.getMessage());
        }
    }

    // Update
    public DeviceResponseDTO update(Long id, DeviceRequestDTO dto) {
        try {
            Device entity = deviceRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Cannot update. Device not found: {}", id);
                        return new GlobalException("Device not found: " + id);
                    });

            // Re-validate if userId changes (identity check only, no relation)
            if (dto.getUserId() != null) {
                ResponseEntity<UserResponseDTO> user = userClient.getUserById(dto.getUserId());
                if (!user.getStatusCode().is2xxSuccessful() || user.getBody() == null) {
                    log.error("User Id not found: {}", dto.getUserId());
                    throw new GlobalException("User Id not found");
                }
                entity.setUserId(dto.getUserId());
            }

            // TODO: If you have a mapper update method, use it here
            // deviceMapper.updateEntityFromDto(dto, entity);
            // Otherwise, set other fields manually as needed:
            // if (dto.getDeviceName() != null) entity.setDeviceName(dto.getDeviceName());
            // if (dto.getDeviceType() != null) entity.setDeviceType(dto.getDeviceType());
            // if (dto.getDeviceOs() != null) entity.setDeviceOs(dto.getDeviceOs());
            // if (dto.getDeviceVersion() != null) entity.setDeviceVersion(dto.getDeviceVersion());
            // if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

            Device saved = deviceRepository.save(entity);
            log.info("Device updated successfully with id: {}", id);
            return deviceMapper.toDto(saved);
        } catch (GlobalException ex) {
            log.error("Error updating device id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error updating device: " + ex.getMessage());
        }
    }

    // Delete
    public void delete(Long id) {
        try {
            if (!deviceRepository.existsById(id)) {
                log.error("Cannot delete. Device not found: {}", id);
                throw new GlobalException("Device not found: " + id);
            }
            deviceRepository.deleteById(id);
            log.info("Deleted device with id: {}", id);
        } catch (GlobalException ex) {
            log.error("Error deleting device id {}: {}", id, ex.getMessage());
            throw new GlobalException("Error deleting device: " + ex.getMessage());
        }
    }
}