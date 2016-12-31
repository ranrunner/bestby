package com.ranrunner.bestby;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    // database objects
    DatabaseHandler handler;
    SQLiteDatabase db;

    // ListView (lv), with supporting objects to help populate it
    ListView lv;
    Cursor listCursor;
    ListCursorAdapter listAdapter;

    // TextView (ifEmpty), to display if ListView is empty
    TextView ifEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // populate ListView with items from database
        handler = new DatabaseHandler(this);
        db = handler.getReadableDatabase();
        listCursor = db.rawQuery("SELECT * FROM " + handler.getTableItems() + " ORDER BY date", null);
        lv = (ListView)findViewById(R.id.items_list);
        listAdapter = new ListCursorAdapter(this, listCursor);
        lv.setAdapter(listAdapter);

        // refresh view
        listAdapter.notifyDataSetChanged();

        // set listener for ListView
        // launch intent to remove item (RemoveActivity)
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id_l = (TextView)view.findViewById(R.id.id_inList);
                TextView item_l = (TextView)view.findViewById(R.id.item_inList);
                TextView date_l = (TextView)view.findViewById(R.id.date_inList);

                // pass ID, item, and date to next activity (as extras in intent)
                Intent intent = new Intent(ListActivity.this, RemoveActivity.class);
                intent.putExtra("ID_EXTRA", id_l.getText().toString());
                intent.putExtra("ITEM_EXTRA", item_l.getText().toString());
                intent.putExtra("DATE_EXTRA", date_l.getText().toString());
                startActivity(intent);
            }
        });

        // let user know if ListView is empty
        ifEmpty = (TextView)findViewById(R.id.empty_label);
        lv.setEmptyView(ifEmpty);
    }

    public void addItem(View view) {

        // launch intent to add item (AddActivity)
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
