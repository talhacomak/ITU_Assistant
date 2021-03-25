package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.localDb;

public class classAttributes extends AppCompatActivity  {
    public Context c1 = this;
    int crn;
    TextView tw1, tw2, tw3, tw4, tw5, tw6, tw7, code, crn_view;
    EditText classname, teacher, building, classroom;
    TimePicker start_time, end_time;
    String day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_attributes);

        crn = getIntent().getIntExtra("crn", 0);
        if(crn != 0){
            Cursor rows = localDb.getRowByCRN(crn);
            if(rows.moveToNext()){
                classname = findViewById(R.id.class_name);
                teacher = findViewById(R.id.teacher);
                building = findViewById(R.id.building);
                classroom = findViewById(R.id.classroom);
                code = findViewById(R.id.code);
                crn_view = findViewById(R.id.crn);
                start_time = findViewById(R.id.start_time);
                end_time = findViewById(R.id.end_time);

                classname.setText(rows.getString(3));
                teacher.setText(rows.getString(8));
                building.setText(rows.getString(2));
                classroom.setText(rows.getString(4));
                code.setText(rows.getString(1));
                crn_view.setText(rows.getString(9));

                day = rows.getString(5);

                String[] start = rows.getString(7).split(":");
                String[] end = rows.getString(6).split(":");
                start_time.setHour(Integer.parseInt(start[0]));
                start_time.setMinute(Integer.parseInt(start[1]));
                end_time.setHour(Integer.parseInt(end[0]));
                end_time.setMinute(Integer.parseInt(end[1]));
            }
        }

        select_day();
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDb.updateData(building.getText().toString(), classname.getText().toString(), classroom.getText().toString(),
                        day, String.valueOf(end_time.getHour()) + ":" + String.valueOf(end_time.getMinute()),
                        String.valueOf(start_time.getHour()) + ":" + String.valueOf(start_time.getMinute()), teacher.getText().toString(), String.valueOf(crn));
            }
        });

        set_layouts();
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
