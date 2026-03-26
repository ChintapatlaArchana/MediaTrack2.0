package com.cts.test.controllerTest;

import com.cts.controller.AdDeliveryReportController;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.service.AdDeliveryReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdDeliveryReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdDeliveryReportService adDeliveryReportService;

    @InjectMocks
    private AdDeliveryReportController adDeliveryReportController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adDeliveryReportController).build();
    }

    @Test
    void testGenerateAdDeliveryReport_Success() throws Exception {
        AdDeliveryReportRequestDTO requestDTO = new AdDeliveryReportRequestDTO();
        requestDTO.setCampaignId(1L);

        AdDeliveryReportResponseDTO responseDTO = new AdDeliveryReportResponseDTO();
        responseDTO.setAdReportID(1L);
        responseDTO.setCampaignId(1L);

        when(adDeliveryReportService.generateAdDeliveryReport(any(AdDeliveryReportRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/adDeliveryReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adReportID").value(1));
    }

    @Test
    void testListAdDeliveryReports_Success() throws Exception {
        AdDeliveryReportResponseDTO responseDTO = new AdDeliveryReportResponseDTO();
        responseDTO.setAdReportID(1L);
        List<AdDeliveryReportResponseDTO> list = Collections.singletonList(responseDTO);

        when(adDeliveryReportService.getAllAdDeliveryReports()).thenReturn(list);

        mockMvc.perform(get("/adDeliveryReport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adReportID").value(1));
    }

    @Test
    void testDeleteAdDeliveryReport_Success() throws Exception {
        doNothing().when(adDeliveryReportService).deleteAdDeliveryReport(anyLong());

        mockMvc.perform(delete("/adDeliveryReport").param("id", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetDashboardStats_Success() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalImpressions", 1000L);

        when(adDeliveryReportService.getDashboardStats()).thenReturn(stats);

        mockMvc.perform(get("/adDeliveryReport/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalImpressions").value(1000));
    }

    @Test
    void testGetPerformanceChart_Success() throws Exception {
        List<Map<String, Object>> chartData = Collections.singletonList(new HashMap<>() {{
            put("impressions", 500);
        }});

        when(adDeliveryReportService.getChartData()).thenReturn(chartData);

        mockMvc.perform(get("/adDeliveryReport/stats/chart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].impressions").value(500));
    }

    @Test
    void testGetDashboardSummary_Success() throws Exception {
        Map<String, Object> summary = new HashMap<>();
        summary.put("impressionGrowth", "+10%");

        when(adDeliveryReportService.getDashboardWithGrowth()).thenReturn(summary);

        mockMvc.perform(get("/adDeliveryReport/stats/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.impressionGrowth").value("+10%"));
    }
}
