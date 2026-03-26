package com.cts.repository;

import com.cts.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title,Long> {
    @Query("SELECT t.applicationStatus, COUNT(t) FROM Title t GROUP BY t.applicationStatus")
    List<Object[]> countByStatus();

    @Query("SELECT t.titleId FROM Title t WHERE t.name = :name")
    Long findIdByName(@Param("name") String name);
}
