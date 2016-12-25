package com.ranrunner.bestby;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListCursorAdapter extends CursorAdapter {

    public ListCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // method newView is used to inflate and return new view
    // no data is bound at this point
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // method bindView binds data to given view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // find fields to populate in inflated template
        TextView id = (TextView)view.findViewById(R.id.id_inList);
        TextView item = (TextView)view.findViewById(R.id.item_inList);
        TextView date = (TextView)view.findViewById(R.id.date_inList);

        // extract properties from cursor
        int item_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        String item_str = cursor.getString(cursor.getColumnIndexOrThrow("item"));
        String date_str = cursor.getString(cursor.getColumnIndexOrThrow("date"));

        // populate fields with extracted properties
        id.setText(String.valueOf(item_id));
        item.setText(item_str);
        date.setText(utcMSToDate(Long.parseLong(date_str), "MM/dd/yyyy"));
    }

    public static String utcMSToDate(long utcMS, String dateFormat) {

        // create object for formatting date in specified format
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // create calendar object that will convert utcMS value to date format
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(utcMS);
        return formatter.format(calendar.getTime());
    }
}
