package com.example.minga.bakingapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minga.bakingapp.fragments.IngredientListFragment;
import com.example.minga.bakingapp.fragments.StepsListFragment;
import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;
import com.example.minga.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {
    static final String STATE_RECIPE = "recipe";
    static final String BUNDLE_INGREDIENTS = "INGREDIENTS";
    static final String BUNDLE_STEPS = "STEPS";
    static final String BUNDLE_STEPS_TWO_PANE = "TWO_PANE";
    private Recipe recipe ;
    @BindView (R.id.recipe_detail_name_tv) TextView recipeNameTv;

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe_detail);
        ButterKnife.bind (this);

        if(findViewById (R.id.step_detail_linear_layout)!=null){
            mTwoPane = true;

            Intent intent = getIntent ();
            if (intent == null) {
                finish ();
                Toast.makeText (this, "The Recipe Detail is not available", Toast.LENGTH_SHORT).show ();
                return;
            }

            // get the pass data
            Bundle data = intent.getExtras ();
            if (data != null ) {
                recipe = ( Recipe ) data.getParcelable (STATE_RECIPE);
                TextView recipeNameTv = ( TextView ) findViewById (R.id.recipe_detail_name_tv);
                recipeNameTv.setText (recipe.getName ());
                initializeIngredientsFragment (recipe);
                initializeStepsFragment (recipe, mTwoPane);
            }


        } else {
            mTwoPane = false;

            if(savedInstanceState == null) {
                Intent intent = getIntent ();
                if (intent == null) {
                    finish ();
                    Toast.makeText (this, "The Recipe Detail is not available", Toast.LENGTH_SHORT).show ();
                    return;
                }
                // get the pass data
                Bundle data = intent.getExtras ();
                if (data != null ) {
                    recipe = ( Recipe ) data.getParcelable (STATE_RECIPE);
                    TextView recipeNameTv = ( TextView ) findViewById (R.id.recipe_detail_name_tv);
                    recipeNameTv.setText (recipe.getName ());
                    initializeIngredientsFragment (recipe);
                    initializeStepsFragment (recipe, mTwoPane);
                }


            } else {
                //recipe = savedInstanceState.getParcelable (STATE_RECIPE);
                //recipeNameTv.setText (recipe.getName ());
                //initializeIngredientsFragment(recipe);
                //initializeStepsFragment(recipe);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable (STATE_RECIPE, recipe);
        super.onSaveInstanceState (savedInstanceState);
    }

    public void initializeIngredientsFragment(Recipe recipe){
        ArrayList<Ingredient> ingredients = recipe.getIngredients ();
        Bundle argsIng = new Bundle ();
        argsIng.putParcelableArrayList (BUNDLE_INGREDIENTS, ingredients);
        IngredientListFragment ingredientFrag = new IngredientListFragment ();
        ingredientFrag.setArguments (argsIng);
        FragmentTransaction transaction = getFragmentManager ().beginTransaction ();
        transaction.replace (R.id.ingredients_fragment_container, ingredientFrag);
        transaction.commit ();
    }

    public void initializeStepsFragment(Recipe recipe, boolean nTwoPane){
        ArrayList<Step> steps = recipe.getSteps ();
        Bundle argsSteps = new Bundle ();
        argsSteps.putParcelableArrayList (BUNDLE_STEPS, steps);
        argsSteps.putBoolean (BUNDLE_STEPS_TWO_PANE, nTwoPane);
        StepsListFragment stepsFrag = new StepsListFragment ();
        stepsFrag.setArguments (argsSteps);
        FragmentTransaction transactionSteps = getFragmentManager ().beginTransaction ();
        transactionSteps.replace (R.id.steps_fragment_container, stepsFrag);
        transactionSteps.commit ();
    }


}
