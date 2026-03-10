package com.cts.service;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.mapper.EntitlementMapper;
import com.cts.model.Entitlement;
import com.cts.repository.EntitlementRepository;
import com.cts.feign.UserFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntitlementServiceTest {

    @Test
    void create_shouldReturn201_onSuccess() {
        EntitlementRepository repo = mock(EntitlementRepository.class);
        EntitlementMapper mapper = mock(EntitlementMapper.class);
        UserFeignClient feign = mock(UserFeignClient.class);

        EntitlementService service = new EntitlementService(repo, mapper, feign);

        // Arrange input
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");
        req.setGrantedDate(LocalDate.of(2026, 2, 1));
        req.setExpiryDate(LocalDate.of(2026, 3, 1));

        // Mock Feign user success
        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setUserId(999L);
        when(feign.getUserById(999L)).thenReturn(ResponseEntity.ok(userDto));

        // Mock mapper toEntity
        Entitlement ent = new Entitlement();
        ent.setContentScope(Entitlement.ContentScope.All);
        when(mapper.toEntity(req)).thenReturn(ent);

        // Mock save
        Entitlement saved = new Entitlement();
        saved.setEntitlementId(1L);
        saved.setUserId(999L);
        saved.setContentScope(Entitlement.ContentScope.All);
        when(repo.save(ent)).thenReturn(saved);

        // Mock toDto
        EntitlementResponseDTO out = new EntitlementResponseDTO();
        out.setEntitlementId(1L);
        out.setUserId(999L);
        when(mapper.toDto(saved)).thenReturn(out);

        // Act
        ResponseEntity<EntitlementResponseDTO> response = service.create(req, "999");

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getEntitlementId());
        assertEquals(999L, response.getBody().getUserId());
        verify(repo, times(1)).save(ent);
    }

    @Test
    void create_shouldReturn404_onFeignClientException() {
        EntitlementRepository repo = mock(EntitlementRepository.class);
        EntitlementMapper mapper = mock(EntitlementMapper.class);
        UserFeignClient feign = mock(UserFeignClient.class);

        EntitlementService service = new EntitlementService(repo, mapper, feign);

        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All");

        // Simulate Feign client exception (simplest: throw a runtime FeignException)
        FeignException ex = mock(FeignException.FeignClientException.class);
        when(feign.getUserById(123L)).thenThrow(ex);

        ResponseEntity<EntitlementResponseDTO> response = service.create(req, "123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody()); // body is message String in your code, but typed as EntitlementResponseDTO
        verify(repo, never()).save(any());
    }

    @Test
    void getAllEntitlements_shouldReturnList_onData() {
        EntitlementRepository repo = mock(EntitlementRepository.class);
        EntitlementMapper mapper = mock(EntitlementMapper.class);
        UserFeignClient feign = mock(UserFeignClient.class);

        EntitlementService service = new EntitlementService(repo, mapper, feign);

        Entitlement e = new Entitlement();
        e.setEntitlementId(1L);

        when(repo.findAll()).thenReturn(List.of(e));

        EntitlementResponseDTO dto = new EntitlementResponseDTO();
        dto.setEntitlementId(1L);
        when(mapper.toDto(e)).thenReturn(dto);

        List<EntitlementResponseDTO> list = service.getAllEntitlements();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getEntitlementId());
        verify(repo, times(2)).findAll(); // called twice in your code (size() then iteration)
    }

    @Test
    void getAllEntitlements_shouldThrowRuntimeException_whenEmpty() {
        EntitlementRepository repo = mock(EntitlementRepository.class);
        EntitlementMapper mapper = mock(EntitlementMapper.class);
        UserFeignClient feign = mock(UserFeignClient.class);

        EntitlementService service = new EntitlementService(repo, mapper, feign);

        when(repo.findAll()).thenReturn(Collections.emptyList());

        // Your code throws new RuntimeException("No Entitlements")
        assertThrows(RuntimeException.class, service::getAllEntitlements);
    }
}