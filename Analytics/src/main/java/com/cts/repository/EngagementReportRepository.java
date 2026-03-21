package com.cts.repository;

import com.cts.model.EngagementReport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EngagementReportRepository extends JpaRepository<EngagementReport,Long> {
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
