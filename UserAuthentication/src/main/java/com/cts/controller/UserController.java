package com.cts.controller;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.dto.AuthRequest;
import com.cts.exception.GlobalException;
import com.cts.jwt.JWTService;
import com.cts.model.User;
import com.cts.repository.UserRepository;
import com.cts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        try {
            return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
        } catch (GlobalException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        return userService.generateToken(authRequest);
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

//    @PutMapping("/user/id/oldPassword/newPassword")
//    public ResponseEntity<UserResponseDTO> updateUserPassword(@PathVariable long id, @PathVariable String oldPassword, @PathVariable String newPassword) {
//        try {
//            return new ResponseEntity<>(userService.updateUserPassword(id, oldPassword, newPassword), HttpStatus.OK);
//        } catch (GlobalException e) {
//            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
//        }
//    }
}

