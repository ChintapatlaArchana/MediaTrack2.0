package com.cts.repository;

import com.cts.model.AdDeliveryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AdDeliveryReportRepository extends JpaRepository<AdDeliveryReport ,Long> {

    @Query("SELECT SUM(a.metrics.impressions * a.metrics.eCPM / 1000) " +
            "FROM AdDeliveryReport a " +
            "WHERE a.generatedDate >= :startOfMonth")
    BigDecimal calculateMonthlyAdRevenue(@Param("startOfMonth") LocalDateTime startOfMonth);
}
