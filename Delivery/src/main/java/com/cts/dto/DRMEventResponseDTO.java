// DRMEventResponseDTO.java
package com.cts.dto;

import com.cts.model.DRMEvent.LicenseStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DRMEventResponseDTO {
    private Long drmEventID;
    private Long playbackSessionId;
    private String drmType;
    private LocalDateTime eventTime;
    private String licenseStatus; // String in API; enum->String in mapper
}

//package com.cts.dto.delivery;
//
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class DRMEventResponseDTO {
//
//        private Long drmEventID;
//        private Long playbackSessionId;
//        private String drmType;
//        private LocalDateTime eventTime;
//        private String licenseStatus;
//}
