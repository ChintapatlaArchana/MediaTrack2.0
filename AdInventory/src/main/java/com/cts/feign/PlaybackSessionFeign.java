package com.cts.feign;

import com.cts.dto.PlaybackSessionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery")
public interface PlaybackSessionFeign {
    @GetMapping("/playback/{id}")
    PlaybackSessionResponseDTO getById(@PathVariable("id") long id);

}
