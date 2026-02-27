package com.cts.service;

import com.cts.dto.SubscriptionRequestDTO;
import com.cts.dto.SubscriptionResponseDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.SubscriptionMapper;
import com.cts.model.Plan;
import com.cts.model.Subscription;
import com.cts.repository.PlanRepository;
import com.cts.repository.SubscriptionRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserFeignClient userFeignClient;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserFeignClient userFeignClient, PlanRepository planRepository,
                               SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userFeignClient = userFeignClient;
        this.planRepository = planRepository;
        this.subscriptionMapper = subscriptionMapper;
        log.info("Subscription Service is called");
    }

    public ResponseEntity<SubscriptionResponseDTO> create(SubscriptionRequestDTO dto, String id) {

        try {
            Long userId = Long.parseLong(id);
            ResponseEntity<UserResponseDTO> user = userFeignClient.getUserById(userId);
            Plan plan = planRepository.findById(dto.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan not found"));
            if(user.getStatusCode().is2xxSuccessful() && user.getBody() != null) {
                LocalDate start = dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now();

                LocalDate end = plan.getBillingCycle().equals(Plan.BillingCycle.Yearly)
                        ? start.plusYears(1)
                        : start.plusMonths(1);

                Subscription sub = new Subscription();
                System.out.println(user);
                System.out.println(plan);
                sub.setUserId(user.getBody().getUserId());
                sub.setPlan(plan);
                sub.setStartDate(start);
                sub.setEndDate(end);
                sub.setStatus(Subscription.Status.Active);
                log.info("Subscription Added");
                return new ResponseEntity<>(subscriptionMapper.toDTO(subscriptionRepository.save(sub)), HttpStatus.CREATED);
            }
            log.warn("UserId not found to add Subscription");
            throw new IllegalArgumentException("User id not present");
        } catch (RuntimeException e) {
            log.error("Cannot add the Subscription"+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public List<SubscriptionResponseDTO> getAllSubscriptions() {
        List<SubscriptionResponseDTO> subscriptionResponseDTOList = new ArrayList<>();
        if(subscriptionRepository.findAll().size() == 0) {
            log.warn("There are no Subscription");
            throw new GlobalException("No one took a subscription");
        } else {
            for (Subscription s: subscriptionRepository.findAll()) {
                subscriptionResponseDTOList.add(subscriptionMapper.toDTO(s));
            }
        }
        log.info("Getting all Subscription");
        return subscriptionResponseDTOList;
    }
}
