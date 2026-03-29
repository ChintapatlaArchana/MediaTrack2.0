package com.cts.feign;

import com.cts.dto.UserResponseDTO;
import com.cts.record.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name="user-authentication")
public interface UserFeignClient {
    @GetMapping("/user/id/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") long id);

    @GetMapping("/user/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable("email") String email);

    @PostMapping("/user/admin/batch-details")
    Map<Long, UserDetailsDTO> getUserDetailsBatch(@RequestBody List<Long> userIds);

    @GetMapping("/user/admin/count")
    ResponseEntity<Long> getTotalUsers();
}


