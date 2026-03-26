package com.cts.repository;

import com.cts.dto.CampaignResponseDTO;
import com.cts.dto.DashboardSummaryDTO;
import com.cts.model.AdDeliveryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface AdDeliveryReportRepository extends JpaRepository<AdDeliveryReport ,Long> {

    @Query("SELECT SUM(a.metrics.impressions * a.metrics.eCPM / 1000) " +
            "FROM AdDeliveryReport a " +
            "WHERE a.generatedDate >= :startOfMonth")
    BigDecimal calculateMonthlyAdRevenue(@Param("startOfMonth") LocalDate startOfMonth);

    //FOR ADOPS DASHBOARD
    // Add to AdDeliveryReportRepository.java

    // AdDeliveryReportRepository.java

    @Query("SELECT COALESCE(SUM(a.metrics.impressions), 0) FROM AdDeliveryReport a")
    Long getTotalImpressions();

    @Query("SELECT COALESCE(AVG(a.metrics.CTR), 0.0) FROM AdDeliveryReport a")
    Double getAverageCTR();

    @Query("SELECT COALESCE(AVG(a.metrics.eCPM), 0.0) FROM AdDeliveryReport a")
    Double getAverageECPM();

    @Query("SELECT COUNT(DISTINCT a.campaignId) FROM AdDeliveryReport a")
    Long getActiveCampaignCount();

    // AdDeliveryReportRepository.java

    // For the Chart: Groups daily stats
    @Query("SELECT a.generatedDate, SUM(a.metrics.impressions), AVG(a.metrics.CTR) " +
            "FROM AdDeliveryReport a " +
            "GROUP BY a.generatedDate " +
            "ORDER BY a.generatedDate ASC")
    List<Object[]> getDailyChartData();

    // For Growth: Gets stats for a specific date range (e.g., last 30 days)
    @Query("SELECT SUM(a.metrics.impressions), AVG(a.metrics.CTR), AVG(a.metrics.eCPM), COUNT(DISTINCT a.campaignId) " +
            "FROM AdDeliveryReport a " +
            "WHERE a.generatedDate BETWEEN :startDate AND :endDate")
    List<Object[]> getStatsForPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT c.id as campaignId, c.name as campaignName, c.status as status, " +
            "SUM(r.impressions) as totalImpressions, SUM(r.clicks) as totalClicks, " +
            "c.budget as budget, SUM(r.spent_amount) as budgetSpent " +
            "FROM campaign c " +
            "LEFT JOIN ad_delivery_report r ON c.id = r.campaign_id " +
            "WHERE c.status = 'ACTIVE' OR c.status = 'PAUSED' " +
            "GROUP BY c.id, c.name, c.status, c.budget",
            nativeQuery = true)
    List<CampaignResponseDTO> findActiveCampaignsMetrics();
}


