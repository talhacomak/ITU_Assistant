package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.localDb;

public class classAttributes extends AppCompatActivity  {
    public Context c1 = this;
    int id;
    TextView tw1, tw2, tw3, tw4, tw5, tw6, tw7, code, crn_view;
    EditText classname, teacher, building, classroom;
    TimePicker start_time, end_time;
    String day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_attributes);
        classname = findViewById(R.id.class_name);
        teacher = findViewById(R.id.teacher);
        building = findViewById(R.id.building);
        classroom = findViewById(R.id.classroom);
        code = findViewById(R.id.code);
        crn_view = findViewById(R.id.crn);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);

        id = getIntent().getIntExtra("id", -1); // crn of lecture to show
        if(id != -1){
            Cursor row = localDb.getRowByID(id);
            if(row.moveToNext()){
                if(row.getString(3) != null) classname.setText(row.getString(3));
                if(row.getString(8) != null) teacher.setText(row.getString(8));
                if(row.getString(2) != null) building.setText(row.getString(2));
                if(row.getString(4) != null) classroom.setText(row.getString(4));
                if(row.getString(1) != null) code.setText(row.getString(1));
                if(row.getString(9) != null) crn_view.setText(row.getString(9));

                day = row.getString(5);

                String[] start = row.getString(7).split(":");
                start_time.setHour(Integer.parseInt(start[0]));
                start_time.setMinute(Integer.parseInt(start[1]));

                String[] end = row.getString(6).split(":");
                end_time.setHour(Integer.parseInt(end[0]));
                end_time.setMinute(Integer.parseInt(end[1]));
            }
        }
        else{
            day = getIntent().getStringExtra("day");
            code.setText("-");
            crn_view.setText("-");
        }

        select_day();
        set_layouts();
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == -1) { // add new data
                    localDb.addData("-", building.getText().toString(), classname.getText().toString(), classroom.getText().toString(),
                            day, end_time.getHour() + ":" + getMinuteFormat(end_time.getMinute()), start_time.getHour() + ":" +
                                    getMinuteFormat(start_time.getMinute()), teacher.getText().toString(), "-");
                }
                else {  // update the data
                    localDb.updateData(id, building.getText().toString(), classname.getText().toString(), classroom.getText().toString(),
                            day, end_time.getHour() + ":" + getMinuteFormat(end_time.getMinute()), start_time.getHour() + ":" +
                                    getMinuteFormat(start_time.getMinute()), teacher.getText().toString(), crn_view.getText().toString());
                }
                Intent in = new Intent(c1, routine.class);
                startActivity(in);
            }
        });
    }

    private String getMinuteFormat(int minute) {
        if(minute<10)
            return "0" + minute;
        else
            return String.valueOf(minute);
    }

    public void set_layouts(){
        tw1 = findViewById(R.id.textView1);
        tw2 = findViewById(R.id.textView2);
        tw3 = findViewById(R.id.textView3);
        tw4 = findViewById(R.id.textView4);
        tw5 = findViewById(R.id.textView5);
        tw6 = findViewById(R.id.textView6);
        tw7 = findViewById(R.id.textView7);
        tw1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tw1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int theWidth = tw1.getWidth();
                tw2.setWidth(theWidth);
                tw3.setWidth(theWidth);
                tw4.setWidth(theWidth);
                tw5.setWidth(theWidth);
                tw6.setWidth(theWidth);
                tw7.setWidth(theWidth);
            }
        });
    }

    public void select_day(){
        Spinner sp = findViewById(R.id.spin);
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.monday));
        list.add(getString(R.string.tuesday));
        list.add(getString(R.string.wednesday));
        list.add(getString(R.string.thursday));
        list.add(getString(R.string.friday));
        ArrayAdapter<String> adapter_list = new ArrayAdapter<String>(c1, R.layout.spinner_style, list);
        sp.setAdapter(adapter_list);
        final int selection = convert_day_to_number.convert(day);
        sp.setSelection(selection);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(selection);
            }
        });
    }
}
