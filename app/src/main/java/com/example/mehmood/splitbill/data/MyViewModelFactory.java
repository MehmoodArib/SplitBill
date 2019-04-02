package com.example.mehmood.splitbill.data;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private Integer eventId;

    public MyViewModelFactory(Application application, Integer eventId) {
        mApplication = application;
        this.eventId = eventId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailedActivityViewModel(mApplication, eventId);
    }
}
