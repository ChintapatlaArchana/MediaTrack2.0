package com.cts.service;

import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.NotificationMapper;
import com.cts.model.Notification;
import com.cts.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserFeignClient userFeignClient;

    public NotificationService(NotificationMapper notificationMapper, NotificationRepository notificationRepository, UserFeignClient userFeignClient) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.userFeignClient = userFeignClient;
    }

    public NotificationResponseDTO create(NotificationRequestDTO dto){
        Notification notification = notificationMapper.toEntity(dto);
        UserResponseDTO userResponseDTO = userFeignClient.getUserById(dto.getUserId());

        notification.setUserId(userResponseDTO.getUserId());

        return notificationMapper.toDto(notificationRepository.save(notification));

    }

    public List<NotificationResponseDTO> getAllNotifications(){
        List<Notification> notifications = notificationRepository.findAll();
        if(notifications.isEmpty()){
            throw new RuntimeException("There are no notifications");
        }
        return notifications.stream()
                .map(notificationMapper::toDto)
                .toList();
    }


}
