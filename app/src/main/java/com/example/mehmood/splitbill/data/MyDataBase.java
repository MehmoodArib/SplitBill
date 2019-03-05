package com.example.mehmood.splitbill.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Event.class,Expense.class},version = 1)
@TypeConverters({Converter.class})
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao myDao();

    private static MyDataBase instance;
    private static Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class, "note_database")
                    .addCallback(roomCallBack)//here We add this call back to populate the database on creation.
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    //    This CallBack is used to Populate the Db on first creation with 3 Note Items
   //            you can update them delete them in the Below populateDbAsyncTask.
    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyDao myDao;
        private populateDbAsyncTask(MyDataBase db) {
            myDao = db.myDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Contact contact1 = new Contact("Arib", "991169753");
            Contact contact2 = new Contact("Amir", "991169753");
            ArrayList<Contact> participants = new ArrayList<>();
            participants.add(contact1);
            participants.add(contact2);
            Event event1 = new Event("Shimla Trip", "Official", "INR", 0.0, participants);
            Event event2 = new Event("Mumbai Trip", "Meeting", "INR", 250.0, participants);
            Event event3 = new Event("Delhi Trip", "rr", "EURO", 50.0, participants);
            Event event4 = new Event("London", "hh", "DOLLAR", 40.0, participants);
            Event event5 = new Event("Taj Mahal", "Agra", "INR", 56.5, participants);
            myDao.addEvent(event1);
            myDao.addEvent(event2);
            myDao.addEvent(event3);
            myDao.addEvent(event4);
            myDao.addEvent(event5);
            return null;
        }
    }
}
