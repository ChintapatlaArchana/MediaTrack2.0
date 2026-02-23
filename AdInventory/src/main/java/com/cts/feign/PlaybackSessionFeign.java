package com.cts.feign;

import com.cts.dto.PlaybackSessionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-service")
public interface PlaybackSessionFeign {
    @GetMapping("playback/{id}")
    PlaybackSessionResponseDTO getById(@PathVariable long Id);

}
