package com.example.minga.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.minga.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minga on 7/19/2018.
 */
@Dao
public interface RecipeDao {

    @Query ("SELECT * FROM recipes LIMIT 1")
    Recipe getAnyRecipe();

    @Query ("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query ("SELECT * FROM recipes")
    LiveData<List<Recipe>> loadAllRecipes();

    @Query ("SELECT * FROM recipes WHERE id =:rid")
    LiveData<Recipe> findByRecipeID(Integer rid);

    @Insert
    void insertRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    /**
     * Select all cheeses.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("SELECT * FROM recipes" )
    Cursor selectAll();

    /**
     * Select a cheese by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected cheese.
     */
    @Query("SELECT * FROM recipes WHERE id = :id")
    Cursor selectById(Integer id);

}
