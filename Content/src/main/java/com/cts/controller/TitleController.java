package com.cts.controller;



import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.model.Title;
import com.cts.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    private final TitleService titleService;

    @PostMapping
    public ResponseEntity<TitleResponseDTO> createTitle(@RequestBody TitleRequestDTO dto){
        return ResponseEntity.ok(titleService.createTitle(dto));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<TitleResponseDTO> updateTitle(@PathVariable("id") Long titleId,@RequestBody TitleRequestDTO dto){
        return ResponseEntity.ok(titleService.updateTitle(titleId,dto));
    }

    @GetMapping
    public ResponseEntity<List<TitleResponseDTO>> ListTitles(){
        return ResponseEntity.ok(titleService.getAllTitles());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TitleResponseDTO> getTitleById(@PathVariable("id") Long titleId){
        return ResponseEntity.ok(titleService.getTitle(titleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id){
        titleService.deleteTitle(id);
        return ResponseEntity.noContent().build();
    }


}
