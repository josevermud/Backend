package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location_event")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LocationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "address_details", columnDefinition = "TEXT")
    private String addressDetails;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;
}
