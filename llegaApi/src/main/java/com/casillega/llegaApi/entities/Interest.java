package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotBlank;

@Entity

public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private int id;
    @Column(nullable = false,name = "interest_type")
    @NotBlank(message = "Please provide your type of Interest")
    private String interestType;
}
