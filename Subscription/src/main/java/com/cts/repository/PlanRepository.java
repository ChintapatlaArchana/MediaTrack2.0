package com.cts.repository;

import com.cts.model.Plan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Plan p WHERE p.name = :name AND p.billingCycle = :cycle")
    void deleteByNameAndBillingCycle(@Param("name") String name, @Param("cycle") Plan.BillingCycle cycle);
}
