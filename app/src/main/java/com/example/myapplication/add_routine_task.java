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

public class add_routine_task extends AppCompatActivity {
    public Context c1 = this;
    TextView tw1, tw2, tw3, tw4, tw5, tw6;
    EditText task_name, location, room;
    TimePicker start_time, end_time;
    String day;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_routine_task);
        task_name = findViewById(R.id.task_name);
        location = findViewById(R.id.location);
        room = findViewById(R.id.room);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);

        id = getIntent().getIntExtra("id", -1);
        if(id != -1){
            Cursor row = localDb.getRowByID(id);
            if(row.moveToNext()){
                if(row.getString(3) != null) task_name.setText(row.getString(3));
                if(row.getString(2) != null) location.setText(row.getString(2));
                if(row.getString(4) != null) room.setText(row.getString(4));

                day = row.getString(5);

                String[] start = row.getString(7).split(":");
                start_time.setHour(Integer.parseInt(start[0]));
                start_time.setMinute(Integer.parseInt(start[1]));

                String[] end = row.getString(6).split(":");
                end_time.setHour(Integer.parseInt(end[0]));
                end_time.setMinute(Integer.parseInt(end[1]));
            }
        }

        day = getIntent().getStringExtra("day");
        set_layouts();
        select_day();
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == -1) {
                    boolean res = localDb.addData(null, location.getText().toString(), task_name.getText().toString(), room.getText().toString(),
                            day, end_time.getHour() + ":" + end_time.getMinute(), start_time.getHour() + ":" +
                                    start_time.getMinute(), null, "-1");
                }
                else {
                    boolean res = localDb.updateData(id, location.getText().toString(), task_name.getText().toString(), room.getText().toString(),
                            day, end_time.getHour() + ":" + end_time.getMinute(), start_time.getHour() + ":" +
                                    start_time.getMinute(), null, "-1");
                }
                Intent in = new Intent(c1, routine.class);
                startActivity(in);
            }
        });
    }

    public void set_layouts(){
        tw1 = findViewById(R.id.textView1);
        tw2 = findViewById(R.id.textView2);
        tw3 = findViewById(R.id.textView3);
        tw4 = findViewById(R.id.textView4);
        tw5 = findViewById(R.id.textView5);
        tw6 = findViewById(R.id.textView6);
        tw3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tw3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int theWidth = tw3.getWidth();
                tw2.setWidth(theWidth);
                tw1.setWidth(theWidth);
                tw4.setWidth(theWidth);
                tw5.setWidth(theWidth);
                tw6.setWidth(theWidth);
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
        ArrayAdapter<String> adapter_list = new ArrayAdapter<>(c1, R.layout.spinner_style, list);
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
