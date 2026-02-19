package com.cts.repository;

import com.cts.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title,Long> {
}
