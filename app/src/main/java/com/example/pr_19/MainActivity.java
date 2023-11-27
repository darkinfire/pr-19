package com.example.pr_19;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView timePick;
    Button btnDate, btnTime, btnOpenDialog;

    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePick = findViewById(R.id.time_pick);
        btnDate = findViewById(R.id.button_date);
        btnTime = findViewById(R.id.button_time);
        btnOpenDialog = findViewById(R.id.button_open_dialog);

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnOpenDialog.setOnClickListener(this);

        setInitialDateTime();
    }

    private void setInitialDateTime() {
        timePick.setText(DateUtils.formatDateTime(
                this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    @Override
    public void onClick(View view) {
        int ID = view.getId();
        if (R.id.button_date == ID)
            new DatePickerDialog(
                    MainActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        else if (R.id.button_time == ID) {
            new TimePickerDialog(
                    MainActivity.this, t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE),
                    true)
                    .show();
        } else if (ID == R.id.button_open_dialog) {
            openCustomDialog();
        }
    }

    private void openCustomDialog() {
        MyDialogFragment myDialog = new MyDialogFragment();
        myDialog.setDialogListener(data -> timePick.setText(data));
        myDialog.show(getSupportFragmentManager(), "MY_DIALOG");
        myDialog.setDatePickerDialog(new DatePickerDialog(
                this,
                myDialog.dateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)));
    }

    TimePickerDialog.OnTimeSetListener t = (view1, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    DatePickerDialog.OnDateSetListener d = (view12, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

}
