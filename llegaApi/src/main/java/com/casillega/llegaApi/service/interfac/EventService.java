package com.casillega.llegaApi.service.interfac;


import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.entities.EventType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Response addNewEvent (MultipartFile file, Long eventTypeId, String caption, LocalDateTime createdDate);
    Response getAllEvent();
    List<String> getAllEventTypes();
    Response deleteEvent(long eventId);
    Response updateEvent(long eventId, MultipartFile file, EventType eventType, String caption, LocalDateTime updatedDate);
    Response getEventById(String eventId);
    Response getEventsByTypes(String eventTypeId);


    Response findEventsByUserId();
}
