package com.ranrunner.bestby;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;              // database version, 1
    private static final String DATABASE_NAME = "itemDB";       // database name, itemDB
    private static final String TABLE_ITEMS = "Items";          // table name, Items

    // columns in Items table
    private static final String KEY_ID = "_id";                 // ID column
    private static final String KEY_ITEM = "item";              // item column
    private static final String KEY_DATE = "date";              // date column

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM + " TEXT,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop old table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        // create table
        onCreate(db);
    }

    // add item
    void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();         // open connection

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem());                   // item
        values.put(KEY_DATE, item.getDate());                   // date

        db.insert(TABLE_ITEMS, null, values);                   // insert row
        db.close();                                             // close connection
    }

    // remove item
    void removeItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();         // open connection

        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) }); // delete by ID
        db.close();                                             // close connection
    }
}
