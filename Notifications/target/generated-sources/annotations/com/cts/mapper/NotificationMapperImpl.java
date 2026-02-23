package com.cts.mapper;

import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.model.Notification;
import com.cts.model.Notification.Category;
import com.cts.model.Notification.Status;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-22T14:45:41+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public Notification toEntity(NotificationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notification notification = new Notification();

        notification.setMessage( dto.getMessage() );
        if ( dto.getCategory() != null ) {
            notification.setCategory( Enum.valueOf( Notification.Category.class, dto.getCategory() ) );
        }
        if ( dto.getStatus() != null ) {
            notification.setStatus( Enum.valueOf( Notification.Status.class, dto.getStatus() ) );
        }
        notification.setCreatedDate( dto.getCreatedDate() );

        return notification;
    }

    @Override
    public NotificationResponseDTO toDto(Notification entity) {
        if ( entity == null ) {
            return null;
        }

        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();

        notificationResponseDTO.setUserId( entity.getUserId() );
        notificationResponseDTO.setNotificationId( entity.getNotificationId() );
        notificationResponseDTO.setMessage( entity.getMessage() );
        notificationResponseDTO.setCategory( entity.getCategory() );
        notificationResponseDTO.setStatus( entity.getStatus() );
        notificationResponseDTO.setCreatedDate( entity.getCreatedDate() );

        return notificationResponseDTO;
    }
}
