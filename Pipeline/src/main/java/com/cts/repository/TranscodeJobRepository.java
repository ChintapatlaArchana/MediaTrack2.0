package com.cts.repository;

import com.cts.model.TranscodeJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscodeJobRepository extends JpaRepository <TranscodeJob,Long> {
}
