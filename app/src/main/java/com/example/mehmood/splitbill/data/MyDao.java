package com.example.mehmood.splitbill.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MyDao {
    @Insert
    void addEvent(Event event);

    @Query("select * from Event")
    List<Event> getEvents();

    @Delete
    void deleteEvent(Event event);


    @Query("select * from Event where eventId = :eventID")
    Event getEvent(String eventID);

}
