package com.cts.service;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.DeviceMapper;
import com.cts.model.Device;
import com.cts.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    public ResponseEntity<DeviceResponseDTO> create(DeviceRequestDTO dto, String id) {
        try {
            Long userId;
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException ex) {
                throw new GlobalException("Invalid user id format: " + id);
            }

            // Validate user exists in identity-service
            ResponseEntity<UserResponseDTO> user = userClient.getUserById(userId);

            if (user.getStatusCode().is2xxSuccessful() && user.getBody() != null) {
                Device device = deviceMapper.toEntity(dto);
                device.setUserId(user.getBody().getUserId());

                Device saved = deviceRepository.save(device);
                return new ResponseEntity<>(deviceMapper.toDto(saved), HttpStatus.OK);
            } else {
                throw new GlobalException("User Id not found");
            }
        } catch (GlobalException ex) {
            throw new GlobalException("Error creating device: " + ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<DeviceResponseDTO> findAll() {
        // Your friend's AssetService doesn't wrap simple list reads.
        // Keeping it straightforward here as well.
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeviceResponseDTO getById(Long id) {
        try {
            Device entity = deviceRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("Device not found: " + id));
            return deviceMapper.toDto(entity);
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching device: " + ex.getMessage());
        }
    }

    public DeviceResponseDTO update(Long id, DeviceRequestDTO dto) {
        try {
            Device entity = deviceRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("Device not found: " + id));

            // Re-validate if userId changes (identity check only, no relation)
            if (dto.getUserId() != null) {
                ResponseEntity<UserResponseDTO> user = userClient.getUserById(dto.getUserId());
                if (!user.getStatusCode().is2xxSuccessful() || user.getBody() == null) {
                    throw new GlobalException("User Id not found");
                }
                entity.setUserId(dto.getUserId());
            }

            // TODO: If you have a mapper update method, use it here
            // deviceMapper.updateEntityFromDto(dto, entity);
            // Otherwise, set other fields manually as needed (uncomment & adjust):
            // if (dto.getDeviceName() != null) entity.setDeviceName(dto.getDeviceName());
            // if (dto.getDeviceType() != null) entity.setDeviceType(dto.getDeviceType());
            // if (dto.getDeviceOs() != null) entity.setDeviceOs(dto.getDeviceOs());
            // if (dto.getDeviceVersion() != null) entity.setDeviceVersion(dto.getDeviceVersion());
            // if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

            Device saved = deviceRepository.save(entity);
            return deviceMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error updating device: " + ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            if (!deviceRepository.existsById(id)) {
                throw new GlobalException("Device not found: " + id);
            }
            deviceRepository.deleteById(id);
        } catch (GlobalException ex) {
            throw new GlobalException("Error deleting device: " + ex.getMessage());
        }
    }
}