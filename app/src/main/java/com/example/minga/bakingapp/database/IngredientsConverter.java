package com.example.minga.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.example.minga.bakingapp.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by minga on 7/19/2018.
 */

public class IngredientsConverter {
    @TypeConverter
    public static ArrayList<Ingredient> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Ingredient>> () {}.getType();
        return new Gson ().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Ingredient> ingredients) {
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        return json;
    }

}
