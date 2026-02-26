package com.cts.service;

import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.InvalidInputException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.NotificationMapper;
import com.cts.model.Notification;
import com.cts.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserFeignClient userFeignClient;

    public NotificationService(NotificationMapper notificationMapper,
                               NotificationRepository notificationRepository,
                               UserFeignClient userFeignClient) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.userFeignClient = userFeignClient;
    }

    public NotificationResponseDTO create(NotificationRequestDTO dto, String id) {
        Long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Invalid user id format: " + id);
        }

        Notification notification = notificationMapper.toEntity(dto);
        ResponseEntity<UserResponseDTO> userResponseDTO = userFeignClient.getUserById(userId);

        if (userResponseDTO.getStatusCode().is2xxSuccessful() && userResponseDTO.getBody() != null) {
            notification.setUserId(userResponseDTO.getBody().getUserId());
            return notificationMapper.toDto(notificationRepository.save(notification));
        } else {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }

    public List<NotificationResponseDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("There are no notifications available");
        }
        return notifications.stream()
                .map(notificationMapper::toDto)
                .toList();
    }
}
