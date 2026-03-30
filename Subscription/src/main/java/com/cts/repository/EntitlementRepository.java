package com.cts.repository;

import com.cts.record.ExpiryDistributionDTO;
import com.cts.model.Entitlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntitlementRepository extends JpaRepository<Entitlement, Long> {
    @Query("SELECT COUNT(DISTINCT e.userId) FROM Entitlement e")
    Long countUniqueUsersWithEntitlements();

    @Query("SELECT e.contentScope, COUNT(e) FROM Entitlement e GROUP BY e.contentScope")
    List<Object[]> countEntitlementsByScope();

    @Query("SELECT new com.cts.record.ExpiryDistributionDTO(" +
            "SUM(CASE WHEN e.expiryDate <= :sevenDaysOut THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.expiryDate > :sevenDaysOut AND e.expiryDate <= :thirtyDaysOut THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.expiryDate > :thirtyDaysOut THEN 1 ELSE 0 END)) " +
            "FROM Entitlement e WHERE e.expiryDate >= CURRENT_DATE")
    ExpiryDistributionDTO getExpiryDistribution(@Param("sevenDaysOut") LocalDate sevenDaysOut, @Param("thirtyDaysOut") LocalDate thirtyDaysOut);

    Page<Entitlement> findAll(Pageable pageable);
}
