package com.cts.service;

import com.cts.dto.EntitlementRequestDTO;
import com.cts.dto.EntitlementResponseDTO;
import com.cts.record.ExpiryDistributionDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.EntitlementMapper;
import com.cts.model.Entitlement;
import com.cts.record.PolicyDriftDTO;
import com.cts.record.UserDetailsDTO;
import com.cts.repository.EntitlementRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Long getUniqueUserCount() {
        return entitlementRepository.countUniqueUsersWithEntitlements();
    }

    public Map<String, Long> getScopeDistribution() {
        List<Object[]> results = entitlementRepository.countEntitlementsByScope();
        return results.stream().collect(Collectors.toMap(
                array -> ((Entitlement.ContentScope) array[0]).name(),
                array -> (Long) array[1]
        ));
    }

    public ExpiryDistributionDTO getExpiringSoonStats() {
        LocalDate today = LocalDate.now();
        return entitlementRepository.getExpiryDistribution(
                today.plusDays(7),
                today.plusDays(30)
        );
    }

    public Page<PolicyDriftDTO> getPolicyDriftData(int page, int size) {
        // 1. Fetch paged entitlements (10 rows)
        Pageable pageable = PageRequest.of(page, size, Sort.by("grantedDate").descending());
        Page<Entitlement> entitlementPage = entitlementRepository.findAll(pageable);

        // 2. Extract unique User IDs from the current 10 rows
        List<Long> userIds = entitlementPage.getContent().stream()
                .map(Entitlement::getUserId)
                .distinct()
                .toList();

        // 3. Enrich with User Data via Feign (Batch Call)
        Map<Long, UserDetailsDTO> userMap = userFeignClient.getUserDetailsBatch(userIds);

        // 4. Transform into the final DTO list
        List<PolicyDriftDTO> dtos = entitlementPage.getContent().stream().map(e -> {
            var user = userMap.get(e.getUserId());
            return new PolicyDriftDTO(
                    (user != null ? user.name() : "Unknown"),
                    (user != null ? user.email() : "N/A"),
                    e.getContentScope().name(),
                    e.getGrantedDate(),
                    e.getExpiryDate()
            );
        }).toList();

        return new PageImpl<>(dtos, pageable, entitlementPage.getTotalElements());
    }

}
