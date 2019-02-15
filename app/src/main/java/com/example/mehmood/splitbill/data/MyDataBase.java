package com.example.mehmood.splitbill.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Event.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao myDao();
}
