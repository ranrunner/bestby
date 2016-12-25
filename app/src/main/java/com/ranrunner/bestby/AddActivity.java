package com.ranrunner.bestby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // database handler object
    DatabaseHandler handler;

    // EditText object (user-entered item)
    EditText itemName;

    // date value in UTC milliseconds (to store into database)
    long utcMS = 0;

    // TextView objects (to display date to user only)
    // values of monthText, dayText, and yearText will not be stored into database
    TextView monthText;
    TextView dayText;
    TextView yearText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // initialize database handler object
        handler = new DatabaseHandler(this);

        // set references to objects
        itemName = (EditText)findViewById(R.id.itemName_field);
        monthText = (TextView)findViewById(R.id.month_value);
        dayText = (TextView)findViewById(R.id.day_value);
        yearText = (TextView)findViewById(R.id.year_value);
    }

    public void selectDate(View view) {

        // make date dialog appear so that user can select date
        DialogFragment f = new DatePickerFragment();
        f.show(getSupportFragmentManager(), "selectDate");
    }

    public void add(View view) {

        // add item using database handler
        Item item = new Item();
        item.setItem(itemName.getText().toString());
        item.setDate(Long.toString(utcMS));
        handler.addItem(item);

        // display toast that item has been added to list
        Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();

        // return to previous activity (ListActivity)
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
