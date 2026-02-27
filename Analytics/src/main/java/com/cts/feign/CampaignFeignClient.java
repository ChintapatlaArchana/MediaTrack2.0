package com.cts.feign;

import com.cts.dto.CampaignResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "adInventory")
public interface CampaignFeignClient {

    @GetMapping("campaign/{id}")
    CampaignResponseDTO getCampaignById(@PathVariable("id") Long id);
}

