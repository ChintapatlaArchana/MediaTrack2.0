package com.cts.controller;



import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.model.Title;
import com.cts.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/titles")
public class TitleController {

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    private final TitleService titleService;

    @PostMapping("/content/addTitle")
    public ResponseEntity<TitleResponseDTO> createTitle(@RequestBody TitleRequestDTO dto){
        return ResponseEntity.ok(titleService.createTitle(dto));
    }

    @PutMapping("/content/{id}")
    public  ResponseEntity<TitleResponseDTO> updateTitle(@PathVariable("id") Long titleId,@RequestBody TitleRequestDTO dto){
        return ResponseEntity.ok(titleService.updateTitle(titleId,dto));
    }

    @GetMapping("/content/getTitle")
    public ResponseEntity<List<TitleResponseDTO>> ListTitles(){
        return ResponseEntity.ok(titleService.getAllTitles());
    }


    @GetMapping("/content/{id}")
    public ResponseEntity<TitleResponseDTO> getTitleById(@PathVariable("id") Long titleId){
        return ResponseEntity.ok(titleService.getTitle(titleId));
    }

    @DeleteMapping("/content/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id){
        titleService.deleteTitle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/content/counts")
    public ResponseEntity<Map<Title.ApplicationStatus, Long>> getStatusCounts() {
        return ResponseEntity.ok(titleService.getCountByStatus());
    }

    @PatchMapping("/content/quickupdate/{id}")
    public ResponseEntity<Title> updateTitle(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {

        // Note: React formData uses "title", but your Service uses "name"
        String name = updates.get("name");
        String genre = updates.get("genre");
        String applicationStatus = updates.get("applicationStatus");

        Title updated = titleService.updateTitle(id, name, genre, applicationStatus);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/content/name")
    public ResponseEntity<Long> getTitleId(@RequestParam String name) {
        Long id = titleService.getTitleIdByName(name);
        return ResponseEntity.ok(id);
    }

}
