package com.casillega.llegaApi.dto;

import com.casillega.llegaApi.entities.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;
    private AppUserdto user;
    private EventDTO eventDTO;
    private List<AppUserdto> userList;
    private List<EventDTO> eventList;
    private List<EventMediadto> eventMediadtoList;
    private List<CommentDTO> commentDTOList;
    private List<String> notifications;
    private CommentDTO commentDTO;

}
