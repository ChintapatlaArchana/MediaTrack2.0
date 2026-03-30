package com.cts.service;

import com.cts.dto.AuthRequest;
import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.jwt.JWTService;
import com.cts.mapper.UserMapper;
import com.cts.model.User;
import com.cts.principal.UserPrincipal;
import com.cts.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        log.error("User service is being used");

    }

    public UserResponseDTO create(UserRequestDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            log.warn("User cannot be created as email already exists");
            throw new GlobalException("Email is already registered");
        } else if (userRepository.existsByPhone(dto.getPhone())) {
            log.warn("User cannot be created as phone number already exists");
            throw new GlobalException("Phone number is already registered");
        }
        User entity = userMapper.toEntity(dto);
        entity.setPassword(new BCryptPasswordEncoder(12).encode(dto.getPassword())); // glue step
        User saved = userRepository.save(entity);
        log.info("New got user added");
        return userMapper.toDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        if(userRepository.findByRoleNot(User.Role.Viewer).isEmpty()) {
            log.warn("No user got registered to get all users list");
            throw new GlobalException("No one got registered");
        } else {
            for (User u: userRepository.findByRoleNot(User.Role.Viewer)) {
                userResponseDTOList.add(userMapper.toDTO(u));
            }
        }
        log.info("Returning all users list");
        return userResponseDTOList;
    }

    public UserResponseDTO getUserById(long id) {
        User user;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
            log.info("User data is getting fetched using userId");
        } catch (Exception e) {
            log.error("Error in getting user using userId"+e.getMessage());
            throw new GlobalException("User Id not found");
        }
        return userMapper.toDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user;
        try {
            user = userRepository.findByEmail(email).orElseThrow(() -> new GlobalException("Invalid Email Id"));
            log.info("User is fetched using email");
        } catch (Exception e) {
            log.error("Error in getting user using email"+e.getMessage());
            throw new GlobalException("User Id not found");
        }
        return userMapper.toDTO(user);
    }

    public UserResponseDTO updateUserEmail(long id,String oldEmail ,String newEmail) {
        User userById = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
        if(!userById.getEmail().equals(oldEmail)) {
            log.warn("User want to change password but old password is not matching");
            throw new GlobalException("Invalid Old Email");
        } else {
            userById.setEmail(newEmail);
        }
        return userMapper.toDTO(userById);
    }

    public UserResponseDTO updateUserPhoneNo(long id, String oldPhoneNo, String newPhoneNo) {
        User userById = userRepository.findById(id).orElseThrow(() -> new GlobalException("Invalid User Id"));
        if (!userById.getPhone().equals(oldPhoneNo)) {
            log.warn("User want to change password but old email is not matching");
            throw new GlobalException("Invalid Old Phone Number");
        } else {
            userById.setPhone(newPhoneNo);
        }
        return userMapper.toDTO(userById);
    }

    public long getTotalUserCount() {
        return userRepository.count();
    }

    public long findDistinctUserIds() {
        return userRepository.countTotalUsers();
    }

    public long countByRoleAdmin() {
        return userRepository.countByRole();
    }

    public Map<String, Long> getUserRoleDistribution() {
        List<Object[]> results = userRepository.countUsersByRole();
        return results.stream().collect(Collectors.toMap(
                array -> ((User.Role) array[0]).name(),
                array -> (Long) array[1]
        ));
    }

    @Transactional
    public UserResponseDTO partialUpdate(long id, UserRequestDTO updateDto) {
        User existing = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        log.info(updateDto+" this is the update DTO");
        if (updateDto.getName() != null) existing.setName(updateDto.getName());
        if (updateDto.getEmail() != null) existing.setEmail(updateDto.getEmail());
        if (updateDto.getPhone() != null) existing.setPhone(updateDto.getPhone());
        if (updateDto.getRole() != null) existing.setRole(updateDto.getRole());
        if (updateDto.getStatus() != null) existing.setStatus(User.Status.valueOf(updateDto.getStatus()));
        log.info("User details updated" + existing);
        return userMapper.toDTO(existing);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        final User user;
        if (id != null && id.matches("^\\d+$")) {
            user = userRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new UsernameNotFoundException("User Id not found"));
        } else {
            user = userRepository.findByEmail(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User Email not found"));
        }
        return new UserPrincipal(user);
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


}
