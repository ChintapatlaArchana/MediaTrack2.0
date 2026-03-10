package com.cts.test.serviceTest;



import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.InvalidInputException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.NotificationMapper;
import com.cts.model.Notification;
import com.cts.repository.NotificationRepository;
import com.cts.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotificationSuccess() {
        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setMessage("Hello");
        request.setCategory("Subscription");
        request.setStatus("Unread");
        request.setCreatedDate(LocalDate.now());

        Notification notification = new Notification();
        notification.setMessage("Hello");

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setUserId(1L);

        NotificationResponseDTO responseDTO = new NotificationResponseDTO();
        responseDTO.setNotificationId(1L);
        responseDTO.setMessage("Hello");

        when(notificationMapper.toEntity(request)).thenReturn(notification);
        when(userFeignClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userResponse));
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.toDto(notification)).thenReturn(responseDTO);

        NotificationResponseDTO result = notificationService.create(request, "1");

        assertEquals("Hello", result.getMessage());
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testCreateNotificationInvalidUserId() {
        NotificationRequestDTO request = new NotificationRequestDTO();
        assertThrows(InvalidInputException.class,
                () -> notificationService.create(request, "abc"));
    }

    @Test
    void testCreateNotificationUserNotFound() {
        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setMessage("Hello");

        Notification notification = new Notification();
        when(notificationMapper.toEntity(request)).thenReturn(notification);
        when(userFeignClient.getUserById(1L))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        assertThrows(ResourceNotFoundException.class,
                () -> notificationService.create(request, "1"));
    }

    @Test
    void testGetAllNotificationsSuccess() {
        Notification notification = new Notification();
        notification.setNotificationId(1L);

        NotificationResponseDTO responseDTO = new NotificationResponseDTO();
        responseDTO.setNotificationId(1L);

        when(notificationRepository.findAll()).thenReturn(List.of(notification));
        when(notificationMapper.toDto(notification)).thenReturn(responseDTO);

        List<NotificationResponseDTO> result = notificationService.getAllNotifications();

        assertEquals(1, result.size());
    }

    @Test
    void testGetAllNotificationsEmpty() {
        when(notificationRepository.findAll()).thenReturn(List.of());
        assertThrows(ResourceNotFoundException.class,
                () -> notificationService.getAllNotifications());
    }
}

