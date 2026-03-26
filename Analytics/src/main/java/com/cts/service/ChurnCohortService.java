package com.cts.service;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.feign.PlanFeignClient;
import com.cts.mapper.ChurnCohortMapper;
import com.cts.model.ChurnCohort;
import com.cts.repository.ChurnCohortRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChurnCohortService {

    private final ChurnCohortRepository churnCohortRepository;
    private final ChurnCohortMapper churnCohortMapper;
    private final PlanFeignClient planFeignClient;

    public ChurnCohortService(ChurnCohortRepository churnCohortRepository,
                              ChurnCohortMapper churnCohortMapper,
                              PlanFeignClient planFeignClient) {
        this.churnCohortRepository = churnCohortRepository;
        this.churnCohortMapper = churnCohortMapper;
        this.planFeignClient = planFeignClient;
    }

    public ChurnCohortResponseDTO generateChurnCohort(ChurnCohortRequestDTO dto) {
        try {
            ChurnCohort churnCohort = churnCohortMapper.toEntity(dto);

            PlanResponseDTO planResponseDTO = planFeignClient.getPlanById(dto.getPlanId());
            if (planResponseDTO == null) {
                throw new RuntimeException("Plan not found with id: " + dto.getPlanId());
            }

            churnCohort.setPlanId(planResponseDTO.getPlanId());
            return churnCohortMapper.toDto(churnCohortRepository.save(churnCohort));
        } catch (Exception ex) {
            throw new RuntimeException("Error generating churn cohort: " + ex.getMessage(), ex);
        }
    }

    public List<ChurnCohortResponseDTO> getAllChurnCohorts() {
        try {
            List<ChurnCohortResponseDTO> cohorts = churnCohortRepository.findAll()
                    .stream()
                    .map(churnCohortMapper::toDto)
                    .toList();

            if (cohorts.isEmpty()) {
                throw new RuntimeException("No churn cohorts found");
            }
            return cohorts;
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching churn cohorts: " + ex.getMessage(), ex);
        }
    }

    public void deleteChurnCohort(Long id) {
        try {
            if (churnCohortRepository.existsById(id)) {
                churnCohortRepository.deleteById(id);
            } else {
                throw new RuntimeException("Churn Cohort does not exist with id: " + id);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting churn cohort: " + ex.getMessage(), ex);
        }
    }

//    public double calculateChurnRate() {
//        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
//
//        // 1. Total users who WERE active 30 days ago
//        long totalUsersAtStart = userRepository.count();
//
//        // 2. Users who are currently Lapsed or Cancelled
//        long lostUsers = subscriptionRepository.countByStatusInAndLastModifiedDateAfter(
//                List.of("Lapsed", "Cancelled"), thirtyDaysAgo);
//
//        if (totalUsersAtStart == 0) return 0.0;
//
//        return ((double) lostUsers / totalUsersAtStart) * 100;
//    }
}
