package com.example.minga.bakingapp.utils;

import com.example.minga.bakingapp.models.Ingredient;
import com.example.minga.bakingapp.models.Recipe;
import com.example.minga.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by minga on 7/12/2018.
 */

public class JsonUtils {

    public static ArrayList<Recipe> parseJsonToArrayList(String json) throws JSONException{
        ArrayList<Recipe> recipes = new ArrayList<Recipe> ();

        JSONArray jsonArray =  new JSONArray (json);
        for(int i=0; i < jsonArray.length (); i++){
            JSONObject r = jsonArray.getJSONObject (i);
            Integer id = r.getInt ("id");
            String name = r.getString ("name");
            Integer servings = r.getInt ("servings");
            String image = r.getString ("image");

            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient> ();
            JSONArray ingArr = r.getJSONArray ("ingredients");
            for(int j=0; j<ingArr.length ();j++){
                JSONObject r2 = ingArr.getJSONObject (j);
                double quantity = r2.getDouble ("quantity");
                String measure = r2.getString ("measure");
                String ingredient = r2.getString ("ingredient");
                ingredients.add (new Ingredient (quantity,measure,ingredient));
            }
            ArrayList<Step> steps = new ArrayList<Step> ();
            JSONArray stepsArr = r.getJSONArray ("steps");
            for(int j=0; j<stepsArr.length ();j++){
                JSONObject r3 = stepsArr.getJSONObject (j);
                Integer sid = r3.getInt ("id");
                String shortDescription = r3.getString ("shortDescription");
                String description = r3.getString ("description");
                String videoURL = r3.getString ("videoURL");
                String thumbnailURL = r3.getString ("thumbnailURL");
                steps.add (new Step (sid,shortDescription,description,videoURL,thumbnailURL));
            }

            recipes.add (new Recipe (id,name,servings,image,ingredients,steps));


        }

        return recipes;
    }
}
