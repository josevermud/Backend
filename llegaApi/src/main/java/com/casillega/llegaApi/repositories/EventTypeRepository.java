package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    Optional<EventType> findByEventType(String eventTypeName);
}
