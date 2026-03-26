package com.cts.controller;

import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestBody NotificationRequestDTO dto, @RequestHeader("X-User-Id") String id) {
        NotificationResponseDTO saved = notificationService.create(dto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/admin/getAll")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        try {
            return new ResponseEntity<>(notificationService.getAllNotifications(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/openAlerts")
    public ResponseEntity<Long> getUnreadNotifications() {
        try {
            return ResponseEntity.ok(notificationService.unreadNotifications());
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/mark-all-read")
    public ResponseEntity<Map<String, String>> markAllRead() {
        notificationService.markAllNotificationsAsRead();

        Map<String, String> response = new HashMap<>();
        response.put("message", "All notifications marked as read successfully");
        return ResponseEntity.ok(response);
    }
}
