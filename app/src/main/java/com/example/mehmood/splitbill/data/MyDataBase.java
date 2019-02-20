package com.example.mehmood.splitbill.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Event.class,Expense.class},version = 1)
@TypeConverters({Converter.class})
public abstract class MyDataBase extends RoomDatabase {
    public abstract MyDao myDao();
}
