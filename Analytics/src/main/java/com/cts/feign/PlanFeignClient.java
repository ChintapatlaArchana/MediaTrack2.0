package com.cts.feign;


import com.cts.dto.PlanResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subscription")
public interface PlanFeignClient {

    @GetMapping("plan/id/{id}")
    PlanResponseDTO getPlanById(@PathVariable("id") Long planId);
}
