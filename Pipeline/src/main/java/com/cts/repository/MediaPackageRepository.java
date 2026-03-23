package com.cts.repository;
import com.cts.model.MediaPackage;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaPackageRepository extends JpaRepository<MediaPackage,Long> {
    long countByQcStatus(MediaPackage.QCStatus status);

    long countByFormat(String format);


}
