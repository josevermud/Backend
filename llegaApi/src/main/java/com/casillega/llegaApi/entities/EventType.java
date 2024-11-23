package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "event_type")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_type")
    private String eventType;


    public EventType(int eventTypeId) {
        this.id = eventTypeId;
    }
}
