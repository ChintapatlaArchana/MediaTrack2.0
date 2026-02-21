package com.cts.feign;

import com.cts.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-authentication")
public interface UserFeignClient {
    @GetMapping("/user/id/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id);
    @GetMapping("/user/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);
}
