package com.cts.controller;

import com.cts.dto.*;
import com.cts.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/add")
    public ResponseEntity<SubscriptionResponseDTO> create(@RequestBody SubscriptionRequestDTO dto,  @RequestHeader("X-User-Id") String id ) {
        try {
            return new ResponseEntity(subscriptionService.create(dto, id), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
        try {
            return new ResponseEntity(subscriptionService.getAllSubscriptions(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/active-subscribers")
    public ResponseEntity<Long> getActiveSubscribersCount() {
        long activeCount = subscriptionService.getActiveSubscriptionCount();
        return ResponseEntity.ok(activeCount);
    }

    @GetMapping("/admin/MRR")
    public ResponseEntity<BigDecimal> getMRR(){
        return ResponseEntity.ok(subscriptionService.calculateNormalizedMRR());
    }

    @GetMapping("/admin/ARR")
    public ResponseEntity<BigDecimal> getARR(){
        return ResponseEntity.ok(subscriptionService.getARR());
    }

    @GetMapping("/admin/ARPU")
    public ResponseEntity<BigDecimal> getARPU(){
        return ResponseEntity.ok(subscriptionService.getARPU());
    }

    @GetMapping("/admin/netAdds")
    public ResponseEntity<Long> getMonthlyNetAdds() {
        return ResponseEntity.ok(subscriptionService.getMonthlyNetAdds());
    }

    @GetMapping("/admin/churnCount")
    public ResponseEntity<Double> getRecentChurnCount() {
        return ResponseEntity.ok(subscriptionService.getRecentChurnCount());
    }

    @GetMapping("/admin/action/dailyExpiry")
    public ResponseEntity<Integer> dailyExpiryCheck() {
        return ResponseEntity.ok(subscriptionService.performDailyExpiryCheck());
    }

    @GetMapping("/admin/renewals")
    public ResponseEntity<Long> countUpcomingRenewals() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        Long renewals = subscriptionService.upcomingRenewals(today, thirtyDaysLater);

        return ResponseEntity.ok(renewals);
    }

    @GetMapping("/admin/charts/mrr-history")
    public ResponseEntity<List<ChartDataDTO>> getMrrHistory() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        return ResponseEntity.ok(subscriptionService.getMonthlyMRRHistory(sixMonthsAgo));
    }

    @GetMapping("/admin/charts/active-subscribers")
    public ResponseEntity<List<ChartDataDTO>> getActiveSubscribers() {
        List<ChartDataDTO> data = subscriptionService.getActiveSubscriberHistory();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/admin/plan-distribution")
    public ResponseEntity<List<PlanDistributionDTO>> getPlanDistribution() {
        return ResponseEntity.ok(subscriptionService.getActiveSubscriptionsByPlan());
    }

    @GetMapping("/admin/charts/mrr-by-plan")
    public ResponseEntity<List<Map<String, Object>>> getFormattedMrrByPlan() {
        return ResponseEntity.ok(subscriptionService.getMrrByPlan());
    }

    @GetMapping("/admin/charts/revenue-history")
    public ResponseEntity<List<Map<String,Object>>> getSubRevenue() {
        return ResponseEntity.ok(subscriptionService.getSubRevenueHistory());
    }
}
