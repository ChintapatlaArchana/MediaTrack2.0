package com.cts.repository;

import com.cts.model.IngestJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngestJobRepository extends JpaRepository<IngestJob, Long> {
    long countByIngestStatus(IngestJob.IngestStatus status);
}
