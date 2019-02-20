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

    @Insert
    void addExpense(Expense expense);

    @Query("select * from Event")
    List<Event> getEvents();

    @Query("select * from Expense where expenseEventId = :eventId")
    List<Expense> getExpenses(String eventId);
    @Delete
    void deleteEvent(Event event);


    @Query("select * from Event where eventId = :eventID")
    Event getEvent(String eventID);

}
