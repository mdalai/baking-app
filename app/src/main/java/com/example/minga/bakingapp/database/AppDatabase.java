package com.example.minga.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.minga.bakingapp.adapters.IngredientsAdapter;
import com.example.minga.bakingapp.models.Recipe;

/**
 * Created by minga on 7/19/2018.
 */
@Database(entities ={Recipe.class},version = 2, exportSchema = false)
@TypeConverters ({IngredientsConverter.class, StepsConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName ();
    private static final Object LOCK = new Object (); //make sure an object instantiates only once
    private static final String DATABASE_NAME = "recipes_db";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d (LOG_TAG, "Creating new database instance");
                sInstance= Room.databaseBuilder (context.getApplicationContext (),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        // Queries should be done in a separate thread to avoid locking the UI
                        // this is only TEMPORALLY to see our DB implementation
                        .allowMainThreadQueries ()
                        .build ();
            }
        }
        Log.d (LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
}
