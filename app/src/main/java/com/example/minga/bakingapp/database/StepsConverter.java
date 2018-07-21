package com.example.minga.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.example.minga.bakingapp.models.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by minga on 7/19/2018.
 */

public class StepsConverter {
    @TypeConverter
    public static ArrayList<Step> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Step>> () {}.getType();
        return new Gson ().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Step> steps) {
        Gson gson = new Gson();
        String json = gson.toJson(steps);
        return json;
    }
}
