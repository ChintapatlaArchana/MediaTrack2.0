package com.cts.mapper;

import com.cts.dto.DeviceRequestDTO;
import com.cts.dto.DeviceResponseDTO;
import com.cts.model.Device;
import com.cts.model.Device.DeviceType;
import com.cts.model.Device.Status;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-23T14:17:39+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class DeviceMapperImpl implements DeviceMapper {

    @Override
    public Device toEntity(DeviceRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Device device = new Device();

        device.setUserId( dto.getUserId() );
        device.setRegisteredDate( dto.getRegisteredDate() );

        device.setStatus( Status.valueOf(dto.getStatus()) );
        device.setDeviceType( DeviceType.valueOf(dto.getDeviceType()) );

        return device;
    }

    @Override
    public DeviceResponseDTO toDto(Device entity) {
        if ( entity == null ) {
            return null;
        }

        DeviceResponseDTO deviceResponseDTO = new DeviceResponseDTO();

        deviceResponseDTO.setUserId( entity.getUserId() );
        deviceResponseDTO.setDeviceId( entity.getDeviceId() );
        deviceResponseDTO.setRegisteredDate( entity.getRegisteredDate() );

        deviceResponseDTO.setStatus( entity.getStatus() );
        deviceResponseDTO.setDeviceType( entity.getDeviceType() );

        return deviceResponseDTO;
    }
}
