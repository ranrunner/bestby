package com.ranrunner.bestby;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // date value in UTC milliseconds
    long utcMS = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // use current date as default in picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // create new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker dp, int year, int month, int day) {

        // set utcMS based on selected date
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, dp.getYear());
        c.set(Calendar.MONTH, dp.getMonth());
        c.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
        utcMS = c.getTimeInMillis();

        // update utcMS on AddActivity
        ((AddActivity)getActivity()).utcMS = this.utcMS;

        // reflect selected date on AddActivity
        ((AddActivity)getActivity()).monthText.setText(Integer.toString(month + 1));
        ((AddActivity)getActivity()).dayText.setText(Integer.toString(day));
        ((AddActivity)getActivity()).yearText.setText(Integer.toString(year));
    }
}
