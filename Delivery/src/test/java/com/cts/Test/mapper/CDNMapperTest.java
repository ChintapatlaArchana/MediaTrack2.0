package com.cts.Test.mapper;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.mapper.CDNEndpointMapper;
import com.cts.model.CDNEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CDNMapperTest {

    @Mock
    private CDNEndpointMapper mapper;  // <-- Mocking the mapper

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testToEntity_WithMockito() {
        CDNEndpointRequestDTO request = new CDNEndpointRequestDTO();
        CDNEndpoint entity = new CDNEndpoint();

        when(mapper.toEntity(request)).thenReturn(entity);

        CDNEndpoint result = mapper.toEntity(request);

        assertNotNull(result);
        verify(mapper, times(1)).toEntity(request);
    }


    @Test
    void testToDto_WithMockito() {
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpointResponseDTO response = new CDNEndpointResponseDTO();

        when(mapper.toDto(entity)).thenReturn(response);

        CDNEndpointResponseDTO result = mapper.toDto(entity);

        assertNotNull(result);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void testMockDefaultStatus() {
        CDNEndpointRequestDTO request = new CDNEndpointRequestDTO();
        CDNEndpoint entity = new CDNEndpoint();
        entity.setStatus(CDNEndpoint.Status.Active);

        when(mapper.toEntity(request)).thenReturn(entity);

        CDNEndpoint result = mapper.toEntity(request);

        assertEquals(CDNEndpoint.Status.Active, result.getStatus());
        verify(mapper).toEntity(request);
    }
}