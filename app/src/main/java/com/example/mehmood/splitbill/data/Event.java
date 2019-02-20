package com.example.mehmood.splitbill.data;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    private String eventName;
    private String eventDesc;
    private String currency;
    private String totalAmount;
    @PrimaryKey
    @NonNull
    private String eventId;
    private ArrayList<Contact> participantsList;



    public Event(String eventName, String eventDesc, String eventId, String currency, String totalAmount, ArrayList<Contact> participantsList) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.eventId = eventId;
        this.currency = currency;
        this.participantsList = participantsList;
        this.totalAmount = totalAmount;
    }
    @Ignore
    public Event(){

    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public ArrayList<Contact> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(ArrayList<Contact> participantsList) {
        this.participantsList = participantsList;
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

