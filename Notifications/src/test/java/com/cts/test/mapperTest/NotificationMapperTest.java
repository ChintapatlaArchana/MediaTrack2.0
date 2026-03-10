package com.cts.test.mapperTest;



import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.mapper.NotificationMapper;
import com.cts.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationMapperMockitoTest {

    @Mock
    private NotificationMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapperMockToEntity() {
        NotificationRequestDTO dto = new NotificationRequestDTO();
        dto.setMessage("Hello");

        Notification entity = new Notification();
        entity.setMessage("Hello");

        when(mapper.toEntity(dto)).thenReturn(entity);

        Notification result = mapper.toEntity(dto);

        assertEquals("Hello", result.getMessage());
        verify(mapper, times(1)).toEntity(dto);
    }

    @Test
    void testMapperMockToDto() {
        Notification entity = new Notification();
        entity.setNotificationId(1L);
        entity.setMessage("Hello");

        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setNotificationId(1L);
        dto.setMessage("Hello");

        when(mapper.toDto(entity)).thenReturn(dto);

        NotificationResponseDTO result = mapper.toDto(entity);

        assertEquals("Hello", result.getMessage());
        verify(mapper, times(1)).toDto(entity);
    }
}

