package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_media_user_tag")
@IdClass(EventMediaUserTagId.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventMediaUserTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "event_media_id", referencedColumnName = "event_media_id", nullable = false)
    private EventMedia eventMedia;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
    private AppUser user;

    @Column(name = "position", nullable = false)
    private String position;

    // Getters and Setters
}
