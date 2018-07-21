package com.example.minga.bakingapp.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.fragments.IngredientListFragment;
import com.example.minga.bakingapp.interfaces.ItemClickListener;
import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minga on 7/11/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Recipe> mRecipes;
    ItemClickListener itemClickListener;

    // Create new views (invoked by the layout manager)
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);

        View recipesView = inflater.inflate (R.layout.recipe_card_item, parent, false);

        ViewHolder viewHolder = new ViewHolder (recipesView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecipesAdapter.ViewHolder holder, final int position) {
        final Recipe recipe = mRecipes.get (position);

        holder.recipeNameTv.setText (recipe.getName ());
        holder.recipeIngredientsTv.setText (String.valueOf (recipe.getIngredients ().size ()) + " Ingredients");
        holder.recipeStepsTv.setText (String.valueOf (recipe.getSteps ().size ()) + " Steps");
        holder.recipeServingsTv.setText (String.valueOf (recipe.getServings ()) + " Servings");

        // set click listener
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClicked(holder, recipe, position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mRecipes.size ();
    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView (R.id.recipe_card_item_cv) CardView cv;
        @BindView (R.id.recipe_item_name_tv)TextView recipeNameTv;
        @BindView (R.id.recipe_item_ingredients_tv)TextView recipeIngredientsTv;
        @BindView (R.id.recipe_item_steps_tv)TextView recipeStepsTv;
        @BindView (R.id.recipe_item_servings_tv)TextView recipeServingsTv;
        @BindView (R.id.recipe_item_icon_iv)ImageView recipeIconIv;

        public ViewHolder(View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // constructor
    public RecipesAdapter(Context c, ArrayList<Recipe> recipes, ItemClickListener itemClickListener){
        this.context = c;
        this.mRecipes = recipes;
        this.itemClickListener =itemClickListener;
    }


    public void updateRvAdapter(ArrayList<Recipe> recipes){
        if(mRecipes != null){
            mRecipes.clear ();
            mRecipes.addAll(recipes);
        } else{
            mRecipes = recipes;
        }
        notifyDataSetChanged ();
    }


}
