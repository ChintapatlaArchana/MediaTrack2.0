// DRMEventRequestDTO.java
package com.cts.dto;

import com.cts.model.DRMEvent.LicenseStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DRMEventRequestDTO {
    private Long playbackSessionId;
    private String drmType;
    private LocalDateTime eventTime;
    private String licenseStatus; // String in API; we'll map to enum in mapper
}
//// DRMEventRequestDTO.java
//package com.cts.dto.delivery;
//
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class DRMEventRequestDTO {
//    private Long playbackSessionId;
//    private String drmType;
//    private LocalDateTime eventTime;
//    private String licenseStatus; // String in API; we'll map to enum in mapper
//}
////package com.cts.dto.delivery;
////
////import lombok.Data;
////
////import java.time.LocalDateTime;
////@Data
////public class DRMEventRequestDTO {
////
////        private Long playbackSessionId;
////        private String drmType;
////        private LocalDateTime eventTime;
////        private String licenseStatus;
////}
////
