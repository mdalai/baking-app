package com.example.minga.bakingapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.RecipeDetailActivity;
import com.example.minga.bakingapp.interfaces.ItemClickListener;
import com.example.minga.bakingapp.adapters.RecipesAdapter;
import com.example.minga.bakingapp.models.Recipe;
import com.example.minga.bakingapp.utils.JsonUtils;
import com.example.minga.bakingapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by minga on 7/12/2018.
 */

public class RecipeListFragment extends Fragment {
    ArrayList<Recipe> recipes;
    RecipesAdapter recipesAdapter;

    // constructor
    public RecipeListFragment(){ }

    // Inflates the RecyclerView
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.recipes_list, parent, false);

        // load and parse Json with AsyncTask
        URL apiURL = NetworkUtils.buildApiUrl ();
        new LoadJsonAsyncTask().execute(apiURL);

        // create interface instance
        ItemClickListener listener = new ItemClickListener() {
            @Override
            public void onItemClicked(RecipesAdapter.ViewHolder vh, Object item, int pos) {
                //Toast.makeText(getActivity(), "Item clicked: " + pos, Toast.LENGTH_SHORT).show();
                //  child activity class
                Class destinationActivity = RecipeDetailActivity.class;
                Intent intent = new Intent(getActivity (), destinationActivity);
                // passing data into child activity.
                intent.putExtra ("recipe", ( Parcelable ) item);
                getActivity ().startActivity (intent);
            }
        };

        // RecyclerView setup
        RecyclerView rvRecipes = (RecyclerView )view.findViewById (R.id.recipe_cards_rv);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvRecipes.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager (getActivity ());
        layoutManager.setOrientation (LinearLayoutManager.VERTICAL);
        rvRecipes.setLayoutManager (layoutManager);
        recipes = new ArrayList<Recipe> ();
        recipesAdapter = new RecipesAdapter (getContext (), recipes, listener);
        rvRecipes.setAdapter (recipesAdapter);
        rvRecipes.setItemAnimator(new DefaultItemAnimator ());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }

    // AsyncTask
    public class LoadJsonAsyncTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute ();
            Toast.makeText(getActivity (),"LOADING JSON",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(URL... urls) {
            URL url = urls[0];
            String json = null;
            try {
                json = NetworkUtils.getResponseFromHttpUrl (url);
                recipes = JsonUtils.parseJsonToArrayList (json);
                //Log.d ("PRINT---", String.valueOf (recipes));
            } catch (IOException e){
                e.printStackTrace ();
            } catch (JSONException e){
                e.printStackTrace ();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute (result);
            if(recipes != null){
                recipesAdapter.updateRvAdapter(recipes);
            }
        }
    }



}
