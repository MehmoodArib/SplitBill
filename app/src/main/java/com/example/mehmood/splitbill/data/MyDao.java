package com.example.mehmood.splitbill.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MyDao {
    @Insert
    void addEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Query("Delete From Event")
    void deleteAllEvents();

    @Query("select * from Event")
    LiveData<List<Event>> getEvents();

    @Query("select * from Event where eventId = :eventID")
    Event getEvent(Integer eventID);

    @Insert
    void addExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("select * from Expense")
    LiveData<List<Expense>> getExpenses();

    @Query("Delete From Expense")
    void deleteAllExpense();

    @Query("select * from Expense where expenseEventId = :eventId")
    List<Expense> getExpenseOfEvent(Integer eventId);

}
