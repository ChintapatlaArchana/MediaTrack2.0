package com.cts.controller;


import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.service.ChurnCohortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/churnCohort")
public class ChurnCohortController {

    public ChurnCohortController(ChurnCohortService churnCohortService) {
        this.churnCohortService = churnCohortService;
    }

    private final ChurnCohortService churnCohortService;

    @PostMapping
    public ResponseEntity<ChurnCohortResponseDTO> generateChurnCohort(@RequestBody ChurnCohortRequestDTO dto){
        return ResponseEntity.ok(churnCohortService.generateChurnCohort(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChurnCohortResponseDTO>> ListChurnCohorts(){
        return ResponseEntity.ok(churnCohortService.getAllChurnCohorts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChurnCohort(@PathVariable Long id){
        churnCohortService.deleteChurnCohort(id);
        return ResponseEntity.noContent().build();

    }
}
