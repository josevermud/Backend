package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Override
    List<Event> findAll();
    //this could be a procedure ( one the of below)
    @Query("select e from Event e where e.createdBy.id = :userId")
    List<Event> findByCreatedByUser(int userId);

    @Query("select e from Event e where e.createdBy.id = :userId")
    List<Event> findByCreatedBy(int userId);

    @Query("select e from Event e where e.eventType.eventType = :eventTypeId")
    List<Event> findAllByeventByEventType(String eventTypeId);

    //this could be a procedure too
    @Query("select DISTINCT t.eventType from Event e inner JOIN EventType t on t.id = e.eventType.id ")
    List<String> findByAllEventType();
}
