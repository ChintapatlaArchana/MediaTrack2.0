package com.cts.service;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.PlanMapper;
import com.cts.model.Plan;
import com.cts.repository.PlanRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final ObjectMapper mapper;

    public PlanService(PlanRepository planRepository, PlanMapper planMapper,ObjectMapper mapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.mapper = mapper;
    }

    public PlanResponseDTO createPlan(PlanRequestDTO dto) {
        Plan plan = planMapper.toEntity(dto);
        return planMapper.toDTO(planRepository.save(plan));
    }

    public PlanResponseDTO getPlan(Long planId){
        return planMapper.toDTO(planRepository.findById(planId).orElseThrow());
    }

    public List<PlanResponseDTO> getAllPlans() {
        List<PlanResponseDTO> planResponseDTOList = new ArrayList<>();
        try {
            if(planRepository.findAll().size() == 0) {
                throw new GlobalException("No Plan are created");
            } else {
                for(Plan p : planRepository.findAll()) {
                    planResponseDTOList.add(planMapper.toDTO(p));
                }
            }
            return planResponseDTOList;
        } catch (IllegalArgumentException e) {
            throw new GlobalException("Can't get Plans");
        }
    }
}
