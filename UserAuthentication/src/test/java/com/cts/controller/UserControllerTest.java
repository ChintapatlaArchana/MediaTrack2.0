package com.cts.controller;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.jwt.JWTService;
import com.cts.repository.UserRepository;
import com.cts.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_shouldReturn201() {
        UserRequestDTO req = new UserRequestDTO();
        req.setName("Archana");

        UserResponseDTO res = new UserResponseDTO();
        res.setUserId(1L);
        res.setName("Archana");

        when(userService.create(req)).thenReturn(res);

        ResponseEntity<UserResponseDTO> response = userController.create(req);

        // FIX: use getStatusCode() or getStatusCode().value()
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getUserId());
        verify(userService, times(1)).create(req);
    }

    @Test
    void getUserById_shouldReturn200() {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(10L);
        dto.setName("Test User");

        when(userService.getUserById(10L)).thenReturn(dto);

        ResponseEntity<UserResponseDTO> response = userController.getUserById(10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test User", response.getBody().getName());
    }

    @Test
    void getAllUsers_shouldReturn200() {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(1L);
        dto.setName("Sample");

        when(userService.getAllUsers()).thenReturn(List.of(dto));

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}