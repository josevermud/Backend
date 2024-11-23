package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reaction")
@IdClass(ReactionId.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reaction {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
    private AppUser user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @Column(name = "reaction_type", nullable = false)
    private String reactionType;

    // Getters and Setters
}
