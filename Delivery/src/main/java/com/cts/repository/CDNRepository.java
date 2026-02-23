package com.cts.repository;

import com.cts.model.CDNEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDNRepository extends JpaRepository <CDNEndpoint, Long> {
}
