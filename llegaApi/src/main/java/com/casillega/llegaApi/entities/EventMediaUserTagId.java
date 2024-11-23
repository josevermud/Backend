package com.casillega.llegaApi.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
public class EventMediaUserTagId implements Serializable {

    private int eventMedia;
    private int user;

    public EventMediaUserTagId() {}

    public EventMediaUserTagId(int eventMedia, int user) {
        this.eventMedia = eventMedia;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventMediaUserTagId that = (EventMediaUserTagId) o;
        return eventMedia == that.eventMedia && user == that.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventMedia, user);
    }

    // Override equals and hashCode
}
