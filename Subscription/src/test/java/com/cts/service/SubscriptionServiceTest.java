package com.cts.service;

import com.cts.dto.SubscriptionRequestDTO;
import com.cts.dto.SubscriptionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.SubscriptionMapper;
import com.cts.model.Plan;
import com.cts.model.Subscription;
import com.cts.repository.PlanRepository;
import com.cts.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.Collections;

class SubscriptionServiceTest {

    @Test
    void testCreateSuccess() {

        SubscriptionRepository repo = mock(SubscriptionRepository.class);
        UserFeignClient feign = mock(UserFeignClient.class);
        PlanRepository planRepo = mock(PlanRepository.class);
        SubscriptionMapper mapper = mock(SubscriptionMapper.class);

        SubscriptionService service = new SubscriptionService(repo, feign, planRepo, mapper);

        SubscriptionRequestDTO req = new SubscriptionRequestDTO();
        req.setPlanId(1L);
        req.setStartDate(LocalDate.of(2026,1,1));

        // Mock user
        UserResponseDTO user = new UserResponseDTO();
        user.setUserId(50L);

        when(feign.getUserById(50L)).thenReturn(ResponseEntity.ok(user));

        // Mock plan
        Plan plan = new Plan();
        plan.setPlanId(1L);
        plan.setBillingCycle(Plan.BillingCycle.Monthly);
        when(planRepo.findById(1L)).thenReturn(Optional.of(plan));

        // Mock repo save
        Subscription saved = new Subscription();
        saved.setSubscriptionId(10L);
        when(repo.save(any())).thenReturn(saved);

        // Mock mapper
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setSubscriptionId(10L);
        when(mapper.toDTO(saved)).thenReturn(dto);

        var response = service.create(req, "50");

        assertEquals(10L, response.getBody().getSubscriptionId());
    }

    @Test
    void testCreateUserNotFound() {

        SubscriptionRepository repo = mock(SubscriptionRepository.class);
        UserFeignClient feign = mock(UserFeignClient.class);
        PlanRepository planRepo = mock(PlanRepository.class);
        SubscriptionMapper mapper = mock(SubscriptionMapper.class);

        SubscriptionService service = new SubscriptionService(repo, feign, planRepo, mapper);

        SubscriptionRequestDTO req = new SubscriptionRequestDTO();
        req.setPlanId(1L);

        Plan plan = new Plan();
        plan.setPlanId(1L);
        plan.setBillingCycle(Plan.BillingCycle.Monthly);

        when(planRepo.findById(1L)).thenReturn(Optional.of(plan));
        when(feign.getUserById(100L)).thenReturn(ResponseEntity.notFound().build());

        var response = service.create(req, "100");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGetAllSubscriptions() {

        SubscriptionRepository repo = mock(SubscriptionRepository.class);
        UserFeignClient feign = mock(UserFeignClient.class);
        PlanRepository planRepo = mock(PlanRepository.class);
        SubscriptionMapper mapper = mock(SubscriptionMapper.class);

        SubscriptionService service = new SubscriptionService(repo, feign, planRepo, mapper);

        Subscription s = new Subscription();
        s.setSubscriptionId(1L);

        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setSubscriptionId(1L);

        when(repo.findAll()).thenReturn(Collections.singletonList(s));
        when(mapper.toDTO(s)).thenReturn(dto);

        var list = service.getAllSubscriptions();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getSubscriptionId());
    }
}
