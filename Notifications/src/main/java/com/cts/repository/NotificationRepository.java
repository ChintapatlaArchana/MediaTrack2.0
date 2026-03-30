package com.cts.repository;

import com.cts.model.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.status = 'Unread'")
    Long countUnreadNotifications();

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = 'Read' WHERE n.status = 'Unread'")
    void markAllAsRead();

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = 'Read' WHERE n.notificationId = :id")
    void markAsRead(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = 'Dismissed' WHERE n.notificationId = :id")
    void dismissNotification(@Param("id") Long id);
}
