package com.cts.test.controllerTest;

import com.cts.controller.EngagementReportController;
import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.EngagementReportResponseDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.service.EngagementReportService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EngagementReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EngagementReportService engagementReportService;

    @Mock
    private EngagementReportMapper engagementReportMapper;

    @InjectMocks
    private EngagementReportController engagementReportController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(engagementReportController).build();
    }

    @Test
    void testGenerateEngagementReport_Success() throws Exception {
        EngagementReportRequestDTO requestDTO = new EngagementReportRequestDTO();
        EngagementReport report = new EngagementReport();
        report.setReportId(1L);
        EngagementReportResponseDTO responseDTO = new EngagementReportResponseDTO();
        responseDTO.setReportId(1L);

        when(engagementReportService.generateEngagementReport(any(EngagementReportRequestDTO.class))).thenReturn(report);
        when(engagementReportMapper.toDto(any(EngagementReport.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/engagementReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId").value(1));
    }

    @Test
    void testListEngagementReports_Success() throws Exception {
        EngagementReport report = new EngagementReport();
        report.setReportId(1L);
        List<EngagementReport> reports = Collections.singletonList(report);
        EngagementReportResponseDTO responseDTO = new EngagementReportResponseDTO();
        responseDTO.setReportId(1L);

        when(engagementReportService.getAllEngagementReports()).thenReturn(reports);
        when(engagementReportMapper.toDto(any(EngagementReport.class))).thenReturn(responseDTO);

        mockMvc.perform(get("/engagementReport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reportId").value(1));
    }

    @Test
    void testDeleteEngagementReport_Success() throws Exception {
        doNothing().when(engagementReportService).deleteEngagementReport(anyLong());

        mockMvc.perform(delete("/engagementReport/1"))
                .andExpect(status().isNoContent());
    }
}
