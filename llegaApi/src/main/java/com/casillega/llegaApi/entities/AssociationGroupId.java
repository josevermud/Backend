package com.casillega.llegaApi.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
public class AssociationGroupId implements Serializable {

    private int followingUser;
    private int followedUser;

    public AssociationGroupId() {}

    public AssociationGroupId(int followingUser, int followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationGroupId that = (AssociationGroupId) o;
        return followingUser == that.followingUser && followedUser == that.followedUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(followingUser, followedUser);
    }
    // Override equals and hashCode
}
