package com.cts.controller;

import com.cts.dto.*;
import com.cts.exception.GlobalException;
import com.cts.jwt.JWTService;
import com.cts.model.User;
import com.cts.repository.UserRepository;
import com.cts.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        try {
            return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> generateToken(@RequestBody AuthRequest authRequest) {
        System.out.println("Controller came here line 51");
        try {
            Authentication authentication =  authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if(authentication.isAuthenticated()) {
                log.info("Generating user token");
                User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new RuntimeException("Invalid Email"));
                String token = jwtService.generateToken(user);
                String role = user.getRole().toString();
                LoginResponse response = new LoginResponse(token, role);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.warn("UserId is not found to generate user token");
                throw new IllegalArgumentException("User Not Found");
            }
        } catch (AuthenticationException e){
            log.error("Error in generating user token "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.FOUND);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/email1/email2")
    public ResponseEntity<UserResponseDTO> updateUserEmail(@PathVariable long id, @PathVariable String email1, @PathVariable String email2) {
        try {
            return new ResponseEntity<>(userService.updateUserEmail(id, email1, email2), HttpStatus.OK);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/id/phoneNo1/phoneNo2")
    public ResponseEntity<UserResponseDTO> updateUserPhoneNo(@PathVariable long id, @PathVariable String phoneNo1, @PathVariable String phoneNo2) {
        try {
            return new ResponseEntity<>(userService.updateUserPhoneNo(id, phoneNo1, phoneNo2), HttpStatus.OK);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/admin/count")
    public ResponseEntity<Long> getTotalUsers() {
        long count = userService.getTotalUserCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/admin/unique-user-ids")
    public ResponseEntity<Long> getUniqueUserIds() {
        return ResponseEntity.ok(userService.findDistinctUserIds());
    }

    @GetMapping("/admin/admin-stats")
    public ResponseEntity<Long> countUserByRole() {
        return ResponseEntity.ok(userService.countByRoleAdmin());
    }

    @GetMapping("/admin/role-distribution")
    public ResponseEntity<Map<String, Long>> allUserCountByRole() {
        return ResponseEntity.ok(userService.getUserRoleDistribution());
    }

    @PostMapping("/admin/batch-details")
    public Map<Long, UserDetailsDTO> getBatchDetails(@RequestBody List<Long> userIds) {
        return userRepository.findAllByUserIdIn(userIds).stream()
                .collect(Collectors.toMap(
                        User::getUserId,
                        u -> new UserDetailsDTO(u.getName(), u.getEmail())
                ));
    }

//    @PutMapping("/user/id/oldPassword/newPassword")
//    public ResponseEntity<UserResponseDTO> updateUserPassword(@PathVariable long id, @PathVariable String oldPassword, @PathVariable String newPassword) {
//        try {
//            return new ResponseEntity<>(userService.updateUserPassword(id, oldPassword, newPassword), HttpStatus.OK);
//        } catch (GlobalException e) {
//            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
//        }
//    }

//    @PostMapping("/user/login/email")
//    public ResponseEntity<UserResponseDTO> loginWithEmail(@RequestBody UserRequestDTO dto) {
//        try {
//            return new ResponseEntity<>(userService.loginWithEmail(dto), HttpStatus.FOUND);
//        } catch (GlobalException e) {
//            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/user/login/ph")
//    public ResponseEntity<UserResponseDTO> loginWithPhoneNo(@RequestBody UserRequestDTO dto) {
//        try {
//            return new ResponseEntity<>(userService.loginWithPhoneNo(dto), HttpStatus.FOUND);
//        } catch (GlobalException e) {
//            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
}

