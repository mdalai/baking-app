package com.example.minga.bakingapp.interfaces;

import com.example.minga.bakingapp.adapters.RecipesAdapter;

/**
 * Created by minga on 7/13/2018.
 */

public interface ItemClickListener {
    void onItemClicked(RecipesAdapter.ViewHolder vh, Object item, int pos);
}
