package com.example.mehmood.splitbill.data;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    private String eventName;
    private String eventDesc;
    private String currency;
    private Double totalAmount;
    private ArrayList<Contact> participantsList;
    @PrimaryKey(autoGenerate = true)
    private Integer eventId;

    public Event(String eventName, String eventDesc, String currency, Double totalAmount, ArrayList<Contact> participantsList) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.currency = currency;
        this.participantsList = participantsList;
        this.totalAmount = totalAmount;
    }

    public Integer getEventId() {
        return eventId;
    }
    @Ignore
    public Event(){

    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString(){
        return this.getEventName()+" "+this.getEventDesc();
    }
}

