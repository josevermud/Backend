package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.entities.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    @Transactional
    @Procedure(procedureName = "LookNotificationByCustomer")
    List<String> notificationsByUserId(@Param("app_userId") long userId);
}
