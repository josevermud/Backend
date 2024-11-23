package com.casillega.llegaApi.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
public class InterestUserId implements Serializable {

    private int interest;
    private int user;

    // Default constructor
    public InterestUserId() {}

    public InterestUserId(int interest, int user) {
        this.interest = interest;
        this.user = user;
    }

    // Override equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestUserId that = (InterestUserId) o;
        return interest == that.interest && user == that.user;
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(interest, user);
    }
    // Override equals and hashCode
}
