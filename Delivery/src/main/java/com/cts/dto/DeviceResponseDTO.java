package com.cts.dto;

import com.cts.model.Device.DeviceType;
import com.cts.model.Device.Status;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class DeviceResponseDTO {

    private Long deviceId;
    private Long userId;
    private LocalDateTime registeredDate;
    private DeviceType deviceType; // use entity enum
    private Status status;         // use entity enum (fix)
}
//package com.cts.dto.delivery;
//
//
//import ch.qos.logback.core.status.Status;
//import com.cts.model.Device.DeviceType;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class DeviceResponseDTO {
//
//        private Long deviceId;
//        private Long userId;
//        private LocalDateTime registeredDate;
//        private DeviceType deviceType;
//        private Status status;
//}
//
