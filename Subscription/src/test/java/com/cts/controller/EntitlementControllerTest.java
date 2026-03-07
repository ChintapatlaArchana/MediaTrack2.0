package com.cts.controller;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.service.EntitlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntitlementControllerTest {

    @Mock
    private EntitlementService entitlementService;

    @InjectMocks
    private EntitlementController entitlementController;

    // POST /entitlement/add - success:
    // Service returns ResponseEntity<EntitlementResponseDTO> with 201,
    // Controller wraps it into new ResponseEntity(..., 200 OK)
    @Test
    void create_shouldReturn200_withInner201_andDtoBody() {
        // Arrange
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");
        req.setGrantedDate(LocalDate.of(2026, 2, 1));
        req.setExpiryDate(LocalDate.of(2026, 3, 1));

        EntitlementResponseDTO dto = new EntitlementResponseDTO();
        dto.setEntitlementId(1L);
        dto.setUserId(999L);

        when(entitlementService.create(eq(req), eq("999")))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(dto));

        // Act
        ResponseEntity<?> response = entitlementController.create(req, "999");

        // Assert outer response (controller)
        assertEquals(HttpStatus.OK, response.getStatusCode()); // controller wraps with 200

        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ResponseEntity);

        // Unwrap inner ResponseEntity (service)
        @SuppressWarnings("unchecked")
        ResponseEntity<EntitlementResponseDTO> inner =
                (ResponseEntity<EntitlementResponseDTO>) response.getBody();

        assertEquals(HttpStatus.CREATED, inner.getStatusCode()); // from service
        assertNotNull(inner.getBody());
        assertEquals(1L, inner.getBody().getEntitlementId());
        assertEquals(999L, inner.getBody().getUserId());

        verify(entitlementService, times(1)).create(req, "999");
    }

    // POST /entitlement/add - failure:
    // Service throws IllegalArgumentException; controller catches and returns 404 with String body
    @Test
    void create_shouldReturn404_onIllegalArgument() {
        // Arrange
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");

        when(entitlementService.create(any(), any()))
                .thenThrow(new IllegalArgumentException("User Id not found"));

        // Act
        ResponseEntity<?> response = entitlementController.create(req, "404");

        // Assert outer response (controller)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertEquals("User Id not found", response.getBody());

        verify(entitlementService, times(1)).create(req, "404");
    }

    // GET /entitlement/getAll - success:
    // Controller returns 302 FOUND per your code
    @Test
    void getAllEntitlements_shouldReturn302_onSuccess() {
        // Arrange
        EntitlementResponseDTO dto = new EntitlementResponseDTO();
        dto.setEntitlementId(1L);
        dto.setUserId(111L);

        when(entitlementService.getAllEntitlements()).thenReturn(List.of(dto));

        // Act
        ResponseEntity<List<EntitlementResponseDTO>> response =
                entitlementController.getAllEntitlements();

        // Assert
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getEntitlementId());
        assertEquals(111L, response.getBody().get(0).getUserId());

        verify(entitlementService, times(1)).getAllEntitlements();
    }

    // GET /entitlement/getAll - failure:
    // Service throws IllegalArgumentException; controller returns 404 with String body
    @Test
    void getAllEntitlements_shouldReturn404_onIllegalArgument() {
        // Arrange
        when(entitlementService.getAllEntitlements())
                .thenThrow(new IllegalArgumentException("No Entitlements"));

        // Act
        ResponseEntity<?> response = entitlementController.getAllEntitlements();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertEquals("No Entitlements", response.getBody()); // <- actual body is the exception message
        verify(entitlementService, times(1)).getAllEntitlements();
    }
}
