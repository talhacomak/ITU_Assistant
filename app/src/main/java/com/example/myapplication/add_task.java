package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class add_task extends AppCompatActivity {
    Context c1 = this;
    ListView dayList, hourList, minList;
    TextView tw1, tw2, tw3;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private CheckBox checkAlarmBox, checkNofityBox;
    LinearLayout notificationLayout, timeLayout;

    static final String[] dayNumbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

    static final String[] hourNumbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    static final String[] minNumbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        set_layouts();
        
        dayList = findViewById(R.id.l1);
        hourList = findViewById(R.id.l2);
        minList = findViewById(R.id.l3);
        initReminderLists();
        
        dateButton = findViewById(R.id.datePickerButton);
        String date = getTodaysDate();
        if (getIntent().getStringExtra("date") != null) date = getIntent().getStringExtra("date");
        dateButton.setText(date);
        initDatePicker();
        
        checkAlarmBox = findViewById(R.id.alarm);
        checkNofityBox = findViewById(R.id.reminder);
        timeLayout = findViewById(R.id.timePickerLayout);
        notificationLayout = findViewById(R.id.notificationLayout);
        initAlarmBox();
        initNotifyBox();
    }

    
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = R.style.SpinnerDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        // datePickerDialog.getDatePicker().setSpinnersShown(true);  // doesn't work
        // datePickerDialog.getDatePicker().setCalendarViewShown(false); // doesn't work
    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    static public String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    public void register(View view){
        //Alarm manager ve notification managera bak
        Intent in = new Intent(c1, tasks.class);
        startActivity(in);
    }

    static public String getMonthFormat(int month){
        if(month == 1)
            return "JAN";
        else if(month == 2)
            return "FEB";
        else if(month == 3)
            return "MAR";
        else if(month == 4)
            return "APR";
        else if(month == 5)
            return "MAY";
        else if(month == 6)
            return "JUN";
        else if(month == 7)
            return "JUL";
        else if(month == 8)
            return "AUG";
        else if(month == 9)
            return "SEP";
        else if(month == 10)
            return "OCT";
        else if(month == 11)
            return "NOV";
        else if(month == 12)
            return "DEC";
        return "JAN";
    }

    private void initNotifyBox() {
        checkNofityBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNofityBox.isChecked()){
                    showNotification();
                }
                else {
                    hideNotification();
                }
            }
        });
    }

    private void initAlarmBox() {
        checkAlarmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAlarmBox.isChecked()){
                    showAlarms();
                }
                else {
                    hideAlarms();
                }
            }
        });
    }

    private void hideAlarms() {
        timeLayout.setVisibility(GONE);
    }

    private void showAlarms() {
        timeLayout.setVisibility(VISIBLE);
    }


    private void hideNotification() {
        notificationLayout.setVisibility(GONE);
    }


    private void showNotification() {
        notificationLayout.setVisibility(VISIBLE);
    }


    private void initReminderLists() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, dayNumbers);
        dayList.setAdapter(adapter);
        dayList.setItemsCanFocus(true);
        dayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dayList.setSelection(i);
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, hourNumbers);
        hourList.setAdapter(adapter2);
        hourList.setItemsCanFocus(true);
        hourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hourList.setSelection(i);
            }
        });
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, minNumbers);
        minList.setAdapter(adapter3);
        minList.setItemsCanFocus(true);
        minList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                minList.setSelection(i);
            }
        });
    }

    public void set_layouts(){
        tw1 = findViewById(R.id.textView1);
        tw2 = findViewById(R.id.textView2);
        tw3 = findViewById(R.id.textView3);
        tw3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tw3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int theWidth = tw3.getWidth();
                tw1.setWidth(theWidth);
                tw2.setWidth(theWidth);
            }
        });
    }
}
