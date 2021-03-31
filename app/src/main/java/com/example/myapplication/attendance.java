package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.example.myapplication.MainActivity.localDb;

public class attendance extends AppCompatActivity {
    Context c1 = this;
    TextView day_view, classname_view, attendance_view, infos_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);
        ProgmaticViews();

    }
    public void ProgmaticViews(){
        final Cursor txt2 = localDb.getAllData();
        day_view = findViewById(R.id.day_view);
        classname_view = findViewById(R.id.class_name_view);
        attendance_view = findViewById(R.id.attendance_view);
        infos_view = findViewById(R.id.infos);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
        for (int i=1; txt2.moveToNext(); i++){
            int crn = Integer.parseInt(txt2.getString(9));

            TextView newClassName = new TextView(c1);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(classname_view.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_START, R.id.class_name_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.class_name_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.class_name_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            newClassName.setLayoutParams(params);
            newClassName.setId(i);
            newClassName.setText(txt2.getString(3)); // Class name
            newClassName.setGravity(Gravity.CENTER);
            newClassName.setTextSize(20);
            newClassName.setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(newClassName);

            TextView newClassDay = new TextView(c1);
            params = new RelativeLayout.LayoutParams(day_view.getWidth(), params.height);
            params.addRule(RelativeLayout.ALIGN_START, R.id.day_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.day_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            newClassDay.setLayoutParams(params);
            newClassDay.setText(txt2.getString(5)); // Day
            newClassDay.setGravity(Gravity.CENTER);
            newClassDay.setTextSize(20);
            newClassDay.setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(newClassDay);

            TextView attendance = new TextView(c1);
            params = new RelativeLayout.LayoutParams(attendance_view.getWidth(), newClassName.getLayoutParams().height);
            params.addRule(RelativeLayout.ALIGN_START, R.id.attendance_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.attendance_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            attendance.setLayoutParams(params);
            attendance.setText((txt2.getString(10) + "/14")); // Attendance
            attendance.setGravity(Gravity.CENTER);
            attendance.setTextSize(20);
            attendance.setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(attendance);

            /*
            Button increaseAtt = new Button(c1);
            params = new RelativeLayout.LayoutParams(attendance_view.getWidth()/2, newClassName.getLayoutParams().height);
            params.addRule(RelativeLayout.START_OF, crn*1000);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            increaseAtt.setLayoutParams(params);
            attendance.setId(crn);
            increaseAtt.setText("-");
            increaseAtt.setBackgroundColor(ContextCompat.getColor(c1, R.color.gri));
            increaseAtt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    Cursor rows = localDb.getRowByCRN(crn);
                    if(rows.moveToNext()){
                        int att = rows.getInt(10);
                        localDb.updateAttendance(att + -1, crn);
                    }
                }
            });
            layout.addView(increaseAtt);

            Button decreaseAtt = new Button(c1);
            params = new RelativeLayout.LayoutParams(attendance_view.getWidth()/2, newClassName.getLayoutParams().height);
            params.addRule(RelativeLayout.START_OF, crn);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            params.width = attendance_view.getWidth()/6;
            params.height = newClassName.getLayoutParams().height;
            decreaseAtt.setLayoutParams(params);
            attendance.setId(crn*10);
            decreaseAtt.setGravity(Gravity.END);
            decreaseAtt.setText("+");
            decreaseAtt.setBackgroundColor(ContextCompat.getColor(c1, R.color.gri));
            decreaseAtt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    crn /= 10;
                    Cursor rows = localDb.getRowByCRN(crn);
                    if(rows.moveToNext()){
                        int att = rows.getInt(10);
                        localDb.updateAttendance(att +1, crn);
                    }
                }
            });
            layout.addView(decreaseAtt);
             */

            Button seeInfos = new Button(c1);
            float scale = c1.getResources().getDisplayMetrics().density;
            int pixels = (int) (30 * scale + 0.5f);
            params = new RelativeLayout.LayoutParams(pixels, pixels);
            params.addRule(RelativeLayout.ALIGN_START, R.id.infos);
            params.addRule(RelativeLayout.ALIGN_END, R.id.infos);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            seeInfos.setLayoutParams(params);
            seeInfos.setId(crn);
            seeInfos.setGravity(Gravity.RIGHT);
            seeInfos.setBackground(ContextCompat.getDrawable(c1, R.drawable.edittt));
            seeInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    Intent intent = new Intent(c1, classAttributes.class);
                    intent.putExtra("crn", crn);
                    startActivityForResult(intent, 1);
                }
            });
            layout.addView(seeInfos);
        }
    }
}