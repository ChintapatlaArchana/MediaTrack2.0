//package com.cts.controller;
//
//import com.cts.dto.PlanRequestDTO;
//import com.cts.dto.PlanResponseDTO;
//import com.cts.service.PlanService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.*;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = PlanController.class)
//class PlanControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PlanService planService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void create_shouldReturn201_onSuccess() throws Exception {
//        PlanResponseDTO resp = new PlanResponseDTO();
//        resp.setPlanId(1L);
//        resp.setName("Basic");
//
//        Mockito.when(planService.createPlan(any(PlanRequestDTO.class)))
//                .thenReturn(resp);
//
//        PlanRequestDTO req = new PlanRequestDTO();
//        req.setName("Basic");
//        req.setPrice(99.0);
//        req.setBillingCycle("Monthly");
//        req.setStatus("Active");
//        // entitlements can be null for a simple test
//
//        mockMvc.perform(post("/plan/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.planId").value(1))
//                .andExpect(jsonPath("$.name").value("Basic"));
//    }
//
//    @Test
//    void create_shouldReturn404_onIllegalArgument() throws Exception {
//        Mockito.when(planService.createPlan(any(PlanRequestDTO.class)))
//                .thenThrow(new IllegalArgumentException("Invalid plan"));
//
//        PlanRequestDTO req = new PlanRequestDTO();
//        req.setName("Invalid");
//
//        mockMvc.perform(post("/plan/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Invalid plan"));
//    }
//
//    @Test
//    void getAll_shouldReturn302_onSuccess() throws Exception {
//        PlanResponseDTO dto = new PlanResponseDTO();
//        dto.setPlanId(1L);
//        dto.setName("Basic");
//
//        Mockito.when(planService.getAllPlans())
//                .thenReturn(List.of(dto));
//
//        mockMvc.perform(get("/plan/getAll"))
//                .andExpect(status().isFound()) // your controller uses HttpStatus.FOUND (302)
//                .andExpect(jsonPath("$[0].planId").value(1))
//                .andExpect(jsonPath("$[0].name").value("Basic"));
//    }
//
//    @Test
//    void getAll_shouldReturn404_onError() throws Exception {
//        Mockito.when(planService.getAllPlans())
//                .thenThrow(new IllegalArgumentException("No Plan are created"));
//
//        mockMvc.perform(get("/plan/getAll"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("No Plan are created"));
//    }
//
//    @Test
//    void getPlanById_shouldReturn200_onSuccess() throws Exception {
//        PlanResponseDTO dto = new PlanResponseDTO();
//        dto.setPlanId(5L);
//        dto.setName("Yearly Pro");
//
//        Mockito.when(planService.getPlan(5L)).thenReturn(dto);
//
//        mockMvc.perform(get("/plan/id/{id}", 5))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.planId").value(5))
//                .andExpect(jsonPath("$.name").value("Yearly Pro"));
//    }
//
//    @Test
//    void getPlanById_shouldReturn404_onIllegalArgument() throws Exception {
//        Mockito.when(planService.getPlan(99L))
//                .thenThrow(new IllegalArgumentException("Not found"));
//
//        mockMvc.perform(get("/plan/id/{id}", 99))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Not found"));
//    }
//}