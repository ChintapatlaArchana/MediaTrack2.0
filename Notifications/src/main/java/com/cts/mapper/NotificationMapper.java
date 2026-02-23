package com.cts.mapper;


import com.cts.dto.NotificationRequestDTO;
import com.cts.dto.NotificationResponseDTO;
import com.cts.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Notification.Status.class, Notification.Category.class})
public interface NotificationMapper {

   // @Mapping(target = "category", expression = "java(Category.valueOf(dto.getCategory()))")
    //@Mapping(target = "status", expression = "java(Status.valueOf(dto.getStatus()))")
    Notification toEntity(NotificationRequestDTO dto);

    //@Mapping(target = "category", expression = "java(entity.getCategory())")
    //@Mapping(target = "status", expression = "java(entity.getStatus())")
    @Mapping(target = "userId", source = "userId")
    NotificationResponseDTO toDto(Notification entity);
}
