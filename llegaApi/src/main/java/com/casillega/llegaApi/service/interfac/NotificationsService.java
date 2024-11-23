package com.casillega.llegaApi.service.interfac;

import com.casillega.llegaApi.entities.Notifications;
import com.casillega.llegaApi.repositories.NotificationsRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationsService extends NotificationsRepository {
    @Transactional
    List<String> notificationsByUserId(long userId);
}
