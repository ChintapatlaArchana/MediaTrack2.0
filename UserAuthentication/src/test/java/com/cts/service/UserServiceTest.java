package com.cts.service;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.mapper.UserMapper;
import com.cts.model.User;
import com.cts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void testCreateUser() {
        UserRequestDTO req = new UserRequestDTO();
        req.setName("Archana");
        req.setEmail("a@test.com");
        req.setPhone("1111");
        req.setPassword("pwd");

        User entity = new User();
        entity.setUserId(1L);

        UserResponseDTO response = new UserResponseDTO();
        response.setUserId(1L);

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(response);

        UserResponseDTO result = service.create(req);

        assertEquals(1L, result.getUserId());
        verify(repo, times(1)).save(entity);
    }
}