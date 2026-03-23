////package com.cts.repository;
////
////
////import com.cts.model.Campaign;
////import org.springframework.data.jpa.repository.JpaRepository;
////
////public interface CampaignRepository extends JpaRepository <Campaign, Long>{
////}
//package com.cts.repository;
//
//import com.cts.model.Campaign;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import java.util.List;
//
//public interface CampaignRepository extends JpaRepository<Campaign, Long> {
//
//    // For the "Active Campaigns" KPI Card
//    long countByStatus(Campaign.Status status);
//
//
//
//    // For the "Active Campaigns" List at the bottom of the UI
//    // Fetches only ACTIVE and PAUSED, ordered by most recent
//    @Query("SELECT c FROM Campaign c WHERE c.status IN (com.cts.model.Campaign.Status.ACTIVE, com.cts.model.Campaign.Status.PAUSED) ORDER BY c.startDate DESC")
//    List<Campaign> findDashboardCampaigns();
//}
package com.cts.repository;

import com.cts.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // 1. For the KPI Card
    long countByStatus(Campaign.Status status);

    // 2. Fix for the dashboard list error
    // We pass the statuses as a list to avoid hardcoding the Enum path
    @Query("SELECT c FROM Campaign c WHERE c.status IN :statuses ORDER BY c.startDate DESC")
    List<Campaign> findDashboardCampaigns(@Param("statuses") List<Campaign.Status> statuses);
}