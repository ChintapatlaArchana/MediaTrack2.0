package com.cts.repository;

import com.cts.model.Entitlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntitlementRepository extends JpaRepository<Entitlement, Long> {
}
