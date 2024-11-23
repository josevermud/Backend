package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.dto.EventDTO;
import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.entities.EventMedia;
import com.casillega.llegaApi.entities.EventType;
import com.casillega.llegaApi.exception.OurException;
import com.casillega.llegaApi.repositories.AppUserRepository;
import com.casillega.llegaApi.repositories.EventMediaRepository;
import com.casillega.llegaApi.repositories.EventRepository;
import com.casillega.llegaApi.repositories.EventTypeRepository;
import com.casillega.llegaApi.service.interfac.EventMediaService;
import com.casillega.llegaApi.service.interfac.EventService;
import com.casillega.llegaApi.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;

    private final EventMediaRepository eventMediaRepository;

    private final EventMediaService fileService;

    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;

    public EventServiceImpl(EventRepository eventRepository, EventMediaRepository eventMediaRepository, FileServiceImpl fileService, EventTypeRepository eventTypeRepository, AppUserRepository appUserRepository) {
        this.eventRepository = eventRepository;
        this.eventMediaRepository = eventMediaRepository;
        this.fileService = fileService;
        this.eventTypeRepository = eventTypeRepository;
        this.appUserRepository = appUserRepository;
    }


    private EventTypeRepository eventTypeRepository;


    private AppUserRepository appUserRepository;


    @Override
    public Response addNewEvent(MultipartFile file, Long eventTypeId, String caption, LocalDateTime createdDate) {
        Response response = new Response();
        try {
            // Fetch EventType and AppUser entities by their IDs
            EventType eventType = eventTypeRepository.findById(eventTypeId)
                    .orElseThrow(() -> new OurException("EventType not found"));
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));

            // Create and save the Event entity
            Event event = new Event();
            event.setCreatedBy(currentUser);
            event.setCreatedDate(createdDate);
            event.setCaption(caption);
            event.setEventType(eventType);
            Event savedEvent = eventRepository.save(event);

            // Map saved Event to EventDTO
            EventDTO eventDTO = Utils.mapeventEntitytoEntityDto(savedEvent);

            // File upload logic
            String fileUrl = null;
            if (file != null && !file.isEmpty()) {
                String fileName = fileService.uploadFile(path, file);
                fileUrl = baseUrl + "/file/" + fileName;

                // Save EventMedia entry
                EventMedia eventMedia = new EventMedia();
                eventMedia.setEvent(savedEvent);
                eventMedia.setMediaLocation(fileUrl);
                eventMediaRepository.save(eventMedia);
                eventDTO.setUrlimage(fileUrl);

            }

            response.setStatusCode(200);
            response.setMessage("Success ");
            response.setEventDTO(eventDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding event: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllEvent() {
        Response response = new Response();
        try {
            // Fetch all events sorted by eventId in descending order, with media preloaded
            List<Event> events = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventId"));

            // Map events to DTOs
            List<EventDTO> eventDTOList = Utils.mapEventListEntitytoEventListDto(events);

            // Manually set the fileUrl in each EventDTO from the associated EventMedia
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                EventDTO eventDTO = eventDTOList.get(i);

                // If the event has associated media, set the file URL
                if (event.getMedia() != null && !event.getMedia().isEmpty()) {
                    eventDTO.setUrlimage(event.getMedia().get(0).getMediaLocation()); // Set URL of the first media
                }

            }

            response.setStatusCode(200);
            response.setMessage("Success");
            response.setEventList(eventDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all the events: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllEventTypes() {
        return eventRepository.findByAllEventType();
    }

    @Override
    public Response deleteEvent(long eventId) {
        Response response = new Response();
        try {
            // Retrieve the event
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new OurException("Event not found"));

            // Retrieve the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));

            // Check if the current user is the creator of the event
            if (event.getCreatedBy().getId() == currentUser.getId() || "ADMIN".equals(currentUser.getRole())) {
                eventRepository.deleteById(eventId);
                response.setStatusCode(200);
                response.setMessage("Event deleted successfully");
            } else {
                response.setStatusCode(403); // Forbidden
                response.setMessage("You do not have permission to delete this event.");
            }

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting event: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateEvent(long eventId, MultipartFile file, EventType eventType, String caption, LocalDateTime updatedDate) {
        Response response = new Response();
        try {
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new OurException("event not found "));
            String fileUrl = null;
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));
            if (event.getCreatedBy().getId() == currentUser.getId() || "ADMIN".equals(currentUser.getRole())) {
                eventRepository.deleteById(eventId);
                response.setStatusCode(200);
                response.setMessage("Event deleted successfully");
//
                if (file != null && !file.isEmpty()) {
                    String fileName = fileService.uploadFile(path, file);
                    fileUrl = baseUrl + "/file/" + fileName;
                    EventMedia eventMedia = new EventMedia();
                    eventMedia.setEvent(event);
                    eventMedia.setMediaLocation(fileUrl);
                    eventMediaRepository.save(eventMedia);
                }
                if (eventType != null) event.setEventType(eventType);
                if (caption != null) event.setCaption(caption);
                if (updatedDate != null) event.setCreatedDate(updatedDate);
                Event updatedEvent = eventRepository.save(event);
                EventDTO eventDTO = Utils.mapeventEntitytoEntityDto(updatedEvent);

                response.setStatusCode(200);
                response.setMessage("Success");
                response.setEventDTO(eventDTO);
            } else {
                response.setStatusCode(403); // Forbidden
                response.setMessage("You do not have permission to delete this event.");

            }
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage() + "Error deleting event ");
        }
        return response;
    }


    @Override
    public Response getEventById(String eventId) {
        Response response = new Response();
        try {
            Event event = eventRepository.findById(Long.parseLong(eventId)).orElseThrow(() -> new OurException("event not found "));
            EventDTO eventDTO = Utils.mapeventEntitytoEntityDto(event);
            if (
                    event.getMedia() != null && !event.getMedia().isEmpty()
            ) {
                eventDTO.setUrlimage(event.getMedia().get(0).getMediaLocation());
            }
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setEventDTO(eventDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage() + "Error getting event by id ");
        }
        return response;
    }

    @Override
    public Response getEventsByTypes(String eventTypeId) {
        Response response = new Response();
        try {
            List<Event> events = eventRepository.findAllByeventByEventType(eventTypeId);

            List<EventDTO> eventDTOList = Utils.mapEventListEntitytoEventListDto(events);
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                EventDTO eventDTO = eventDTOList.get(i);

                // If the event has associated media, set the file URL
                if (event.getMedia() != null && !event.getMedia().isEmpty()) {
                    eventDTO.setUrlimage(event.getMedia().get(0).getMediaLocation()); // Set URL of the first media
                }

            }

            response.setStatusCode(200);
            response.setMessage("Success");
            response.setEventList(eventDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage() + "Error getting event by id ");
        }
        return response;
    }

    @Override
    public Response findEventsByUserId() {
        Response response = new Response();
        try {
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));
            List<Event> events = eventRepository.findByCreatedByUser(currentUser.getId());

            // Map events to DTOs
            List<EventDTO> eventDTOList = Utils.mapEventListEntitytoEventListDto(events);

            // Manually set the fileUrl in each EventDTO from the associated EventMedia
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                EventDTO eventDTO = eventDTOList.get(i);

                // If the event has associated media, set the file URL
                if (event.getMedia() != null && !event.getMedia().isEmpty()) {
                    eventDTO.setUrlimage(event.getMedia().get(0).getMediaLocation()); // Set URL of the first media
                }

            }

            response.setStatusCode(200);
            response.setMessage("Success");
            response.setEventList(eventDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage() + "Error getting event by id ");
        }
        return response;
    }
}




