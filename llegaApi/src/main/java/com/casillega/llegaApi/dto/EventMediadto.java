package com.casillega.llegaApi.dto;

import com.casillega.llegaApi.entities.Event;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventMediadto {

    private int eventMediaId;


    private Event event;

    private String mediaLocation;

}
