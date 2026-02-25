package com.cts.feign;

import com.cts.dto.AssetResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "content")
public interface AssetFeignClient {

    @GetMapping("/asset/{id}")
    AssetResponseDTO getAssetById(@PathVariable("id") Long id);
}
