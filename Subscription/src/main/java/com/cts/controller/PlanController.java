package com.cts.controller;

import com.cts.dto.PlanRequestDTO;
import com.cts.dto.PlanResponseDTO;
import com.cts.model.Plan;
import com.cts.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/admin/add")
    public ResponseEntity<PlanResponseDTO> create(@RequestBody PlanRequestDTO dto) {
        try {
            return new ResponseEntity(planService.createPlan(dto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<List<PlanResponseDTO>> getAllPlans() {
        try {
            return new ResponseEntity(planService.getAllPlans(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<PlanResponseDTO> getPlanById(@PathVariable("id") Long planId){
        try{
            return ResponseEntity.ok(planService.getPlan(planId));
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable long id) {

        planService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Void> updatePlan(@PathVariable long id, @RequestBody PlanRequestDTO dto) {
        planService.updatePlanById(id, dto);
        return ResponseEntity.noContent().build();
    }
}
