package com.example.minga.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.minga.bakingapp.services.BakingAppService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final int LOADER_RECIPE = 2;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String dbTitle, String dbIngredientsStr) {

        //CharSequence widgetText = context.getString (R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews (context.getPackageName (), R.layout.baking_app_widget);
        views.setTextViewText (R.id.appwidget_title_tv, dbTitle);
        views.setTextViewText (R.id.appwidget_ingredients_tv, dbIngredientsStr);

        // add click Handler to only launch the app using pending intent
        Intent intent=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity (context, 0, intent, 0);
        views.setOnClickPendingIntent (R.id.widget_button, pendingIntent);

        // add click Handler to draw DB data when
        Intent dbIntent = new Intent (context, BakingAppService.class);
        dbIntent.setAction (BakingAppService.ACTION_QUERY_DATA_WIDGETS);
        PendingIntent dbPendingIntent = PendingIntent.getService (context,0,dbIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_title_tv, dbPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget (appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //for (int appWidgetId : appWidgetIds) {
        //    updateAppWidget (context, appWidgetManager, appWidgetId);
        //}

        //Start the intent service update widget action, the service takes care of updating the widgets UI
        BakingAppService.startActionQueryDataWidgets(context);
    }


    public static void updateBakingAppWidgets(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds, String dbTitle, String dbIngredientsStr) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, dbTitle, dbIngredientsStr );
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

