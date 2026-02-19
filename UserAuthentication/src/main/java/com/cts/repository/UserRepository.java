package com.cts.repository;

import com.cts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phNo);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
