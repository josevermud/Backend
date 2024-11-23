package com.casillega.llegaApi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO  {
    private int eventId;
    private String createdById;
    private AppUserdto createdBy;
    private String caption;
    private String eventTypeId;
    private List<EventMediadto> eventTypeName;
    private String urlimage;
    private String comments;

    private String createdByName;
    // Getters and Setters
}
