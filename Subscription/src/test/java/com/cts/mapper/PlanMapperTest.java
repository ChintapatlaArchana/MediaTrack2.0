package com.cts.mapper;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.model.Plan;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PlanMapperTest {

    private final PlanMapper mapper = Mappers.getMapper(PlanMapper.class);

    @Test
    void toEntity_shouldMap_basicFields_andEnums() {
        PlanRequestDTO req = new PlanRequestDTO();
        req.setName("Basic");
        req.setPrice(49.0);
        req.setBillingCycle("Monthly");
        req.setStatus("Active");
        req.setEntitlements(null);

        Plan entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals("Basic", entity.getName());
        assertEquals(49.0, entity.getPrice());
        assertEquals(Plan.BillingCycle.Monthly, entity.getBillingCycle());
        assertEquals(Plan.Status.Active, entity.getStatus());
        assertNull(entity.getEntitlementsJSON());
    }

    @Test
    void toDTO_shouldMap_basicFields_andNullEntitlements() {
        Plan entity = new Plan();
        entity.setPlanId(5L);
        entity.setName("Pro");
        entity.setPrice(199.0);
        entity.setBillingCycle(Plan.BillingCycle.Yearly);
        entity.setStatus(Plan.Status.Active);
        entity.setEntitlementsJSON(null);

        PlanResponseDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(5L, dto.getPlanId());
        assertEquals("Pro", dto.getName());
        assertEquals(199.0, dto.getPrice());
        assertEquals(Plan.BillingCycle.Yearly, dto.getBillingCycle());
        assertEquals(Plan.Status.Active, dto.getStatus());
        assertNull(dto.getEntitlementsJSON());
    }
}