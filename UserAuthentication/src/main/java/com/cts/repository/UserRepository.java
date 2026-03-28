package com.cts.repository;

import com.cts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phNo);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<User> findByRoleNot(User.Role role);

    @Query("SELECT COUNT(u) FROM User u")
    long countTotalUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = com.cts.model.User.Role.Admin")
    long countByRole();

    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

    List<User> findAllByUserIdIn(List<Long> userIds);
}
