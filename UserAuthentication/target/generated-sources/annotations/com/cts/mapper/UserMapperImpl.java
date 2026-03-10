package com.cts.mapper;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.model.User;
import com.cts.model.User.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-09T09:12:26+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        if ( dto.getRole() != null ) {
            user.setRole( dto.getRole() );
        }
        else {
            user.setRole( User.Role.Viewer );
        }
        user.setName( dto.getName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public UserResponseDTO toDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUserId( entity.getUserId() );
        userResponseDTO.setName( entity.getName() );
        userResponseDTO.setEmail( entity.getEmail() );
        userResponseDTO.setPhone( entity.getPhone() );

        userResponseDTO.setRole( entity.getRole() );

        return userResponseDTO;
    }
}
