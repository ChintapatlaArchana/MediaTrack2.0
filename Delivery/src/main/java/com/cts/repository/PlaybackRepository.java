package com.cts.repository;

import com.cts.model.PlaybackSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaybackRepository extends JpaRepository <PlaybackSession, Long> {
}
