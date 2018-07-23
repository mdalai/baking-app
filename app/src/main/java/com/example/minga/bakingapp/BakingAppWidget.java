package com.example.minga.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.minga.bakingapp.services.BakingAppService;
import com.example.minga.bakingapp.services.ListviewWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service update widget action, the service takes care of updating the widgets UI
        BakingAppService.startActionQueryDataWidgets(context);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId,  remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    private RemoteViews updateWidgetListView(Context context,int appWidgetId) {
        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.baking_app_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, ListviewWidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.appwidget_ingredients_lv,  svcIntent);
        //setting an empty view in case of no data
        //remoteViews.setEmptyView(R.id.appwidget_ingredients_lv, R.id.empty_view);
        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    static void updateAppWidget2(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String dbTitle) {
        //CharSequence widgetText = context.getString (R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews (context.getPackageName (), R.layout.baking_app_widget);
        views.setTextViewText (R.id.appwidget_title_tv, dbTitle);

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


    public static void updateBakingAppWidgets(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds, String dbTitle) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget2(context, appWidgetManager, appWidgetId, dbTitle);
        }
    }

}

