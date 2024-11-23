package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interest_user")
@IdClass(InterestUserId.class)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InterestUser {

    @Id
    @ManyToOne
    @JoinColumn(name = "interest_id", referencedColumnName = "interest_id", nullable = false)
    private Interest interest;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
    private AppUser user;


}
