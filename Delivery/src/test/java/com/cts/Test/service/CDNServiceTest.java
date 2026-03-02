package com.cts.Test.service;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.mapper.CDNEndpointMapper;
import com.cts.model.CDNEndpoint;
import com.cts.repository.CDNRepository;
import com.cts.service.CDNService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CDNServiceTest {

    @Mock
    private CDNRepository cdnRepository;

    @Mock
    private CDNEndpointMapper mapper;

    @InjectMocks
    private CDNService cdnService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        CDNEndpointRequestDTO req = new CDNEndpointRequestDTO();
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpoint saved = new CDNEndpoint();
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(cdnRepository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        CDNEndpointResponseDTO result = cdnService.create(req);

        assertNotNull(result);
        verify(cdnRepository).save(entity);
    }

    @Test
    void testFindAll() {
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(cdnRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<CDNEndpointResponseDTO> result = cdnService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(cdnRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        CDNEndpointResponseDTO result = cdnService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        CDNEndpointRequestDTO req = new CDNEndpointRequestDTO();
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpoint saved = new CDNEndpoint();
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(cdnRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(cdnRepository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        CDNEndpointResponseDTO result = cdnService.update(1L, req);

        assertNotNull(result);
    }

    @Test
    void testUpdateStatus() {
        CDNEndpoint entity = new CDNEndpoint();
        CDNEndpoint saved = new CDNEndpoint();
        CDNEndpointResponseDTO dto = new CDNEndpointResponseDTO();

        when(cdnRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(cdnRepository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        CDNEndpointResponseDTO result =
                cdnService.updateStatus(1L, CDNEndpoint.Status.Active);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        CDNEndpoint entity = new CDNEndpoint();

        when(cdnRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(cdnRepository).delete(entity);

        assertDoesNotThrow(() -> cdnService.delete(1L));
    }
}