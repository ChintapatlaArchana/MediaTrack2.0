package com.cts.service;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.UserMapper;
import com.cts.model.User;
import com.cts.principal.UserPrincipal;
import com.cts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public UserResponseDTO create(UserRequestDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new GlobalException("Email is already registered");
        } else if (userRepository.existsByPhone(dto.getPhone())) {
            throw new GlobalException("Phone number is already registered");
        }
        User entity = userMapper.toEntity(dto);
        entity.setPassword(new BCryptPasswordEncoder(12).encode(dto.getPassword())); // glue step
        User saved = userRepository.save(entity);
        return userMapper.toDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        if(userRepository.findAll().size() == 0) {
            throw new GlobalException("No one got registered");
        } else {
            for (User u: userRepository.findAll()) {
                userResponseDTOList.add(userMapper.toDTO(u));
            }
        }
        return userResponseDTOList;
    }

    public UserResponseDTO getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
        return userMapper.toDTO(user);
    }

//    public UserResponseDTO loginWithEmail(UserRequestDTO dto) {
//        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new GlobalException("Invalid credentials"));
//        boolean ok = encoder.matches(dto.getPassword(), user.getPassword());
//        if(!ok) {
//            throw new GlobalException("Invalid credentials");
//        }
//        return userMapper.toDTO(user);
//    }
//
//    public UserResponseDTO loginWithPhoneNo(UserRequestDTO dto) {
//        User user = userRepository.findByPhone(dto.getPhone()).orElseThrow(()-> new GlobalException("Invalid credentials"));
//        boolean ok = encoder.matches(dto.getPassword(), user.getPassword());
//        if(!ok) {
//            throw new GlobalException("Invalid credentials");
//        }
//        return userMapper.toDTO(user);
//    }

    public UserResponseDTO updateUserEmail(long id,String oldEmail ,String newEmail) {
        User userById = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
        if(!userById.getEmail().equals(oldEmail)) {
            throw new GlobalException("Invalid Old Email");
        } else {
            userById.setEmail(newEmail);
        }
        return userMapper.toDTO(userById);
    }

    public UserResponseDTO updateUserPhoneNo(long id, String oldPhoneNo, String newPhoneNo) {
        User userById = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
        if (!userById.getPhone().equals(oldPhoneNo)) {
            throw new GlobalException("Invalid Old Phone Number");
        } else {
            userById.setPhone(newPhoneNo);
        }
        return userMapper.toDTO(userById);
    }

//    public UserResponseDTO updateUserPassword(long id, String oldPassword, String newPassword) {
//        User userById = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
//        if (!encoder.matches(oldPassword, newPassword)) {
//            throw new GlobalException("Invalid Old Password");
//        } else {
//            userById.setPhone(newPassword);
//        }
//        return userMapper.toDTO(userById);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new UserPrincipal(user);

    }
}
