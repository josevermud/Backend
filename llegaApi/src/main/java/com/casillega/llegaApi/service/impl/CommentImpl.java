package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.dto.CommentDTO;
import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.Comment;
import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.exception.OurException;
import com.casillega.llegaApi.repositories.AppUserRepository;
import com.casillega.llegaApi.repositories.CommentRepository;
import com.casillega.llegaApi.repositories.EventRepository;
import com.casillega.llegaApi.service.interfac.CommentService;
import com.casillega.llegaApi.utils.Utils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class CommentImpl implements CommentService {
    private CommentRepository commentRepository;
    private AppUserRepository appUserRepository;
    private EventRepository eventRepository;
    public CommentImpl (CommentRepository commentRepository, AppUserRepository appUserRepository, EventRepository eventRepository) {
        this.appUserRepository = appUserRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public Response getAllCommentsByEventId(long eventId) {
        List<Object[]> results = commentRepository.getCommentsByEventId((int) eventId);
        List<CommentDTO> comments = new ArrayList<>();
        Response response = new Response();

        for (Object[] row : results) {
            int id = ((Number) row[0]).intValue();
            String commentText = (String) row[1];
            int userId = ((Number) row[2]).intValue();
            int eventIdResult = ((Number) row[3]).intValue();
            String userName = appUserRepository.findById(Long.valueOf(userId)).get().getUserName();

            comments.add(new CommentDTO(id, commentText, userId, eventIdResult, userName));
        }
        response.setStatusCode(200);
        response.setMessage("Success");
        response.setCommentDTOList(comments);

        return  response;
    }

    @Override
    public Response addNewCommnet(Long eventId, String commentText, LocalDateTime createdDate) {
        Response response = new Response();
        try {
            // Log the parameters received
            System.out.println("Received eventId: " + eventId);
            System.out.println("Received commentText: " + commentText);
            System.out.println("Received createdDate: " + createdDate);

            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new OurException("Event not found"));

            Comment comment = new Comment();
            comment.setCreatedDate(createdDate);
            comment.setCommentText(commentText); // Ensure this is not null
            comment.setUser(currentUser);
            comment.setEvent(event);

            // Log the comment object before saving
            System.out.println("Comment to save: " + comment);

            Comment savedComment = commentRepository.save(comment);

            CommentDTO commentDTO = Utils.mapCommentDtoEntity(savedComment);
            response.setStatusCode(200);
            response.setMessage("Success ");
            response.setCommentDTO(commentDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            response.setStatusCode(400);
            response.setMessage("Failed to add comment: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteComment( Long commentId) {
        Response response = new Response();
        try {
            // Retrieve the event
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new OurException("Comment not found"));

            // Retrieve the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new OurException("User not found"));

            // Check if the current user is the creator of the event
            if (comment.getUser().getId() == currentUser.getId() || "ADMIN".equals(currentUser.getRole())) {
                commentRepository.deleteById(commentId);
                response.setStatusCode(200);
                response.setMessage("Comment deleted successfully");
            } else {
                response.setStatusCode(403); // Forbidden
                response.setMessage("You do not have permission to delete this comment.");
            }

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting comment: " + e.getMessage());
        }
        return response;

    }


}
