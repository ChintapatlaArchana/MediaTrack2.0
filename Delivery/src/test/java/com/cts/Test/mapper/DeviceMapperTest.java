package com.cts.Test.mapper;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.mapper.DeviceMapper;
import com.cts.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeviceMapperTest {

    @Mock
    private DeviceMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toEntity_ShouldBeCalled() {
        DeviceRequestDTO req = new DeviceRequestDTO();
        Device entity = new Device();

        when(mapper.toEntity(req)).thenReturn(entity);

        Device result = mapper.toEntity(req);

        assertNotNull(result);
        verify(mapper).toEntity(req);
    }

    @Test
    void toDto_ShouldBeCalled() {
        Device entity = new Device();
        DeviceResponseDTO dto = new DeviceResponseDTO();

        when(mapper.toDto(entity)).thenReturn(dto);

        DeviceResponseDTO result = mapper.toDto(entity);

        assertNotNull(result);
        verify(mapper).toDto(entity);
    }
}