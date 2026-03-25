package com.cts.service;

import com.cts.dto.*;
import com.cts.exception.GlobalException;
import com.cts.feign.UserFeignClient;
import com.cts.mapper.SubscriptionMapper;
import com.cts.model.Plan;
import com.cts.model.Subscription;
import com.cts.repository.PlanRepository;
import com.cts.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public long getActiveSubscriptionCount() {
        return subscriptionRepository.countByStatus();
    }

    public BigDecimal calculateNormalizedMRR() {
        List<Object[]> results = subscriptionRepository.getRevenueByBillingCycle();
        BigDecimal totalMrr = BigDecimal.ZERO;

        for (Object[] result : results) {
            if (result[0] == null || result[1] == null) continue;

            String cycle = result[0].toString();

            BigDecimal revenue = new BigDecimal(result[1].toString());

            if ("YEARLY".equalsIgnoreCase(cycle)) {
                totalMrr = totalMrr.add(revenue.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP));
            } else if ("MONTHLY".equalsIgnoreCase(cycle)) {
                totalMrr = totalMrr.add(revenue);
            }
        }
        return totalMrr;
    }

    public BigDecimal getARR() {
        BigDecimal mrr = calculateNormalizedMRR();
        return mrr.multiply(new BigDecimal("12"));
    }

    public BigDecimal getARPU() {
        BigDecimal mrr = calculateNormalizedMRR();
        long activeUsers = subscriptionRepository.countByStatus();

        if (activeUsers == 0) return BigDecimal.ZERO;

        // Divide MRR by User Count, scale to 2 decimal places
        return mrr.divide(BigDecimal.valueOf(activeUsers), 2, RoundingMode.HALF_UP);
    }

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void expireSubscriptions() {
        log.info("Automated 10AM check started.");
        performDailyExpiryCheck();
    }

    @Transactional
    public int performDailyExpiryCheck() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(7);

        log.info("Starting subscription cleanup at {}", now);

        // STEP 1: Grace -> Lapsed (If they've been in Grace for > 7 days)
        int movedToLapsed = subscriptionRepository.updateStatusFromGraceToLapsed(sevenDaysAgo);
        log.info("Moved {} subscriptions from GRACE to LAPSED.", movedToLapsed);

        // STEP 2: Active -> Grace (If their EndDate has passed)
        int movedToGrace = subscriptionRepository.updateStatusFromActiveToGrace(now);
        log.info("Moved {} subscriptions from ACTIVE to GRACE.", movedToGrace);

        return movedToLapsed + movedToGrace;
    }

    public Long upcomingRenewals(LocalDate today, LocalDate thirtyDaysLater) {
        return subscriptionRepository.countUpcomingRenewals(today, thirtyDaysLater);
    }

    public List<ChartDataDTO> getMonthlyMRRHistory(LocalDate sixMonthsAgo) {
        return subscriptionRepository.getMonthlyMRRHistory(sixMonthsAgo);
    }

    public List<ChartDataDTO> getActiveSubscriberHistory() {
        // Calculate the date 6 months ago from today
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        return subscriptionRepository.getActiveSubscriberHistory(sixMonthsAgo);
    }

    public List<PlanDistributionDTO> getActiveSubscriptionsByPlan() {
        return subscriptionRepository.getPlanDistribution();
    }

    public Map<String, Map<String, Object>> getFormattedBillingMix() {
        List<PlanDistributionDTO> rawData = subscriptionRepository.getPlanDistribution();

        return rawData.stream().collect(Collectors.groupingBy(
                PlanDistributionDTO::getPlanName,
                Collectors.collectingAndThen(Collectors.toList(), list -> {

                    long monthly = list.stream()
                            .filter(d -> "MONTHLY".equalsIgnoreCase(d.getBillingCycle()))
                            .mapToLong(PlanDistributionDTO::getCount).sum();

                    long yearly = list.stream()
                            .filter(d -> "YEARLY".equalsIgnoreCase(d.getBillingCycle()))
                            .mapToLong(PlanDistributionDTO::getCount).sum();

                    Map<String, Object> stats = new HashMap<>();
                    stats.put("monthly", monthly);
                    stats.put("yearly", yearly);
                    stats.put("total", monthly + yearly);
                    return stats;
                })
        ));
    }

    public List<Map<String, Object>> getMrrByPlan() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        List<RevenueDataDTO> rawData = subscriptionRepository.getMrrByPlan(sixMonthsAgo);

        // Grouping the flat list into the format the chart needs
        Map<String, Map<String, Object>> grouped = new LinkedHashMap<>();

        for (RevenueDataDTO data : rawData) {
            grouped.computeIfAbsent(data.getMonth(), k -> {
                Map<String, Object> m = new HashMap<>();
                m.put("month", k);
                return m;
            }).put(data.getCategory(), data.getAmount());
        }

        return new ArrayList<>(grouped.values());
    }

    public Double getRecentChurnCount() {

        long totalUsersAtStart = userFeignClient.getTotalUsers().getBody();

        List<Subscription.Status> churnStatuses = List.of(
                Subscription.Status.Lapsed,
                Subscription.Status.Cancelled
        );
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

        long lostUsers = subscriptionRepository.countByStatusInAndEndDateAfter(
                churnStatuses,
                thirtyDaysAgo
        );

        if (totalUsersAtStart == 0) return 0.0;

        return ((double) lostUsers / totalUsersAtStart) * 100;
    }
}
