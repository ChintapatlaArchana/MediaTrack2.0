package com.cts.feign;

import com.cts.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-authentication",url="http://localhost:9090")
public interface UserFeignClient {

    @GetMapping("user/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

}
