package com.cts.repository;

import com.cts.dto.ChartDataDTO;
import com.cts.dto.RevenueDataDTO;
import com.cts.model.Subscription;
import com.cts.dto.PlanDistributionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT p.billingCycle, SUM(p.price) " +
            "FROM Subscription s JOIN s.plan p " +
            "WHERE s.status = 'Active' " +
            "GROUP BY p.billingCycle")
    List<Object[]> getRevenueByBillingCycle();

    @Query("SELECT SUM(p.price) FROM Subscription s JOIN s.plan p WHERE s.status = 'Active'")
    BigDecimal calculateTotalActiveRevenue();

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.status = 'Active'")
    long countByStatus();

    @Modifying
    @Query("UPDATE Subscription s SET s.status = 'Lapsed' " +
            "WHERE s.status = 'Grace' AND s.endDate <= :sevenDaysAgo")
    int updateStatusFromGraceToLapsed(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Modifying
    @Query("UPDATE Subscription s SET s.status = 'Grace' " +
            "WHERE s.status = 'Active' AND s.endDate <= :now")
    int updateStatusFromActiveToGrace(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(s) FROM Subscription s " +
            "WHERE s.endDate BETWEEN :today AND :thirtyDaysHence " +
            "AND s.status = 'Active'")
    Long countUpcomingRenewals(@Param("today") LocalDate today,
                               @Param("thirtyDaysHence") LocalDate thirtyDaysHence);

    @Query("SELECT new com.cts.dto.ChartDataDTO(" +
            "CAST(FUNCTION('DATE_FORMAT', s.startDate, '%Y-%m') AS string) AS monthGroup, " +
            "CAST(SUM(p.price) AS double)) " + // Force the sum to be a Double
            "FROM Subscription s JOIN s.plan p " +
            "WHERE s.startDate >= :sixMonthsAgo " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.startDate, '%Y-%m') " +
            "ORDER BY monthGroup ASC")
    List<ChartDataDTO> getMonthlyMRRHistory(@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

    @Query("SELECT new com.cts.dto.ChartDataDTO(" +
            "CAST(FUNCTION('DATE_FORMAT', s.startDate, '%Y-%m') AS string) AS monthGroup, " +
            "CAST(COUNT(s.subscriptionId) AS double)) " +
            "FROM Subscription s " +
            "WHERE s.startDate >= :sixMonthsAgo " +
            "GROUP BY monthGroup " +
            "ORDER BY monthGroup ASC")
    List<ChartDataDTO> getActiveSubscriberHistory(@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

    @Query("SELECT new com.cts.dto.PlanDistributionDTO(p.name, p.billingCycle, COUNT(s)) " +
            "FROM Subscription s JOIN s.plan p " +
            "WHERE s.status = 'Active' " +
            "GROUP BY p.name, p.billingCycle")
    List<PlanDistributionDTO> getPlanDistribution();

    @Query("SELECT new com.cts.dto.RevenueDataDTO(" +
            "CAST(FUNCTION('DATE_FORMAT', s.startDate, '%Y-%m') AS string), " +
            "p.name, " +
            "SUM(p.price)) " +
            "FROM Subscription s JOIN s.plan p " +
            "WHERE s.startDate >= :sixMonthsAgo " +
            "GROUP BY 1, p.name")
    List<RevenueDataDTO> getMrrByPlan(LocalDate sixMonthsAgo);

}
