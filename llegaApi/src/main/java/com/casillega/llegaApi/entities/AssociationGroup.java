package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "association_group")
@IdClass(AssociationGroupId.class)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AssociationGroup {

    @Id
    @ManyToOne
    @JoinColumn(name = "following_user_id", referencedColumnName = "ID", nullable = false)
    private AppUser followingUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed_users_id", referencedColumnName = "ID", nullable = false)
    private AppUser followedUser;

    // Getters and Setters
}
