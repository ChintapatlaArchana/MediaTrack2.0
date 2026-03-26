package com.cts.test.serviceTest;

import com.cts.dto.AdDeliveryReportMetricsDTO;
import com.cts.dto.AdDeliveryReportRequestDTO;
import com.cts.dto.AdDeliveryReportResponseDTO;
import com.cts.dto.CampaignResponseDTO;
import com.cts.feign.CampaignFeignClient;
import com.cts.mapper.AdDeliveryReportMapper;
import com.cts.model.AdDeliveryReport;
import com.cts.model.AdDeliveryReportMetrics;
import com.cts.repository.AdDeliveryReportRepository;
import com.cts.service.AdDeliveryReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdDeliveryReportServiceTest {

    @Mock
    private AdDeliveryReportRepository adDeliveryReportRepository;

    @Mock
    private AdDeliveryReportMapper adDeliveryReportMapper;

    @Mock
    private CampaignFeignClient campaignFeignClient;

    @InjectMocks
    private AdDeliveryReportService adDeliveryReportService;

    private AdDeliveryReportRequestDTO requestDTO;
    private AdDeliveryReport adDeliveryReport;
    private AdDeliveryReportResponseDTO responseDTO;
    private CampaignResponseDTO campaignDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AdDeliveryReportRequestDTO();
        requestDTO.setCampaignId(1L);
        requestDTO.setGeneratedDate(LocalDate.now());

        adDeliveryReport = new AdDeliveryReport();
        adDeliveryReport.setAdReportID(1L);
        adDeliveryReport.setCampaignId(1L);
        adDeliveryReport.setGeneratedDate(LocalDate.now());

        responseDTO = new AdDeliveryReportResponseDTO();
        responseDTO.setAdReportID(1L);
        responseDTO.setCampaignId(1L);
        responseDTO.setGeneratedDate(LocalDate.now());

        campaignDTO = new CampaignResponseDTO();
        campaignDTO.setCampaignId(1L);
        campaignDTO.setName("Test Campaign");
    }

    @Test
    void testGenerateAdDeliveryReport_Success() {
        when(adDeliveryReportMapper.toEntity(any(AdDeliveryReportRequestDTO.class))).thenReturn(adDeliveryReport);
        when(campaignFeignClient.getCampaignById(1L)).thenReturn(campaignDTO);
        when(adDeliveryReportRepository.save(any(AdDeliveryReport.class))).thenReturn(adDeliveryReport);
        when(adDeliveryReportMapper.toDto(any(AdDeliveryReport.class))).thenReturn(responseDTO);

        AdDeliveryReportResponseDTO result = adDeliveryReportService.generateAdDeliveryReport(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getAdReportID());
        verify(adDeliveryReportRepository).save(any(AdDeliveryReport.class));
    }

    @Test
    void testGenerateAdDeliveryReport_CampaignNotFound() {
        when(adDeliveryReportMapper.toEntity(any(AdDeliveryReportRequestDTO.class))).thenReturn(adDeliveryReport);
        when(campaignFeignClient.getCampaignById(1L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adDeliveryReportService.generateAdDeliveryReport(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Campaign not found"));
    }

    @Test
    void testGetAllAdDeliveryReports_Success() {
        List<AdDeliveryReport> reports = List.of(adDeliveryReport);
        when(adDeliveryReportRepository.findAll()).thenReturn(reports);
        when(adDeliveryReportMapper.toDto(any(AdDeliveryReport.class))).thenReturn(responseDTO);

        List<AdDeliveryReportResponseDTO> result = adDeliveryReportService.getAllAdDeliveryReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllAdDeliveryReports_Empty() {
        when(adDeliveryReportRepository.findAll()).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adDeliveryReportService.getAllAdDeliveryReports();
        });

        assertTrue(exception.getMessage().contains("No ad delivery reports found"));
    }

    @Test
    void testDeleteAdDeliveryReport_Success() {
        when(adDeliveryReportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(adDeliveryReportRepository).deleteById(1L);

        assertDoesNotThrow(() -> adDeliveryReportService.deleteAdDeliveryReport(1L));
        verify(adDeliveryReportRepository).deleteById(1L);
    }

    @Test
    void testDeleteAdDeliveryReport_NotFound() {
        when(adDeliveryReportRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adDeliveryReportService.deleteAdDeliveryReport(1L);
        });

        assertTrue(exception.getMessage().contains("Ad Delivery report does not exist"));
    }

    @Test
    void testGetDashboardStats() {
        when(adDeliveryReportRepository.getTotalImpressions()).thenReturn(1000L);
        when(adDeliveryReportRepository.getAverageCTR()).thenReturn(0.05);
        when(adDeliveryReportRepository.getAverageECPM()).thenReturn(2.5);
        when(adDeliveryReportRepository.getActiveCampaignCount()).thenReturn(5L);

        Map<String, Object> stats = adDeliveryReportService.getDashboardStats();

        assertEquals(1000L, stats.get("totalImpressions"));
        assertEquals(0.05, stats.get("ctr"));
        assertEquals(2.5, stats.get("ecpm"));
        assertEquals(5L, stats.get("activeCampaigns"));
    }

    @Test
    void testGetChartData() {
        List<Object[]> queryResults = new ArrayList<>();
        queryResults.add(new Object[]{LocalDate.now(), 500L, 0.04});
        
        when(adDeliveryReportRepository.getDailyChartData()).thenReturn(queryResults);

        List<Map<String, Object>> result = adDeliveryReportService.getChartData();

        assertEquals(1, result.size());
        assertEquals(500L, result.get(0).get("impressions"));
    }

    @Test
    void testGetDashboardWithGrowth() {
        List<Object[]> currentStats = new ArrayList<>();
        currentStats.add(new Object[]{1000L, 0.05, 2.5, 5L});

        List<Object[]> previousStats = new ArrayList<>();
        previousStats.add(new Object[]{800L, 0.04, 2.0, 4L});

        when(adDeliveryReportRepository.getStatsForPeriod(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(currentStats)
                .thenReturn(previousStats);

        Map<String, Object> result = adDeliveryReportService.getDashboardWithGrowth();

        assertEquals(1000L, result.get("totalImpressions"));
        // ((1000-800)/800)*100 = 25%
        assertEquals("+25.0%", result.get("impressionGrowth"));
    }
}
