package com.cts.service;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.feign.PlanFeignClient;
import com.cts.mapper.ChurnCohortMapper;
import com.cts.model.ChurnCohort;
import com.cts.repository.ChurnCohortRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChurnCohortService {

    public ChurnCohortService(ChurnCohortRepository churnCohortRepository, ChurnCohortMapper churnCohortMapper, PlanFeignClient planFeignClient) {
        this.churnCohortRepository = churnCohortRepository;
        this.churnCohortMapper = churnCohortMapper;
        this.planFeignClient = planFeignClient;
    }

    private final ChurnCohortRepository churnCohortRepository;
    private final ChurnCohortMapper churnCohortMapper;
    private final PlanFeignClient planFeignClient;


    public ChurnCohortResponseDTO generateChurnCohort(ChurnCohortRequestDTO dto){
        ChurnCohort churnCohort = churnCohortMapper.toEntity(dto);

        PlanResponseDTO planResponseDTO = planFeignClient.getPlanById(dto.getPlanId());
        churnCohort.setPlanId(planResponseDTO.getPlanId());

        return churnCohortMapper.toDto(churnCohortRepository.save(churnCohort));
    }

    public List<ChurnCohortResponseDTO> getAllChurnCohorts(){
        return churnCohortRepository.findAll().stream().map(churnCohortMapper::toDto).toList();
    }

    public void deleteChurnCohort(Long id){
        if(churnCohortRepository.existsById(id)){
            churnCohortRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Churn Cohort does not exist with id: "+id);
        }
    }
}
