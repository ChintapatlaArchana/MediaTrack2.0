package com.cts.service;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.PlanMapper;
import com.cts.model.Plan;
import com.cts.repository.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final ObjectMapper mapper;

    public PlanService(PlanRepository planRepository, PlanMapper planMapper,ObjectMapper mapper) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.mapper = mapper;
        log.info("Plan Service is being used");
    }

    public PlanResponseDTO createPlan(PlanRequestDTO dto) {
        Plan plan = planMapper.toEntity(dto);
        log.info("New plan is added");
        return planMapper.toDTO(planRepository.save(plan));
    }

    public PlanResponseDTO getPlan(Long planId){
        log.info("Getting plan using plan Id");
        return planMapper.toDTO(planRepository.findById(planId).orElseThrow());
    }

    public List<PlanResponseDTO> getAllPlans() {
        List<PlanResponseDTO> planResponseDTOList = new ArrayList<>();
        try {
            if(planRepository.findAll().size() == 0) {
                log.warn("No plans are created");
                throw new GlobalException("No Plan are created");
            } else {
                for(Plan p : planRepository.findAll()) {
                    planResponseDTOList.add(planMapper.toDTO(p));
                }
                log.info("List of plans are sent");
            }
            return planResponseDTOList;
        } catch (IllegalArgumentException e) {
            log.error("Error in returning plans"+e.getMessage());
            throw new GlobalException("Can't get Plans");
        }
    }
}
