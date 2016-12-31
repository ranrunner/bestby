package com.ranrunner.bestby;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

            // configure button to open app from widget
            Intent intent_launchApp = new Intent(context, ListActivity.class);
            PendingIntent pending = PendingIntent.getActivity(context, 0, intent_launchApp, 0);
            rv.setOnClickPendingIntent(R.id.widget_launchapp, pending);
            appWidgetManager.updateAppWidget(appWidgetIds, rv);

            // execute update of instances
            appWidgetManager.updateAppWidget(appWidgetIds[ins], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
