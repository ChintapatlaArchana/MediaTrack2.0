package com.cts.test.serviceTest;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.feign.PlanFeignClient;
import com.cts.mapper.ChurnCohortMapper;
import com.cts.model.ChurnCohort;
import com.cts.repository.ChurnCohortRepository;
import com.cts.service.ChurnCohortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChurnCohortServiceTest {

    @Mock
    private ChurnCohortRepository churnCohortRepository;

    @Mock
    private ChurnCohortMapper churnCohortMapper;

    @Mock
    private PlanFeignClient planFeignClient;

    @InjectMocks
    private ChurnCohortService churnCohortService;

    private ChurnCohortRequestDTO requestDTO;
    private ChurnCohort cohort;
    private ChurnCohortResponseDTO responseDTO;
    private PlanResponseDTO planDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ChurnCohortRequestDTO();
        requestDTO.setPlanId(1L);
        requestDTO.setStartPeriod(LocalDate.now());

        cohort = new ChurnCohort();
        cohort.setCohortId(1L);
        cohort.setPlanId(1L);
        cohort.setStartPeriod(LocalDate.now());

        responseDTO = new ChurnCohortResponseDTO();
        responseDTO.setCohortId(1L);
        responseDTO.setPlanId(1L);

        planDTO = new PlanResponseDTO();
        planDTO.setPlanId(1L);
        planDTO.setName("Premium Plan");
    }

    @Test
    void testGenerateChurnCohort_Success() {
        when(churnCohortMapper.toEntity(any(ChurnCohortRequestDTO.class))).thenReturn(cohort);
        when(planFeignClient.getPlanById(1L)).thenReturn(planDTO);
        when(churnCohortRepository.save(any(ChurnCohort.class))).thenReturn(cohort);
        when(churnCohortMapper.toDto(any(ChurnCohort.class))).thenReturn(responseDTO);

        ChurnCohortResponseDTO result = churnCohortService.generateChurnCohort(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getCohortId());
        verify(churnCohortRepository).save(any(ChurnCohort.class));
    }

    @Test
    void testGenerateChurnCohort_PlanNotFound() {
        when(churnCohortMapper.toEntity(any(ChurnCohortRequestDTO.class))).thenReturn(cohort);
        when(planFeignClient.getPlanById(1L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            churnCohortService.generateChurnCohort(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Plan not found"));
    }

    @Test
    void testGetAllChurnCohorts_Success() {
        List<ChurnCohort> cohorts = List.of(cohort);
        when(churnCohortRepository.findAll()).thenReturn(cohorts);
        when(churnCohortMapper.toDto(any(ChurnCohort.class))).thenReturn(responseDTO);

        List<ChurnCohortResponseDTO> result = churnCohortService.getAllChurnCohorts();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllChurnCohorts_Empty() {
        when(churnCohortRepository.findAll()).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            churnCohortService.getAllChurnCohorts();
        });

        assertTrue(exception.getMessage().contains("No churn cohorts found"));
    }

    @Test
    void testDeleteChurnCohort_Success() {
        when(churnCohortRepository.existsById(1L)).thenReturn(true);
        doNothing().when(churnCohortRepository).deleteById(1L);

        assertDoesNotThrow(() -> churnCohortService.deleteChurnCohort(1L));
        verify(churnCohortRepository).deleteById(1L);
    }

    @Test
    void testDeleteChurnCohort_NotFound() {
        when(churnCohortRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            churnCohortService.deleteChurnCohort(1L);
        });

        assertTrue(exception.getMessage().contains("Churn Cohort does not exist"));
    }
}
