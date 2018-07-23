package com.example.minga.bakingapp.services;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.minga.bakingapp.R;
import com.example.minga.bakingapp.models.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.minga.bakingapp.provider.MyContentProvider.URI_RECIPES;

/**
 * Created by minga on 7/22/2018.
 */

public class ListviewAdapter implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Ingredient> listItemList = new ArrayList<Ingredient>();
    private Context context = null;
    private int appWidgetId;
    private String ingredientStr = "";

    public ListviewAdapter(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        // populate item
        populateListItem();

    }


    private void populateListItem() {
        Uri RECIPE_URI = URI_RECIPES;
        Cursor cursor = context.getContentResolver ().query (RECIPE_URI,null,null,null,"id");
        String ingredients = "";

        if (cursor!=null && cursor.getCount () >0){
            cursor.moveToFirst ();
            int ingredientsIdx = cursor.getColumnIndex ("ingredients");
            ingredients = cursor.getString (ingredientsIdx);
            Type listType = new TypeToken<ArrayList<Ingredient>> () {}.getType();
            listItemList = new Gson ().fromJson(ingredients, listType);
            cursor.close ();
        }
    }

    @Override
    public int getCount() {
        return listItemList.size ();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_listview_item);
        Ingredient ingredient = listItemList.get(position);
        ingredientStr = ingredient.getIngredient () + " (" + ingredient.getQuantity () +" "+ingredient.getMeasure () + ")";
        remoteView.setTextViewText(R.id.appwidget_listitem_tv, ingredientStr);
        return remoteView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

}
