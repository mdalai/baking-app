package com.example.minga.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by minga on 7/14/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Ingredient> ingredients;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{
        //TextView ingredientQuntityTv;
        //TextView ingredientMesureTv;
        @BindView (R.id.ingredient_tv) TextView ingredientTv;

        public ViewHolder(View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
            //ingredientQuntityTv = (TextView)itemView.findViewById (R.id.ingredient_quantity_tv);
            //ingredientMesureTv =(TextView)itemView.findViewById (R.id.ingredient_measure_tv);
            //ingredientTv =(TextView)itemView.findViewById (R.id.ingredient_tv);

        }
    }

    // constructor
    public IngredientsAdapter(Context c, ArrayList<Ingredient> ingredients){
        this.context = c;
        this.ingredients = ingredients;

    }


    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);
        View view = inflater.inflate (R.layout.ingredients_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {
        final Ingredient ingredient = this.ingredients.get(position);

        //holder.ingredientQuntityTv.setText (String.valueOf (ingredient.getQuantity ()));
        //holder.ingredientMesureTv.setText (String.valueOf (ingredient.getMeasure ()));
        holder.ingredientTv.setText ( ingredient.getIngredient () + ": "
                        + String.valueOf (ingredient.getQuantity ()) + " "
                        + String.valueOf (ingredient.getMeasure ())         );

    }

    @Override
    public int getItemCount() {
        return this.ingredients.size ();
    }


}
