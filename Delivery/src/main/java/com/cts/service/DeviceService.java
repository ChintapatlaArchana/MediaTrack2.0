package com.cts.service;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.dto.UserResponseDTO;
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
        // Validate user exists in identity-service
        Long userId = Long.parseLong(id);
        ResponseEntity<UserResponseDTO> user = userClient.getUserById(userId);

        if(user.getStatusCode().is2xxSuccessful()) {
            Device device = deviceMapper.toEntity(dto);
            device.setUserId(user.getBody().getUserId());

            Device saved = deviceRepository.save(device);
            return new ResponseEntity(deviceMapper.toDto(saved), HttpStatus.OK);
        } else {
            throw new RuntimeException("User Id not found");
        }
        // Map & set userId (no JPA relation across services)
    }

    @Transactional(readOnly = true)
    public List<DeviceResponseDTO> findAll() {
        return deviceRepository.findAll().stream().map(deviceMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public DeviceResponseDTO getById(Long id) {
        Device entity = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found: " + id));
        return deviceMapper.toDto(entity);
    }

    public DeviceResponseDTO update(Long id, DeviceRequestDTO dto) {
        Device entity = deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found: " + id));

        // Re-validate if userId changes
        if (dto.getUserId() != null) {
            userClient.getUserById(dto.getUserId());
            entity.setUserId(dto.getUserId());
        }

        // deviceMapper.updateEntityFromDto(dto, entity); // if you have it
        // Or set other fields manually

        Device saved = deviceRepository.save(entity);
        return deviceMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new RuntimeException("Device not found: " + id);
        }
        deviceRepository.deleteById(id);
    }
}