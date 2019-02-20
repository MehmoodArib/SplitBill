package com.example.mehmood.splitbill.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;

public class Converter {
    @TypeConverter
    public static ArrayList<Contact> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Contact> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}

