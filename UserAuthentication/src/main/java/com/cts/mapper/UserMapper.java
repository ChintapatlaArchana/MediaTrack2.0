package com.cts.mapper;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {User.Role.class})
public interface UserMapper {

    @Mapping(target = "role", source = "role", defaultValue = "Viewer") // default role
    @Mapping(target="status", source = "status", defaultValue = "ACTIVE")
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "role", expression = "java(entity.getRole())")
    UserResponseDTO toDTO(User entity);

}
