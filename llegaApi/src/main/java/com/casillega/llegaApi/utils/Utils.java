package com.casillega.llegaApi.utils;

import com.casillega.llegaApi.dto.AppUserdto;
import com.casillega.llegaApi.dto.CommentDTO;
import com.casillega.llegaApi.dto.EventDTO;
import com.casillega.llegaApi.dto.EventMediadto;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.Comment;
import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.entities.EventMedia;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();
    public static String generateRamdonAlphanumeric(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomOndex=secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar=ALPHANUMERIC_STRING.charAt(randomOndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public static AppUserdto mapUserDtoEntity(AppUser user){
        AppUserdto appUserdto = new AppUserdto();
        appUserdto.setId(user.getId());
        appUserdto.setUser_email(user.getUserEmail());
        appUserdto.setFull_name(user.getFullName());
        appUserdto.setUsername(user.getUsername());
        appUserdto.setRole(user.getRole());
        return appUserdto;
    }
    public static CommentDTO mapCommentDtoEntity(Comment comment){
    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setId(comment.getId());
    commentDTO.setUserId(comment.getUser().getId());
    commentDTO.setEventId(comment.getEvent().getEventId());
    commentDTO.setCommentText(comment.getCommentText());
        return commentDTO;
    }
    public static EventDTO mapeventEntitytoEntityDto(Event event){
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setCreatedById(event.getCreatedBy().getFullName());
        eventDTO.setCaption(event.getCaption());
        eventDTO.setEventTypeId(event.getEventType().getEventType());
        return eventDTO;
    }

    public static EventMediadto mapEventMediaEntityToEventMediadto(EventMedia eventMedia){
        EventMediadto eventMediadto = new EventMediadto();
        eventMediadto.setEventMediaId(eventMedia.getEventMediaId());
        eventMediadto.setEvent(eventMedia.getEvent());
        eventMediadto.setMediaLocation(eventMedia.getMediaLocation());
        return eventMediadto;

    }

    public static EventDTO mapeventEntitytoEntityDtoPlusItsMedia(Event event){
        EventDTO eventDTO = new EventDTO();

        eventDTO.setEventId(event.getEventId());
        eventDTO.setCreatedById(event.getCreatedBy().getFullName());
        eventDTO.setEventTypeId(event.getEventType().getEventType());

        if(event.getMedia() != null){
            eventDTO.setEventTypeName(event.getMedia().stream().map(Utils::mapEventMediaEntityToEventMediadto).collect(Collectors.toList()));
        }
        return eventDTO;
    }

    public static AppUserdto mapUserDtoEntityandEventOfUserAndItsMedia(AppUser user){
        AppUserdto appUserdto = new AppUserdto();
        appUserdto.setId(user.getId());
        appUserdto.setUser_email(user.getUserEmail());
        appUserdto.setFull_name(user.getFullName());
        appUserdto.setUsername(user.getUsername());
        appUserdto.setRole(user.getRole());

        if(!user.getEvents().isEmpty()){
            appUserdto.setEvents(user.getEvents().stream().map(event -> mapEventEntityToEventDtoPlusItsMedia(event,false)).collect(Collectors.toList()));
        }
        return appUserdto;
    }
    public static EventDTO mapEventEntityToEventDtoPlusItsMedia(Event event, boolean mapUser) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setCreatedById(event.getCreatedBy().getFullName()); // Setting the ID directly here if needed
        eventDTO.setCaption(event.getCaption());
        eventDTO.setEventTypeId(event.getEventType().getEventType());

        if (mapUser) {
            // Pass the AppUser entity directly
            eventDTO.setCreatedBy(Utils.mapUserDtoEntity(event.getCreatedBy()));
        }
        if (event.getMedia() != null) {
            List<EventMediadto> mediaDtos = event.getMedia().stream()
                    .map(media -> {
                        EventMediadto eventMediadto = new EventMediadto();
                        eventMediadto.setEventMediaId(media.getEventMediaId());
                        eventMediadto.setEvent(media.getEvent());
                        eventMediadto.setMediaLocation(media.getMediaLocation());
                        return eventMediadto;
                    })
                    .collect(Collectors.toList());
            eventDTO.setEventTypeName(mediaDtos);
        }
        return eventDTO;

    }
    public static List<AppUserdto> mapUserListEntitytouserListDto(List<AppUser> userList){
        return userList.stream().map(Utils::mapUserDtoEntity).collect(Collectors.toList());
    }
    public static List<CommentDTO> mapCommentListEntitytoCommentDto(List<Comment> commentList){
        return commentList.stream().map(Utils::mapCommentDtoEntity).collect(Collectors.toList());

    }
    public static List<EventDTO> mapEventListEntitytoEventListDto(List<Event> eventList){
        return eventList.stream().map(Utils::mapeventEntitytoEntityDto).collect(Collectors.toList());
    }
    public static List<EventMediadto> mapEventmediaListEntitytoEventmediaListDto(List<EventMedia> eventMediaList){
        return eventMediaList.stream().map(Utils::mapEventMediaEntityToEventMediadto).collect(Collectors.toList());
    }


}
