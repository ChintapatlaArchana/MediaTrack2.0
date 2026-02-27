package com.cts.test.ServiceTest;

import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.mapper.AdSlotMapper;
import com.cts.model.AdSlot;
import com.cts.repository.AdSlotRepository;

import com.cts.service.AdSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdSlotServiceTest {

    @Mock private AdSlotRepository repo;
    @Mock private AdSlotMapper mapper;

    @InjectMocks
    private AdSlotService service;

    private AdSlotRequestDTO req;
    private AdSlot entity;
    private AdSlot saved;
    private AdSlotResponseDTO resp;

    @BeforeEach
    void setup() {
        req = new AdSlotRequestDTO();
        req.setPlacement("PreRoll");
        req.setDuration(30);

        entity = new AdSlot();
        entity.setPlacement(AdSlot.Placement.PreRoll);
        entity.setDuration(30);

        saved = new AdSlot();
        saved.setSlotId(1L);
        saved.setPlacement(AdSlot.Placement.PreRoll);
        saved.setDuration(30);

        resp = new AdSlotResponseDTO();
        resp.setSlotId(1L);
        resp.setPlacement(AdSlot.Placement.PreRoll);
        resp.setDuration(30);
    }

    @Test
    void create_success() {
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(resp);

        AdSlotResponseDTO out = service.create(req);

        assertEquals(1L, out.getSlotId());
        verify(mapper).toEntity(req);
        verify(repo).save(entity);
        verify(mapper).toDTO(saved);
    }

    @Test
    void getAll_success() {
        when(repo.findAll()).thenReturn(List.of(saved));
        when(mapper.toDTO(saved)).thenReturn(resp);

        List<AdSlotResponseDTO> list = service.getAll();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getSlotId());
        verify(repo).findAll();
        verify(mapper).toDTO(saved);
    }

    @Test
    void getById_success() {
        when(repo.findById(1L)).thenReturn(Optional.of(saved));
        when(mapper.toDTO(saved)).thenReturn(resp);

        AdSlotResponseDTO out = service.getById(1L);

        assertEquals(1L, out.getSlotId());
        verify(repo).findById(1L);
    }

    @Test
    void getById_notFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class,
                () -> service.getById(999L));

        assertTrue(ex.getMessage().contains("AdSlot not found"));
        verify(repo).findById(999L);
    }

    @Test
    void update_success() {
        when(repo.findById(1L)).thenReturn(Optional.of(saved));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDTO(any())).thenReturn(resp);

        AdSlotResponseDTO out = service.update(1L, req);

        assertNotNull(out);
        verify(repo).findById(1L);
        verify(repo).save(any());
        verify(mapper).toDTO(any());
    }

    @Test
    void update_notFound() {
        when(repo.findById(5L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class,
                () -> service.update(5L, req));

        assertTrue(ex.getMessage().contains("AdSlot not found"));
        verify(repo).findById(5L);
        verify(repo, never()).save(any());
    }

    @Test
    void delete_success() {
        String result = service.delete(1L);

        assertEquals("AdSlot deleted successfully.", result);
        verify(repo).deleteById(1L);
    }
}
