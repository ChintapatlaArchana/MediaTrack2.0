package com.cts.service;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.EntitlementMapper;
import com.cts.model.Entitlement;
import com.cts.repository.EntitlementRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EntitlementService {

    private final EntitlementRepository entitlementRepository;
    private final EntitlementMapper entitlementMapper;
    private final UserFeignClient userFeignClient;

    public EntitlementService(EntitlementRepository entitlementRepository, EntitlementMapper entitlementMapper, UserFeignClient userFeignClient) {
        this.entitlementRepository = entitlementRepository;
        this.entitlementMapper = entitlementMapper;
        this.userFeignClient = userFeignClient;
        log.info("Entitlement Service is being used");
    }

    public ResponseEntity<EntitlementResponseDTO> create(EntitlementRequestDTO dto, String id) {
        try{
            Long userId = Long.parseLong(id);
            ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = userFeignClient.getUserById(userId);
            if(userResponseDTOResponseEntity.getStatusCode().is2xxSuccessful() && userResponseDTOResponseEntity.getBody() != null) {
                Entitlement entitlement = entitlementMapper.toEntity(dto);
                entitlement.setUserId(userResponseDTOResponseEntity.getBody().getUserId());
                log.info("Entitlement added successfully");
                return new ResponseEntity(entitlementMapper.toDto(entitlementRepository.save(entitlement)), HttpStatus.CREATED);
            }
            log.warn("Problem in adding entitlement- User Id not found");
            throw new IllegalArgumentException("User Id not found");
        } catch (FeignException.FeignClientException e) {
            log.error("Feign Error in adding entitlement"+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public List<EntitlementResponseDTO> getAllEntitlements() {
        List<EntitlementResponseDTO> entitlementList = new ArrayList<>();
        try {
            if(entitlementRepository.findAll().size() == 0) {
                log.warn("Get all entitlements list is empty");
                throw new RuntimeException("No Entitlements");
            } else {
                for(Entitlement e : entitlementRepository.findAll()) {
                    entitlementList.add(entitlementMapper.toDto(e));
                }
                log.info("Returning all the entitlements");
                return entitlementList;
            }
        } catch (IllegalArgumentException e) {
            log.error("Error in returning all entitlements"+e.getMessage());
            throw new GlobalException("Can't get Entitlements");
        }
    }
}
