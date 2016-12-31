package com.ranrunner.bestby;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// method that will provide data to collection
public class ItemViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    // variables for context and app widget ID
    private Context mContext = null;
    private int mAppWidgetID;

    // RemoteViews object
    RemoteViews rv;

    // database objects and list cursor
    DatabaseHandler handler;
    SQLiteDatabase db;
    Cursor cursor;

    // list to hold data from database
    private List<Item> itemList = new ArrayList<>();

    // object to hold single item from database
    private Item item;

    public ItemViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() { }

    public void onDestroy() { }

    public int getCount() {
        db = handler.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + handler.getTableItems(), null);
        int count = cursor.getCount();

        return count;
    }

    // set data in list for each position
    public RemoteViews getViewAt(int position) {
        rv.setTextViewText(R.id.item_inList, itemList.get(position).getItem());
        rv.setTextViewText(R.id.date_inList, itemList.get(position).getDate());
        rv.setTextViewText(R.id.id_inList, String.valueOf(itemList.get(position).getID()));

        Intent intent = new Intent();
        Bundle extras = new Bundle();

        extras.putString("ITEM_EXTRA", itemList.get(position).getItem());
        extras.putString("DATE_EXTRA", itemList.get(position).getDate());
        extras.putString("ID_EXTRA", String.valueOf(itemList.get(position).getID()));
        intent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.item_inList, intent);

        return rv;
    }

    // do not return a default loading view
    public RemoteViews getLoadingView() { return null; }

    // set number of views that will be returned
    public int getViewTypeCount() { return 1; }

    public long getItemId (int position) { return position; }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {

        // initialize handler and rv objects
        handler = new DatabaseHandler(mContext);
        rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item);

        // database query
        db = handler.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + handler.getTableItems() + " ORDER BY date", null);

        // add data from cursor to list
        cursor.moveToFirst();
        do {

            // convert milliseconds to date format
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(cursor.getString(cursor.getColumnIndex(handler.getKeyDate()))));

            // set values
            String itemColumn = cursor.getString(cursor.getColumnIndex(handler.getKeyItem()));
            String dateColumn = formatter.format(calendar.getTime());
            int idColumn = cursor.getInt(cursor.getColumnIndex(handler.getKeyId()));

            // make item object empty
            item = new Item();

            // add values to item object
            item.setItem(itemColumn);
            item.setDate(dateColumn);
            item.setID(idColumn);

            // add item to itemList
            itemList.add(item);
        } while (cursor.moveToNext());
    }
}