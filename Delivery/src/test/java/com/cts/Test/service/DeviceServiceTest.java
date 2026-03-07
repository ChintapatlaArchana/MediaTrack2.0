package com.cts.Test.service;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.DeviceMapper;
import com.cts.model.Device;
import com.cts.repository.DeviceRepository;
import com.cts.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    @Mock private DeviceRepository deviceRepository;
    @Mock private DeviceMapper deviceMapper;
    @Mock private UserFeignClient userClient;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnDevice() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        Device entity = new Device();
        Device saved = new Device();
        DeviceResponseDTO response = new DeviceResponseDTO();

        UserResponseDTO user = new UserResponseDTO();
        user.setUserId(1L);

        when(userClient.getUserById(1L)).thenReturn(ResponseEntity.ok(user));
        when(deviceMapper.toEntity(req)).thenReturn(entity);
        when(deviceRepository.save(entity)).thenReturn(saved);
        when(deviceMapper.toDto(saved)).thenReturn(response);

        ResponseEntity<DeviceResponseDTO> result = deviceService.create(req, "1");

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void findAll_ShouldReturnDeviceList() {
        Device d = new Device();
        DeviceResponseDTO dto = new DeviceResponseDTO();

        when(deviceRepository.findAll()).thenReturn(List.of(d));
        when(deviceMapper.toDto(d)).thenReturn(dto);

        List<DeviceResponseDTO> list = deviceService.findAll();

        assertEquals(1, list.size());
    }

    @Test
    void getById_ShouldReturnDevice() {
        Device entity = new Device();
        DeviceResponseDTO dto = new DeviceResponseDTO();

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(deviceMapper.toDto(entity)).thenReturn(dto);

        assertNotNull(deviceService.getById(1L));
    }

    @Test
    void update_ShouldReturnUpdatedDevice() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        Device entity = new Device();
        Device saved = new Device();
        DeviceResponseDTO dto = new DeviceResponseDTO();

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(deviceRepository.save(entity)).thenReturn(saved);
        when(deviceMapper.toDto(saved)).thenReturn(dto);

        DeviceResponseDTO result = deviceService.update(1L, req);

        assertNotNull(result);
    }

    @Test
    void delete_WhenExists_ShouldPass() {
        when(deviceRepository.existsById(1L)).thenReturn(true);
        doNothing().when(deviceRepository).deleteById(1L);

        assertDoesNotThrow(() -> deviceService.delete(1L));
    }

    @Test
    void delete_WhenNotFound_ShouldThrow() {
        when(deviceRepository.existsById(1L)).thenReturn(false);

        assertThrows(GlobalException.class, () -> deviceService.delete(1L));
    }
}