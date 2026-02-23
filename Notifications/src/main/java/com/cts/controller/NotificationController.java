package com.cts.controller;

import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody NotificationRequestDTO dto, @RequestHeader("X-User-Id") String id) {
        NotificationResponseDTO saved = notificationService.create(dto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        try {
            return new ResponseEntity<>(notificationService.getAllNotifications(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
