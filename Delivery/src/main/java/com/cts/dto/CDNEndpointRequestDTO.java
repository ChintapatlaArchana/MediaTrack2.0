package com.cts.dto;

import com.cts.model.CDNEndpoint;
import lombok.Data;

@Data
public class CDNEndpointRequestDTO {
    private String name;
    private String baseURL;
    private String region;
    private CDNEndpoint.Status status; // use entity enum
}
//package com.cts.dto.delivery;
//
//import ch.qos.logback.core.status.Status;
//import lombok.Data;
//
//@Data
//public class CDNEndpointRequestDTO {
//        private String name;
//        private String baseURL;
//        private String region;
//        private Status status;
//
//}
//


