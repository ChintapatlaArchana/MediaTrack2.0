package com.cts.test.ServiceTest;

import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.mapper.CreativeMapper;
import com.cts.model.Creative;
import com.cts.repository.CreativeRepository;
import com.cts.service.CreativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreativeServiceTest {

    @Mock
    private CreativeRepository repo;

    @Mock
    private CreativeMapper mapper;

    @InjectMocks
    private CreativeService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        CreativeRequestDTO dto = new CreativeRequestDTO();
        Creative entity = new Creative();
        Creative saved = new Creative();
        CreativeResponseDTO response = new CreativeResponseDTO();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(response);

        CreativeResponseDTO result = service.create(dto);

        assertNotNull(result);
        verify(repo).save(entity);
    }

    @Test
    void testGetById() {
        Creative entity = new Creative();
        CreativeResponseDTO response = new CreativeResponseDTO();

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(response);

        CreativeResponseDTO result = service.getById(1L);

        assertNotNull(result);
    }

    @Test
    void testGetAll() {
        Creative entity = new Creative();
        CreativeResponseDTO dto = new CreativeResponseDTO();

        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<CreativeResponseDTO> list = service.getAll();

        assertEquals(1, list.size());
    }

    @Test
    void testDelete() {
        service.delete(5L);
        verify(repo).deleteById(5L);
    }
}
