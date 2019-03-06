package com.example.mehmood.splitbill.data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MyRepository {
    private MyDao myDao;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Expense>> allExpenses;
    private MutableLiveData<List<Expense>> expenseListOfEvent = new MutableLiveData<>();
    private MutableLiveData<Event> event = new MutableLiveData<>();

    public MyRepository(Application application) {
        MyDataBase dataBase = MyDataBase.getInstance(application);
        myDao = dataBase.myDao();
        allEvents = myDao.getEvents();
        allExpenses = myDao.getExpenses();

    }

    private void asyncFinished(List<Expense> expenses) {
        expenseListOfEvent.setValue(expenses);
    }
    private void asyncFinished(Event event) {
        this.event.setValue(event);
    }


    public void addEvent(Event event) {
        new AddEventAsyncTask(myDao).execute(event);
    }

    public void updateEvent(Event event) {
        new UpdateEventAsyncTask(myDao).execute(event);
    }

    public void deleteEvent(Event event) {
        new DeleteEventAsyncTask(myDao).execute(event);
    }

    public void deleteAllEvent() {
        new DeleteAllEventAsyncTask(myDao).execute();
    }

    public void addExpense(Expense expense) {
        new AddExpenseAsyncTask(myDao).execute(expense);
    }

    public void updateExpense(Expense expense) {
        new UpdateExpenseAsyncTask(myDao).execute(expense);
    }

    public void deleteExpense(Expense expense) {
        new DeleteExpenseAsyncTask(myDao).execute(expense);
    }

    public void deleteAllExpense() {
        new DeleteAllExpenseAsyncTask(myDao).execute();
    }

    public void getExpenseOfEvent(Integer eventId) {
        GetExpenseOfEventAsyncTask task = new GetExpenseOfEventAsyncTask(myDao);
        task.delegate = this;
        task.execute(eventId);
    }
    public void getEvent(Integer eventId) {
        GetEventAsyncTask task = new GetEventAsyncTask(myDao);
        task.delegate = this;
        task.execute(eventId);
    }
    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    public MutableLiveData<List<Expense>> getExpenseListOfEvent() {
        return expenseListOfEvent;
    }

    public MutableLiveData<Event> getEvent() {
        return event;
    }


    private static class GetExpenseOfEventAsyncTask extends AsyncTask<Integer, Void, List<Expense>> {

        private MyDao myDao;
        private MyRepository delegate = null;

        GetExpenseOfEventAsyncTask(MyDao dao) {
            myDao = dao;
        }

        @Override
        protected List<Expense> doInBackground(final Integer... eventId) {
            return myDao.getExpenseOfEvent(eventId[0]);
        }

        @Override
        protected void onPostExecute(List<Expense> expenses) {
            delegate.asyncFinished(expenses);
        }
    }
    private static class GetEventAsyncTask extends AsyncTask<Integer, Void, Event> {

        private MyDao myDao;
        private MyRepository delegate = null;

        GetEventAsyncTask(MyDao dao) {
            myDao = dao;
        }

        @Override
        protected Event doInBackground(final Integer... eventId) {
            return myDao.getEvent(eventId[0]);
        }

        @Override
        protected void onPostExecute(Event event) {
            delegate.asyncFinished(event);
        }
    }

    private static class AddEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private MyDao myDao;

        private AddEventAsyncTask(MyDao myDao) {
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            myDao.addEvent(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private MyDao myDao;

        private UpdateEventAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Event... events) {
            myDao.updateEvent(events[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private MyDao myDao;

        private DeleteEventAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Event... events) {
            myDao.deleteEvent(events[0]);
            return null;
        }
    }

    private static class DeleteAllEventAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao myDao;

        private DeleteAllEventAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Void... events) {
            myDao.deleteAllEvents();
            return null;
        }
    }

    private static class AddExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private MyDao myDao;

        private AddExpenseAsyncTask(MyDao myDao) {
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            myDao.addExpense(expenses[0]);
            return null;
        }
    }

    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private MyDao myDao;

        private UpdateExpenseAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            myDao.updateExpense(expenses[0]);
            return null;
        }
    }

    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private MyDao myDao;

        private DeleteExpenseAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            myDao.deleteExpense(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpenseAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao myDao;

        private DeleteAllExpenseAsyncTask(MyDao myDao) {
            this.myDao = myDao;

        }

        @Override
        protected Void doInBackground(Void... events) {
            myDao.deleteAllExpense();
            return null;
        }
    }


}