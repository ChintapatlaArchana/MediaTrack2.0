//package com.cts.controller;
//
//import com.cts.dto.SubscriptionRequestDTO;
//import com.cts.dto.SubscriptionResponseDTO;
//import com.cts.service.SubscriptionService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(SubscriptionController.class)
//class SubscriptionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SubscriptionService subscriptionService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void create_shouldReturn201() throws Exception {
//        SubscriptionResponseDTO resp = new SubscriptionResponseDTO();
//        resp.setSubscriptionId(1L);
//
//        Mockito.when(subscriptionService.create(any(SubscriptionRequestDTO.class), eq("999")))
//                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(resp));
//
//        SubscriptionRequestDTO req = new SubscriptionRequestDTO();
//        req.setPlanId(10L);
//
//        mockMvc.perform(post("/subscription/add")
//                        .header("X-User-Id", "999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.subscriptionId").value(1));
//    }
//
//    @Test
//    void getAll_shouldReturn200() throws Exception {
//        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
//        dto.setSubscriptionId(1L);
//
//        Mockito.when(subscriptionService.getAllSubscriptions()).thenReturn(List.of(dto));
//
//        mockMvc.perform(get("/subscription/getAll"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].subscriptionId").value(1));
//    }
//}
