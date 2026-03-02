package com.cts.controller;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.service.EntitlementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntitlementControllerTest {

    @Mock
    private EntitlementService entitlementService;

    @InjectMocks
    private EntitlementController entitlementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // POST /entitlement/add - success (controller returns 200 OK as per your code)
    @Test
    void create_shouldReturn200_onSuccess() {
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");
        req.setGrantedDate(LocalDate.of(2026, 2, 1));
        req.setExpiryDate(LocalDate.of(2026, 3, 1));

        EntitlementResponseDTO body = new EntitlementResponseDTO();
        body.setEntitlementId(1L);
        body.setUserId(999L);

        when(entitlementService.create(eq(req), eq("999")))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(body));

        ResponseEntity<EntitlementResponseDTO> response =
                entitlementController.create(req, "999");

        // Controller wraps service response into new ResponseEntity(..., OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getEntitlementId());
        verify(entitlementService, times(1)).create(req, "999");
    }

    // POST /entitlement/add - failure: controller returns 404 with String body
    @Test
    void create_shouldReturn404_onIllegalArgument() {
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");

        when(entitlementService.create(any(), any()))
                .thenThrow(new IllegalArgumentException("User Id not found"));

        ResponseEntity<EntitlementResponseDTO> response =
                entitlementController.create(req, "404");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Body type is String in this path; method signature uses EntitlementResponseDTO
        // so response.getBody() will be null.
        assertNull(response.getBody());
        verify(entitlementService, times(1)).create(req, "404");
    }

    // GET /entitlement/getAll - success (controller returns 302 FOUND per your code)
    @Test
    void getAllEntitlements_shouldReturn302_onSuccess() {
        EntitlementResponseDTO dto = new EntitlementResponseDTO();
        dto.setEntitlementId(1L);

        when(entitlementService.getAllEntitlements()).thenReturn(List.of(dto));

        ResponseEntity<List<EntitlementResponseDTO>> response =
                entitlementController.getAllEntitlements();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getEntitlementId());
        verify(entitlementService, times(1)).getAllEntitlements();
    }

    // GET /entitlement/getAll - failure: controller returns 404 with String message
    @Test
    void getAllEntitlements_shouldReturn404_onIllegalArgument() {
        when(entitlementService.getAllEntitlements())
                .thenThrow(new IllegalArgumentException("No Entitlements"));

        ResponseEntity<List<EntitlementResponseDTO>> response =
                entitlementController.getAllEntitlements();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(entitlementService, times(1)).getAllEntitlements();
    }
}