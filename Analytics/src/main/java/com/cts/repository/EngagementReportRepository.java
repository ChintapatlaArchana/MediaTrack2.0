package com.cts.repository;

import com.cts.model.EngagementReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EngagementReportRepository extends JpaRepository<EngagementReport,Long> {

    @Query("SELECT AVG(e.engagementReportMetrics.dau), " +
            "AVG(e.engagementReportMetrics.mau), " +
            "AVG(e.engagementReportMetrics.watchTimeSeconds), " +
            "AVG(e.engagementReportMetrics.completionRate) " +
            "FROM EngagementReport e")
    List<Object[]> findGlobalAverages();

    @Query("SELECT e FROM EngagementReport e " +
            "WHERE e.generatedDate >= :startDate " +
            "ORDER BY e.generatedDate ASC")
    List<EngagementReport> findReportsInDateRange(@Param("startDate") LocalDate startDate);

    @Query("SELECT e.generatedDate, e.engagementReportMetrics.watchTimeSeconds " +
            "FROM EngagementReport e " +
            "WHERE e.generatedDate >= :startDate " +
            "ORDER BY e.generatedDate ASC")
    List<Object[]> findWatchTimeTrend(@Param("startDate") LocalDate startDate);

    @Query("SELECT (CAST(e.engagementReportMetrics.dau AS double) / e.engagementReportMetrics.mau) " +
            "FROM EngagementReport e " +
            "ORDER BY e.generatedDate DESC")
    List<Double> findLatestDauMauRatio(Pageable pageable);

    // Default method to get just the single latest value
    default Double getLatestRatio() {
        List<Double> results = findLatestDauMauRatio(PageRequest.of(0, 1));
        return results.isEmpty() ? 0.0 : results.get(0);
    }
}
