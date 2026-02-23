package com.cts.repository;
import com.cts.model.MediaPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaPackageRepository extends JpaRepository<MediaPackage,Long> {
}
