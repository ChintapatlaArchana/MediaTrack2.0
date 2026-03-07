package com.cts.mapper;

import com.cts.dto.SubscriptionResponseDTO;
import com.cts.model.Plan;
import com.cts.model.Subscription;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionMapperTest {

    private final SubscriptionMapper mapper = Mappers.getMapper(SubscriptionMapper.class);

    @Test
    void testToDTO() {

        Plan plan = new Plan();
        plan.setPlanId(5L);

        Subscription s = new Subscription();
        s.setSubscriptionId(10L);
        s.setUserId(20L);
        s.setPlan(plan);
        s.setStartDate(LocalDate.of(2026, 1, 1));
        s.setEndDate(LocalDate.of(2026, 2, 1));
        s.setStatus(Subscription.Status.Active);

        SubscriptionResponseDTO dto = mapper.toDTO(s);

        assertEquals(10L, dto.getSubscriptionId());
        assertEquals(20L, dto.getUserId());
        assertEquals(5L, dto.getPlanId());
        assertEquals("Active", dto.getStatus());
    }
}