package com.ranrunner.bestby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveActivity extends AppCompatActivity {

    // database handler object
    DatabaseHandler handler;

    // TextView objects (to display item info)
    TextView itemText;
    TextView idText;
    TextView dateText;

    // boolean to determine if user loaded RemoveActivity from widget
    Boolean isFromWidget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        // initialize database handler object
        handler = new DatabaseHandler(this);

        // set references to objects
        itemText = (TextView)findViewById(R.id.itemName_value);
        idText = (TextView)findViewById(R.id.id_value);
        dateText = (TextView)findViewById(R.id.date_value);

        // set TextView objects on activity
        itemText.setText(getIntent().getStringExtra("ITEM_EXTRA"));
        idText.setText(getIntent().getStringExtra("ID_EXTRA"));
        dateText.setText(getIntent().getStringExtra("DATE_EXTRA"));

        // set isFromWidget if necessary
        isFromWidget = Boolean.valueOf(getIntent().getStringExtra("FROM_WIDGET"));
    }

    public void remove(View view) {
        Item item = new Item();
        item.setID(Integer.parseInt(idText.getText().toString()));
        handler.removeItem(item);

        // display toast that item has been removed from list
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();

        // return to previous activity (ListActivity)
        // if user loaded this activity from app
        if (isFromWidget == false) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }

        // return to widget (ListWidget)
        // if user loaded this activity from widget
        else {

            // equivalent of pressing back button on bottom navigation
            super.onBackPressed();
        }
    }
}
