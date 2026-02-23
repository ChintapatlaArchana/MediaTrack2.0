package com.cts.controller;

import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.service.PlaybackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playback")
    public class PlaybackController {

        private final PlaybackService playBackService;

        public PlaybackController(PlaybackService service) {
            this.playBackService = service;
        }

        @PostMapping
        public ResponseEntity<PlaybackSessionResponseDTO> create(@RequestBody PlaybackSessionRequestDTO dto) {
            var saved = playBackService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }

        @GetMapping
        public ResponseEntity<List<PlaybackSessionResponseDTO>> getAll() {
            return ResponseEntity.ok(playBackService.getAll());
        }

        @GetMapping("/{id}")
        public ResponseEntity<PlaybackSessionResponseDTO> getById(@PathVariable Long id) {
            return ResponseEntity.ok(playBackService.getById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<PlaybackSessionResponseDTO> update(@PathVariable Long id,
                                                                 @RequestBody PlaybackSessionRequestDTO dto) {
            return ResponseEntity.ok(playBackService.update(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            playBackService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }


