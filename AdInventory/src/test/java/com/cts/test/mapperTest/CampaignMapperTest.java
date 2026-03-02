package com.cts.test.mapperTest;

import com.cts.dto.CampaignRequestDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.mapper.CampaignMapper;
import com.cts.model.Campaign;
import com.cts.model.Creative;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignMapperTest {

    @Test
    void testToEntity_Mocked() {
        CampaignMapper mapper = mock(CampaignMapper.class);

        CampaignRequestDTO request = new CampaignRequestDTO();
        Campaign entity = new Campaign();

        when(mapper.toEntity(request)).thenReturn(entity);

        Campaign result = mapper.toEntity(request);

        assertEquals(entity, result);
        verify(mapper).toEntity(request);
    }

    @Test
    void testToDTO_Mocked() {
        CampaignMapper mapper = mock(CampaignMapper.class);

        Campaign campaign = new Campaign();
        Creative creative = new Creative();
        creative.setCreativeId(10L);
        campaign.setCreative(creative);

        CampaignResponseDTO dto = new CampaignResponseDTO();

        when(mapper.toDTO(campaign)).thenReturn(dto);

        CampaignResponseDTO result = mapper.toDTO(campaign);

        assertEquals(dto, result);
        verify(mapper).toDTO(campaign);
    }
}
