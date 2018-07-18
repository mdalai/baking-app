package com.example.minga.bakingapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.minga.bakingapp.MainActivity;
import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.adapters.IngredientsAdapter;
import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;

import java.util.ArrayList;

/**
 * Created by minga on 7/14/2018.
 */

public class IngredientListFragment extends Fragment {
    ArrayList<Ingredient> ingredients;
    IngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            ingredients = this.getArguments ().getParcelableArrayList ("INGREDIENTS");
        }

        View view = inflater.inflate (R.layout.ingredients_list, parent, false);

        // RecyclerView setup
        RecyclerView rvIngredients = (RecyclerView )view.findViewById (R.id.ingredient_list_rv);
        // use this setting to improve performance if you know that changes
        rvIngredients.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager (getActivity ());
        rvIngredients.setLayoutManager (layoutManager);
        //recipes = new ArrayList<Recipe> ();
        ingredientsAdapter = new IngredientsAdapter (this.getActivity (), ingredients);
        rvIngredients.setAdapter (ingredientsAdapter);
        rvIngredients.setItemAnimator(new DefaultItemAnimator ());

        return view;
    }



}
