package com.cts.mapper;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Device.DeviceType.class, Device.Status.class})
public interface DeviceMapper {


    @Mapping(target="status",expression = "java(Status.valueOf(dto.getStatus()))")
    @Mapping(target="deviceType",expression = "java(DeviceType.valueOf(dto.getDeviceType()))")
    Device toEntity(DeviceRequestDTO dto);

    @Mapping(target="status",expression = "java(entity.getStatus())")
    @Mapping(target="deviceType",expression = "java(entity.getDeviceType())")
    @Mapping(target="userId", source = "userId")
    DeviceResponseDTO toDto(Device entity);
}
