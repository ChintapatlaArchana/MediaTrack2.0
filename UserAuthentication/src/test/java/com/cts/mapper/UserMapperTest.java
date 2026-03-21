package com.cts.mapper;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToEntity() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Archana");
        dto.setEmail("archana@test.com");
        dto.setPhone("99999");
        dto.setPassword("secret");

        User user = mapper.toEntity(dto);

        assertNotNull(user);
        assertEquals("Archana", user.getName());
        assertEquals("archana@test.com", user.getEmail());
        assertEquals("99999", user.getPhone());
        assertEquals(User.Role.Viewer, user.getRole()); // default
    }

    @Test
    void testToDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setName("Archana");
        user.setEmail("archana@test.com");
        user.setPhone("99999");
        user.setRole(User.Role.Admin);

        UserResponseDTO dto = mapper.toDTO(user);

        assertEquals(1L, dto.getUserId());
        assertEquals("Archana", dto.getName());
        assertEquals(User.Role.Admin, dto.getRole());
    }
}