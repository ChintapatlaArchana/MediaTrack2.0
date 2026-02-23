package com.cts.dto;

import com.cts.model.Device.DeviceType;
import com.cts.model.Device.Status;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeviceRequestDTO {

    private Long userId;
    private LocalDateTime registeredDate;
    private String deviceType; // use entity enum
    private String status;         // use entity enum (fix)
}

//package com.cts.dto.delivery;
//
//import ch.qos.logback.core.status.Status;
//import com.cts.model.Device.DeviceType;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class DeviceRequestDTO {
//
//        private Long userId;
//        private LocalDateTime registeredDate;
//        private DeviceType deviceType;
//        private String status;
//}
//
//
//
