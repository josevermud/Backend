package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false, referencedColumnName = "ID")
    private AppUser createdBy;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "caption", columnDefinition = "TEXT")
    private String caption;
//
    @ManyToOne
    @JoinColumn(name = "event_type", nullable = false, referencedColumnName = "ID")
    private EventType eventType;

    // One-to-many relationship with EventMedia
    @OneToMany(mappedBy = "event")
    private List<EventMedia> media;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notification;

    @OneToMany(mappedBy = "id")
    private List<EventType> eventype;

}
