package com.cts.controller;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> create(@RequestBody DeviceRequestDTO dto, @RequestHeader("X-User-Id") String id) {
        try {
            return new ResponseEntity(deviceService.create(dto, id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> findAll(
//            @RequestParam(value = "userId", required = false) Long userId,
//            @RequestParam(value = "status", required = false) String status
    ) {
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DeviceRequestDTO request
    ) {
        return ResponseEntity.ok(deviceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> delete(@PathVariable Long id) {
        deviceService.delete(id); // recommended: set status=Revoked
        return ResponseEntity.noContent().build();
    }

}