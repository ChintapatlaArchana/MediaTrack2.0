package com.cts.dto;

import com.cts.model.CDNEndpoint;
import lombok.Data;

@Data
public class CDNEndpointResponseDTO {
    private Long endpointID;
    private String name;
    private String baseURL;
    private String region;
    private CDNEndpoint.Status status; // use entity enum
//    private List<Long> packageID; // we can map this later if you share MediaPackage model
}

////package com.cts.dto.delivery;
//import ch.qos.logback.core.status.Status;
//import lombok.Data;
//import java.util.List;
//@Data
//public class CDNEndpointResponseDTO {
//
//        private Long endpointID;
//        private String name;
//        private String baseURL;
//        private String region;
//        private Status status;
//        private List<Long> packageID;
//}
//
//
