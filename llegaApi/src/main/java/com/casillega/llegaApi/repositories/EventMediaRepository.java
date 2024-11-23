package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.entities.EventMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventMediaRepository extends JpaRepository<EventMedia, Long> {
    List<EventMedia> findByEvent_EventId(long eventId);
}

