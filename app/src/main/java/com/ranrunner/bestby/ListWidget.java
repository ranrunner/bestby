package com.ranrunner.bestby;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class ListWidget extends AppWidgetProvider {

    // database objects
    DatabaseHandler handler;
    SQLiteDatabase db;

    // ListView (lv), with supporting objects to help populate it
//    ListView lv;
    Cursor listCursor;
    ListCursorAdapter listAdapter;

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // (for) loop to update all widget instances on launcher
        for (int ins = 0; ins < appWidgetIds.length; ins++) {

            // enable access to widget_list layout
            final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list);

            // intents for each item and open app button
            final Intent intent_item = new Intent(context, RemoveActivity.class);
//            final Intent intent_openApp = new Intent(context, RemoveActivity.class);

            // populate ListView with items from database
            handler = new DatabaseHandler(context);
            db = handler.getWritableDatabase();
            listCursor = db.rawQuery("SELECT * FROM Items ORDER BY date", null);
            rv.setRemoteAdapter(R.id.widget_itemslist, intent_item);
            listAdapter = new ListCursorAdapter(context, listCursor);
//            lv.setAdapter(listAdapter);

            // refresh view
            listAdapter.notifyDataSetChanged();

            // set listener for ListView
            // launch intent to remove item (RemoveActivity)
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    TextView id_l = (TextView)view.findViewById(R.id.id_inList);
//                    TextView item_l = (TextView)view.findViewById(R.id.item_inList);
//                    TextView date_l = (TextView)view.findViewById(R.id.date_inList);
//
//                    // pass ID, item, and date to next activity (as extras in intent)
//                    intent_item.putExtra("ID_EXTRA", id_l.getText().toString());
//                    intent_item.putExtra("ITEM_EXTRA", item_l.getText().toString());
//                    intent_item.putExtra("DATE_EXTRA", date_l.getText().toString());
//                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent_item, 0);
//                    rv.setOnClickPendingIntent(R.id.widget_itemslist, pendingIntent);
//                }
//            });

            // let user know if ListView is empty
            rv.setEmptyView(R.id.widget_itemslist, R.id.widget_emptylabel);

            // execute update of instances
            appWidgetManager.updateAppWidget(appWidgetIds[ins], rv);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
