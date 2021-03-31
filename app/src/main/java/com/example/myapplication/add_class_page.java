package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class add_class_page extends AppCompatActivity  {
    int hour;
    int min;
    String day;
    public Context c1 = this;
    TimePicker t1;
    int selection=0;
    static final int Contact_Request = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_page);


        t1= (TimePicker) findViewById(R.id.timer1);


        // GETTING DATA FROM FRAGMENT
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("day") != null){
                day = bundle.getString("day");
                selection = convert_day_to_number.convert(day);
            }
        }

        select_day();

        final EditText className = (EditText) findViewById(R.id.class_name);

        final DatabaseHelper db = new DatabaseHelper(this);

        Button btn = (Button) findViewById(R.id.register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String class_name = className.getText().toString();
                hour = t1.getHour();
                min = t1.getMinute();

                if(!db.addData(class_name, day, hour + ":" + min, "null", "null",  "null", "null", "null", "null")) toastMessage.tM("an error occured", c1);

                Intent intent = new Intent(c1, routine.class);
                startActivity(intent);
            }
        });
    }

    public void select_day(){
        Spinner sp = (Spinner) findViewById(R.id.spin);
        ArrayList<String> list = new ArrayList<>();
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        ArrayAdapter<String> adapter_list = new ArrayAdapter<String>(c1, android.R.layout.simple_list_item_1, list);
        sp.setAdapter(adapter_list);
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
