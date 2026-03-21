package com.cts.controller;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.service.PlanService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlanControllerTest {

    @Mock
    private PlanService planService;

    @InjectMocks
    private PlanController planController;

    public PlanControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlan() {
        PlanRequestDTO req = new PlanRequestDTO();
        req.setName("Basic");
        req.setPrice(99.0);
        req.setBillingCycle("Monthly");
        req.setStatus("Active");

        PlanResponseDTO resp = new PlanResponseDTO();
        resp.setPlanId(1L);
        resp.setName("Basic");

        when(planService.createPlan(req)).thenReturn(resp);

        ResponseEntity<PlanResponseDTO> response = planController.create(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getPlanId());
        assertEquals("Basic", response.getBody().getName());
        verify(planService, times(1)).createPlan(req);
    }

    @Test
    void testGetAllPlans() {
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setPlanId(1L);
        dto.setName("Basic");

        when(planService.getAllPlans()).thenReturn(List.of(dto));

        ResponseEntity<List<PlanResponseDTO>> response = planController.getAllPlans();

        assertEquals(HttpStatus.FOUND, response.getStatusCode()); // controller uses FOUND
        assertEquals(1, response.getBody().size());
        assertEquals("Basic", response.getBody().get(0).getName());
    }

    @Test
    void testGetPlanById() {
        PlanResponseDTO dto = new PlanResponseDTO();
        dto.setPlanId(5L);
        dto.setName("Yearly Pro");

        when(planService.getPlan(5L)).thenReturn(dto);

        ResponseEntity<PlanResponseDTO> response = planController.getPlanById(5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5L, response.getBody().getPlanId());
        assertEquals("Yearly Pro", response.getBody().getName());
    }
}
