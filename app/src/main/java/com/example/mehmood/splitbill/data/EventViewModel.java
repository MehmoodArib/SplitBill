package com.example.mehmood.splitbill.data;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EventViewModel extends AndroidViewModel {
    private MyRepository myRepository;
    private LiveData<List<Event>> allEvents;
    public EventViewModel(@NonNull Application application) {
        super(application);
        myRepository = new MyRepository(application);
        allEvents = myRepository.getAllEvents();
    }
    public void addEvent(Event event) {
        myRepository.addEvent(event);
    }
    public void deleteEvent(Event event) {
        myRepository.deleteEvent(event);
    }
    public void deleteAllEvent() {
        myRepository.deleteAllEvent();
    }
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
}
