package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.Contact_Request;

public class tasks extends AppCompatActivity {
    Context c1 = this;
    DatePicker datePicker;
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        datePicker = findViewById(R.id.calender1);
        initDatePicker();

        Button btn = findViewById(R.id.add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(c1, add_task.class);
                if(date !=null) {
                    in.putExtra("date", date);
                }
                startActivityForResult(in, Contact_Request);
            }
        });

    }

    private void initDatePicker(){
        DatePicker.OnDateChangedListener onDateChange = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = add_task.makeDateString(dayOfMonth, monthOfYear, year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, onDateChange);
    }
}
