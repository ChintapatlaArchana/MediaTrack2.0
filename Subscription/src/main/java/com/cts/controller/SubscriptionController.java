package com.cts.controller;

import com.cts.dto.SubscriptionRequestDTO;
import com.cts.dto.SubscriptionResponseDTO;
import com.cts.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/add")
    public ResponseEntity<SubscriptionResponseDTO> create(@RequestBody SubscriptionRequestDTO dto,  @RequestHeader("X-User-Id") String id) {
        try {
            return new ResponseEntity(subscriptionService.create(dto, id), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
        try {
            return new ResponseEntity(subscriptionService.getAllSubscriptions(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
