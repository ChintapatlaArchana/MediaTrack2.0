package com.cts.repository;

import com.cts.model.ContentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentVersionRepository extends JpaRepository<ContentVersion,Long> {
    //@Override
    List<ContentVersion> findByAsset_AssetId(Long assetId);
}
