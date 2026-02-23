package com.cts.feign;

import com.cts.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "identity-service")
public interface UserFeignClient {

    @GetMapping("users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

}
