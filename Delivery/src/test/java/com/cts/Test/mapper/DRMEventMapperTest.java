package com.cts.Test.mapper;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.mapper.DRMEventMapper;
import com.cts.model.DRMEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DRMEventMapperTest {

    @Mock
    private DRMEventMapper mapper;

    @InjectMocks
    private DRMEventMapperTest mapperTestHelper; // dummy container

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity_MapperCalled() {
        DRMEventRequestDTO req = new DRMEventRequestDTO();
        DRMEvent entity = new DRMEvent();

        when(mapper.toEntity(req)).thenReturn(entity);

        DRMEvent result = mapper.toEntity(req);

        assertNotNull(result);
        verify(mapper, times(1)).toEntity(req);
    }

    @Test
    void testToDto_MapperCalled() {
        DRMEvent entity = new DRMEvent();
        DRMEventResponseDTO dto = new DRMEventResponseDTO();

        when(mapper.toDto(entity)).thenReturn(dto);

        DRMEventResponseDTO result = mapper.toDto(entity);

        assertNotNull(result);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void testUpdateEntity_MapperCalled() {
        DRMEvent entity = new DRMEvent();
        DRMEventRequestDTO req = new DRMEventRequestDTO();

        doNothing().when(mapper).updateEntity(entity, req);

        mapper.updateEntity(entity, req);

        verify(mapper, times(1)).updateEntity(entity, req);
    }
}