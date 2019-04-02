package com.example.mehmood.splitbill.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DetailedActivityViewModel extends AndroidViewModel {
    private Integer eventId;
    private MyRepository myRepository;
    public DetailedActivityViewModel(@NonNull Application application, Integer eventId) {
        super(application);
        myRepository = new MyRepository(application, eventId);
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
    public LiveData<List<Expense>> getExpenseListOfEvent() {
        return myRepository.getExpenseOfEvent();
    }
    public void updateEvent(Event event) {
        myRepository.updateEvent(event);
    }
    public LiveData<Event> getEvent() {
        return myRepository.getEvent();
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
