package com.example.mehmood.splitbill.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    private String eventName;
    private String eventDesc;
    private String currency;
    @PrimaryKey
    @NonNull
    private String eventId;

    public Event(String eventName, String eventDesc, String eventId, String currency) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.eventId = eventId;
        this.currency = currency;
    }
    @Ignore
    public Event(){

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

