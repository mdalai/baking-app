package com.example.minga.bakingapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minga.bakingapp.fragments.IngredientListFragment;
import com.example.minga.bakingapp.fragments.StepsListFragment;
import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;
import com.example.minga.bakingapp.models.Step;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe_detail);

        Intent intent = getIntent ();
        if(intent == null){
            finish ();
            Toast.makeText (this, "The Recipe Detail is not available", Toast.LENGTH_SHORT).show ();
            return;
        }

        // get the pass data
        Bundle data = intent.getExtras ();
        final Recipe recipe = (Recipe ) data.getParcelable ("recipe");

        TextView recipeNameTv = (TextView )findViewById (R.id.recipe_detail_name_tv);
        recipeNameTv.setText (recipe.getName ());

        ArrayList<Ingredient> ingredients = recipe.getIngredients ();
        Bundle argsIng = new Bundle ();
        argsIng.putParcelableArrayList ("INGREDIENTS",ingredients);
        IngredientListFragment ingredientFrag= new IngredientListFragment ();
        ingredientFrag.setArguments (argsIng);
        FragmentTransaction transaction = getFragmentManager().beginTransaction ();
        transaction.replace (R.id.ingredients_fragment_container, ingredientFrag);
        transaction.commit ();

        ArrayList<Step> steps = recipe.getSteps ();
        Bundle argsSteps = new Bundle ();
        argsSteps.putParcelableArrayList ("STEPS",steps);
        StepsListFragment stepsFrag= new StepsListFragment ();
        stepsFrag.setArguments (argsSteps);
        FragmentTransaction transactionSteps = getFragmentManager().beginTransaction ();
        transactionSteps.replace (R.id.steps_fragment_container, stepsFrag);
        transactionSteps.commit ();


    }


}
