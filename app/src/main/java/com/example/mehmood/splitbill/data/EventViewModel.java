package com.example.mehmood.splitbill.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EventViewModel extends AndroidViewModel {

    private MyRepository myRepository;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Expense>> allExpense;
    private MutableLiveData<List<Expense>> expenseListOfEvent;
    private MutableLiveData<Event> event;

    public EventViewModel(@NonNull Application application) {
        super(application);
        myRepository = new MyRepository(application);
        allEvents = myRepository.getAllEvents();
        allExpense = myRepository.getAllExpenses();
        expenseListOfEvent = myRepository.getExpenseListOfEvent();
        event = myRepository.getEvent();
    }

    public void addEvent(Event event) {
        myRepository.addEvent(event);
    }

    public void updateEvent(Event event) {
        myRepository.updateEvent(event);
    }

    public void deleteEvent(Event event) {
        myRepository.deleteEvent(event);
    }

    public void deleteAllEvent() {
        myRepository.deleteAllEvent();
    }

    public void addExpense(Expense expense) {
        myRepository.addExpense(expense);
    }

    public void updateExpense(Expense expense) {
        myRepository.updateExpense(expense);
    }

    public void deleteExpense(Expense expense) {
        myRepository.deleteExpense(expense);
    }

    public void deleteAllExpense() {
        myRepository.deleteAllExpense();
    }

    public void getExpenseOfEvent(Integer eventId) {
        myRepository.getExpenseOfEvent(eventId);
    }

    public void getEvent(Integer eventId) {
        myRepository.getEvent(eventId);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Expense>> getAllExpense() {
        return allExpense;
    }

    public MutableLiveData<List<Expense>> getExpenseListOfEvent() {
        return expenseListOfEvent;
    }

    public MutableLiveData<Event> getEvent() {
        return event;
    }
}
