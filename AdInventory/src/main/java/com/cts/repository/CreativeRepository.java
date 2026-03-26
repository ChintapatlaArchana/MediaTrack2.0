package com.cts.repository;


import com.cts.model.Creative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreativeRepository extends JpaRepository <Creative, Long>{

    @Query("SELECT c.creativeId FROM Creative c WHERE c.advertiser = :advertiser")
    Optional<Long> findIdByAdvertiser(@Param("advertiser") String advertiser);
}
