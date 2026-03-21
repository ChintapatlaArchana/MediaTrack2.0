package com.cts.controller;

import com.cts.dto.SubscriptionRequestDTO;

import com.cts.dto.SubscriptionResponseDTO;

import com.cts.service.SubscriptionService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

class SubscriptionControllerTest {

    @Mock

    private SubscriptionService subscriptionService;

    @InjectMocks

    private SubscriptionController subscriptionController;

    @BeforeEach

    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

//    @Test
//    void testCreateSubscription_Success() {
//        SubscriptionRequestDTO requestDTO = new SubscriptionRequestDTO();
//        SubscriptionResponseDTO responseDTO = new SubscriptionResponseDTO();
//        // Explicitly tell Mockito the return type
//        when(subscriptionService.create(eq(requestDTO), eq("123")))
//                .thenReturn(responseDTO);
//        ResponseEntity<SubscriptionResponseDTO> response =
//                subscriptionController.create(requestDTO, "123");
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(responseDTO, response.getBody());
//        verify(subscriptionService, times(1)).create(requestDTO, "123");
//    }

    @Test

    void testCreateSubscription_Failure() {

        SubscriptionRequestDTO requestDTO = new SubscriptionRequestDTO();

        when(subscriptionService.create(eq(requestDTO), eq("123")))

                .thenThrow(new IllegalArgumentException("User not found"));

        ResponseEntity<SubscriptionResponseDTO> response =

                subscriptionController.create(requestDTO, "123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("User not found", response.getBody());

        verify(subscriptionService, times(1)).create(requestDTO, "123");

    }

    @Test

    void testGetAllSubscriptions_Success() {

        SubscriptionResponseDTO responseDTO1 = new SubscriptionResponseDTO();

        SubscriptionResponseDTO responseDTO2 = new SubscriptionResponseDTO();

        List<SubscriptionResponseDTO> subscriptions = Arrays.asList(responseDTO1, responseDTO2);

        when(subscriptionService.getAllSubscriptions()).thenReturn(subscriptions);

        ResponseEntity<List<SubscriptionResponseDTO>> response =

                subscriptionController.getAllSubscriptions();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(subscriptions, response.getBody());

        verify(subscriptionService, times(1)).getAllSubscriptions();

    }

    @Test

    void testGetAllSubscriptions_Failure() {

        when(subscriptionService.getAllSubscriptions())

                .thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<SubscriptionResponseDTO>> response =

                subscriptionController.getAllSubscriptions();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertEquals("Database error", response.getBody());

        verify(subscriptionService, times(1)).getAllSubscriptions();

    }

}

