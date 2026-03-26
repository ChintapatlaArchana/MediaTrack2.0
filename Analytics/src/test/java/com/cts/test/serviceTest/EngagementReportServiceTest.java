package com.cts.test.serviceTest;

import com.cts.dto.EngagementReportRequestDTO;
import com.cts.dto.ScopeDTO;
import com.cts.mapper.EngagementReportMapper;
import com.cts.model.EngagementReport;
import com.cts.model.Scope;
import com.cts.repository.EngagementReportRepository;
import com.cts.service.EngagementReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngagementReportServiceTest {

    @Mock
    private EngagementReportRepository engagementReportRepository;

    @Mock
    private EngagementReportMapper engagementReportMapper;

    @InjectMocks
    private EngagementReportService engagementReportService;

    private EngagementReportRequestDTO requestDTO;
    private EngagementReport report;

    @BeforeEach
    void setUp() {
        requestDTO = new EngagementReportRequestDTO();
        requestDTO.setGeneratedDate(LocalDate.now());

        report = new EngagementReport();
        report.setReportId(1L);
        report.setGeneratedDate(LocalDate.now());
    }

    @Test
    void testGenerateEngagementReport_Success() {
        when(engagementReportMapper.toEntity(any(EngagementReportRequestDTO.class))).thenReturn(report);
        when(engagementReportRepository.save(any(EngagementReport.class))).thenReturn(report);

        EngagementReport result = engagementReportService.generateEngagementReport(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getReportId());
        verify(engagementReportRepository).save(any(EngagementReport.class));
    }

    @Test
    void testGenerateEngagementReport_Error() {
        when(engagementReportMapper.toEntity(any(EngagementReportRequestDTO.class))).thenThrow(new RuntimeException("Mapping error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            engagementReportService.generateEngagementReport(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Error creating engagement report"));
    }

    @Test
    void testGetAllEngagementReports_Success() {
        List<EngagementReport> reports = List.of(report);
        when(engagementReportRepository.findAll()).thenReturn(reports);

        List<EngagementReport> result = engagementReportService.getAllEngagementReports();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllEngagementReports_Empty() {
        when(engagementReportRepository.findAll()).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            engagementReportService.getAllEngagementReports();
        });

        assertTrue(exception.getMessage().contains("No engagement reports found"));
    }

    @Test
    void testDeleteEngagementReport_Success() {
        when(engagementReportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(engagementReportRepository).deleteById(1L);

        assertDoesNotThrow(() -> engagementReportService.deleteEngagementReport(1L));
        verify(engagementReportRepository).deleteById(1L);
    }

    @Test
    void testDeleteEngagementReport_NotFound() {
        when(engagementReportRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            engagementReportService.deleteEngagementReport(1L);
        });

        assertTrue(exception.getMessage().contains("Engagement report does not exist"));
    }


}
