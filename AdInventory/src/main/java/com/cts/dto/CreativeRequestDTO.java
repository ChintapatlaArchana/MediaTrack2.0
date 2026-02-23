package com.cts.dto;

import lombok.Data;

@Data
public class CreativeRequestDTO {

    private String advertiser;
    private String mediaUri;
    private Integer duration;
    private String clickThroughUrl;
}
