package com.example.minga.bakingapp.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.minga.bakingapp.BakingAppWidget;
import com.example.minga.bakingapp.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.minga.bakingapp.provider.MyContentProvider.URI_RECIPES;

/**
 * Created by minga on 7/21/2018.
 */

public class BakingAppService extends IntentService {
    public static final String ACTION_QUERY_DATA_WIDGETS = "com.example.minga.bakingapp.action.query_data_widgets";

    public BakingAppService(){
        super("BakingAppService");
    }

    // Start the service
    public static void startActionQueryDataWidgets(Context context){
        Intent intent = new Intent (context, BakingAppService.class);
        intent.setAction (ACTION_QUERY_DATA_WIDGETS);
        context.startService (intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction ();
            if (ACTION_QUERY_DATA_WIDGETS.equals (action)){
                handleActionQueryDataWidgets();
            }
        }
    }

    private void handleActionQueryDataWidgets(){
        Uri RECIPE_URI = URI_RECIPES;
        Cursor cursor = getContentResolver ().query (RECIPE_URI,null,null,null,"id");
        String recipeName = "";
        if (cursor!=null && cursor.getCount () >0){
            cursor.moveToFirst ();
            int recipeNameIdx = cursor.getColumnIndex ("name");
            recipeName = cursor.getString (recipeNameIdx);
            cursor.close ();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance (this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds (new ComponentName (this, BakingAppWidget.class));
        BakingAppWidget.updateBakingAppWidgets (this, appWidgetManager, appWidgetIds, recipeName);

    }
}
