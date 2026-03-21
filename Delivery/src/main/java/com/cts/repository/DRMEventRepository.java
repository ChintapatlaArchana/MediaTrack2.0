package com.cts.repository;


import com.cts.model.DRMEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DRMEventRepository  extends JpaRepository<DRMEvent, Long> {
    List<DRMEvent> findByPlaybackSession_SessionId(Long sessionId);

    long countByLicenseStatus(DRMEvent.LicenseStatus status);
}
