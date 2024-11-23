package com.casillega.llegaApi.controllers;


import com.casillega.llegaApi.dto.EventDTO;
import com.casillega.llegaApi.dto.Response;

import com.casillega.llegaApi.entities.EventType;

import com.casillega.llegaApi.repositories.EventTypeRepository;
import com.casillega.llegaApi.service.interfac.CommentService;
import com.casillega.llegaApi.service.interfac.EventMediaService;
import com.casillega.llegaApi.service.interfac.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {
    private CommentService commentService;
    private EventService eventService;
    @Autowired
    private EventMediaService eventMediaService;
    @Autowired
    EventTypeRepository eventTypeRepository ;
    
    public EventController(EventService eventService, CommentService commentService) {
        this.commentService = commentService;
        this.eventService = eventService;
    }


    @PostMapping("/add")
    public ResponseEntity<Response> addNewEvent(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "eventTypeId") String eventTypeName,
            @RequestParam(value = "caption") String caption,
            @RequestParam(value = "createdDate") String createdDateStr) {

        Response response = new Response();

        try {
            // Parse the date
            LocalDateTime createdDate = LocalDateTime.parse(createdDateStr);

            // Validate fields
            if (eventTypeName == null || caption.isBlank()) {
                response.setStatusCode(400);
                response.setMessage("Please fill all the required fields");
                return ResponseEntity.status(response.getStatusCode()).body(response);
            }

            // Fetch the EventType entity by its name

            EventType eventType = eventTypeRepository.findByEventType(eventTypeName)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid event type"));

            // Pass the extracted ID to the service layer
            response = eventService.addNewEvent(file, (long) eventType.getId(), caption, createdDate);

        } catch (IllegalArgumentException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding event: " + e.getMessage());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/all")
    public ResponseEntity<Response> getAlLEvents() {
        Response response = eventService.getAllEvent();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/types")
   // @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<String> getAllEventTypes() {
       return eventService.getAllEventTypes();
    }
    @GetMapping("/event/{eventId}")
    public ResponseEntity<Response> getEventById(@PathVariable String eventId) {
        Response response = eventService.getEventById(eventId);
        return ResponseEntity.status(response.getStatusCode()).body(response);        // Attach the body
    }

    @GetMapping("/get-user-event")
    public ResponseEntity<Response> getUserEvent() {
        Response response = eventService.findEventsByUserId();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/getEvents/byType/{eventTypeId}")
    public ResponseEntity<Response> getEventsByType(@PathVariable String eventTypeId) {
        Response response = eventService.getEventsByTypes(eventTypeId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/update/{eventId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Response> updateEvent(@PathVariable long eventId,@RequestParam MultipartFile file,
                                                @RequestParam(value = "eventType", required = false) EventType eventType,
                                                @RequestParam(value = "caption", required = false) String caption,
                                                @RequestParam(value = "createdDate", required = false) LocalDateTime createdDate
                                                ) {
        Response response = eventService.updateEvent( eventId,  file,  eventType,  caption,  createdDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Response> deleteEvent(@PathVariable long eventId) {
        Response response = eventService.deleteEvent(eventId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/comment/{eventId}")
    public ResponseEntity<Response> getAllCommentsByEvent(@PathVariable long eventId) {
        Response response = commentService.getAllCommentsByEventId(eventId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    //Long eventId, String commentText, long userId, LocalDateTime createdDate
    @PostMapping("/comment/add")
    public ResponseEntity<Response> addNewComment(
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "createdDate") String createdDateStr,
            @RequestBody Map<String, String> requestBody) { // Adjust to read the body
        String commentText = requestBody.get("commentText"); // Extract commentText from the body

        System.out.println("Received commentText: " + commentText); // Debug log

        LocalDateTime createdDate = LocalDateTime.parse(createdDateStr);
        Response response = commentService.addNewCommnet(eventId, commentText, createdDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<Response> deleteComment(@PathVariable long commentId) {
        Response response = commentService.deleteComment(commentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}

