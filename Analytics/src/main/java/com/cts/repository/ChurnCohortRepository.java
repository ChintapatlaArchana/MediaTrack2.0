package com.cts.repository;

import com.cts.model.ChurnCohort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurnCohortRepository extends JpaRepository<ChurnCohort, Long> {
}
