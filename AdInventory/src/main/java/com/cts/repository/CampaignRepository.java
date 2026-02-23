package com.cts.repository;


import com.cts.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository <Campaign, Long>{
}
