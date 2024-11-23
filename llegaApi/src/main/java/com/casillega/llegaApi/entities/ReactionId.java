package com.casillega.llegaApi.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
public class ReactionId  implements Serializable {

    private int user;
    private int event;

    public ReactionId() {}

    public ReactionId(int user, int event) {
        this.user = user;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionId that = (ReactionId) o;
        return user == that.user && event == that.event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, event);
    }


}
