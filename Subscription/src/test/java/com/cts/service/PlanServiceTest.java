package com.cts.service;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.PlanMapper;
import com.cts.model.Plan;
import com.cts.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanServiceTest {

    @Test
    void createPlan_shouldSave_andReturnDTO() {
        PlanRepository repo = mock(PlanRepository.class);
        PlanMapper mapper = mock(PlanMapper.class);

        ObjectMapper objMapper = new ObjectMapper();

        PlanService service = new PlanService(repo, mapper, objMapper);

        PlanRequestDTO req = new PlanRequestDTO();
        req.setName("Basic");
        req.setPrice(99.0);
        req.setBillingCycle("Monthly");
        req.setStatus("Active");

        Plan entity = new Plan();
        entity.setPlanId(1L);
        entity.setName("Basic");

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);

        PlanResponseDTO out = new PlanResponseDTO();
        out.setPlanId(1L);
        out.setName("Basic");

        when(mapper.toDTO(entity)).thenReturn(out);

        PlanResponseDTO result = service.createPlan(req);

        assertNotNull(result);
        assertEquals(1L, result.getPlanId());
        assertEquals("Basic", result.getName());
        verify(repo, times(1)).save(entity);
    }

    @Test
    void getPlan_shouldReturnDTO_whenExists() {
        PlanRepository repo = mock(PlanRepository.class);
        PlanMapper mapper = mock(PlanMapper.class);
        ObjectMapper objMapper = new ObjectMapper();

        PlanService service = new PlanService(repo, mapper, objMapper);

        Plan entity = new Plan();
        entity.setPlanId(10L);
        entity.setName("Pro");

        when(repo.findById(10L)).thenReturn(Optional.of(entity));

        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setPlanId(10L);
        dto.setName("Pro");

        when(mapper.toDTO(entity)).thenReturn(dto);

        PlanResponseDTO result = service.getPlan(10L);

        assertEquals(10L, result.getPlanId());
        assertEquals("Pro", result.getName());
    }

    @Test
    void getAllPlans_shouldReturnList_whenDataExists() {
        PlanRepository repo = mock(PlanRepository.class);
        PlanMapper mapper = mock(PlanMapper.class);
        ObjectMapper objMapper = new ObjectMapper();

        PlanService service = new PlanService(repo, mapper, objMapper);

        Plan p = new Plan();
        p.setPlanId(1L);
        p.setName("Basic");

        when(repo.findAll()).thenReturn(List.of(p));

        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setPlanId(1L);
        dto.setName("Basic");

        when(mapper.toDTO(p)).thenReturn(dto);

        var list = service.getAllPlans();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getPlanId());
        assertEquals("Basic", list.get(0).getName());
    }

    @Test
    void getAllPlans_shouldThrowGlobalException_whenEmpty() {
        PlanRepository repo = mock(PlanRepository.class);
        PlanMapper mapper = mock(PlanMapper.class);
        ObjectMapper objMapper = new ObjectMapper();

        PlanService service = new PlanService(repo, mapper, objMapper);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(GlobalException.class, service::getAllPlans);
    }
}