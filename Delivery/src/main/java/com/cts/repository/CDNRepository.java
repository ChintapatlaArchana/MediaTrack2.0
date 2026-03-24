package com.cts.repository;

import com.cts.model.CDNEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CDNRepository extends JpaRepository <CDNEndpoint, Long> {

    long countByStatus(CDNEndpoint.Status status);

    List<CDNEndpoint> findByStatus(CDNEndpoint.Status status);

    List<CDNEndpoint> findByRegion(String region);
}
