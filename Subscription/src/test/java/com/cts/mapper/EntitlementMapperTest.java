package com.cts.mapper;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.model.Entitlement;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EntitlementMapperTest {

    private final EntitlementMapper mapper = Mappers.getMapper(EntitlementMapper.class);

    @Test
    void toEntity_shouldMapEnumFromString_andDates() {
        EntitlementRequestDTO req = new EntitlementRequestDTO();
        req.setContentScope("All"); // will be valueOf to enum
        req.setGrantedDate(LocalDate.of(2026, 2, 1));
        req.setExpiryDate(LocalDate.of(2026, 3, 1));

        Entitlement entity = mapper.toEntity(req);

        assertNotNull(entity);
        assertEquals(Entitlement.ContentScope.All, entity.getContentScope());
        assertEquals(LocalDate.of(2026, 2, 1), entity.getGrantedDate());
        assertEquals(LocalDate.of(2026, 3, 1), entity.getExpiryDate());
    }

    @Test
    void toDto_shouldCopyFields_andEnum() {
        Entitlement e = new Entitlement();
        e.setEntitlementId(10L);
        e.setUserId(500L);
        e.setContentScope(Entitlement.ContentScope.Category);
        e.setGrantedDate(LocalDate.of(2026, 1, 1));
        e.setExpiryDate(LocalDate.of(2026, 2, 1));

        EntitlementResponseDTO dto = mapper.toDto(e);

        assertNotNull(dto);
        assertEquals(10L, dto.getEntitlementId());
        assertEquals(500L, dto.getUserId());
        assertEquals(Entitlement.ContentScope.Category, dto.getContentScope());
        assertEquals(LocalDate.of(2026, 1, 1), dto.getGrantedDate());
        assertEquals(LocalDate.of(2026, 2, 1), dto.getExpiryDate());
    }
}