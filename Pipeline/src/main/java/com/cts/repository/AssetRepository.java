package com.cts.repository;

import com.cts.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssetRepository extends JpaRepository<Asset,Long>{
    List<Asset> findByTitle_TitleId(long titleId);
}
