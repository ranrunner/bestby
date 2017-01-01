package com.ranrunner.bestby;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

public class ListWidget extends AppWidgetProvider {

    @SuppressWarnings("deprecation")
    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // enable access to widget_list layout
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list);

        // (for) loop to update all widget instances on launcher
        for (int ins = 0; ins < appWidgetIds.length; ins++) {

            // intent for each item (with extra holding app widget ID)
            Intent intent_toService = new Intent(context, WidgetService.class);
            intent_toService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[ins]);
            intent_toService.setData(Uri.parse(intent_toService.toUri(Intent.URI_INTENT_SCHEME)));

            // set rv object to use RemoteViews adapter
            // adapter connects to RemoteViewsService via specified intent
            // data is populated this way
            rv.setRemoteAdapter(appWidgetIds[ins], R.id.widget_itemslist, intent_toService);

            // let user know if items list is empty
            rv.setEmptyView(R.id.widget_itemslist, R.id.widget_emptylabel);

            // make each list item clickable
            Intent intent_item = new Intent(context, RemoveActivity.class);
            PendingIntent pending_item = PendingIntent.getActivity(context, 0, intent_item, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_itemslist, pending_item);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[ins], R.id.widget_itemslist);

            // configure button to open app from widget
            Intent intent_launchApp = new Intent(context, ListActivity.class);
            PendingIntent pending_launchApp = PendingIntent.getActivity(context, 0, intent_launchApp, 0);
            rv.setOnClickPendingIntent(R.id.widget_launchapp, pending_launchApp);
            appWidgetManager.updateAppWidget(appWidgetIds, rv);

            // configure remove button to sync ListView on widget with database
            Intent intent_listSync = new Intent(context, ListWidget.class);
            intent_listSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent_listSync.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[ins]);
            PendingIntent pending_listSync = PendingIntent.getBroadcast(context, 0, intent_listSync, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.remove_button, pending_listSync);
            appWidgetManager.updateAppWidget(appWidgetIds, rv);

            // execute update of instances
            appWidgetManager.updateAppWidget(appWidgetIds[ins], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    // line 54 calls this method
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        Bundle extras  = intent.getExtras();

        if (extras != null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName appWidget = new ComponentName(context.getPackageName(), ListWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(appWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
