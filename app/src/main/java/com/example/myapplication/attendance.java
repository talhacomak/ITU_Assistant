package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
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
    TextView[] newClassName, newClassDay, attendance, seeInfos;
    RelativeLayout layout;
    Cursor txt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);
        day_view = findViewById(R.id.day_view);
        classname_view = findViewById(R.id.class_name_view);
        attendance_view = findViewById(R.id.attendance_view);
        infos_view = findViewById(R.id.infos);
        layout = findViewById(R.id.main_layout);
        txt2 = localDb.getAllData();
        newClassName = new TextView[50];
        newClassDay = new TextView[50];
        attendance = new TextView[50];
        seeInfos = new TextView[50];
        attendance_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                attendance_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ProgmaticViews();
            }
        });
    }

    public void ProgmaticViews(){
        int i;
        int classname_view_width = classname_view.getWidth();
        int day_view_width = day_view.getWidth();
        int attendance_view_width = attendance_view.getWidth();
        for (i=1; txt2.moveToNext(); i++){
            if(txt2.getString(9).equals("-1") || txt2.getString(9).equals("0")){
                i--;
                continue;
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(classname_view_width, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_START, R.id.class_name_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.class_name_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.class_name_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            newClassName[i] = new TextView(c1);
            newClassName[i].setLayoutParams(params);
            newClassName[i].setId(i);
            newClassName[i].setText(txt2.getString(3)); // Class name
            newClassName[i].setGravity(Gravity.CENTER);
            newClassName[i].setTextSize(20);
            newClassName[i].setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(newClassName[i]);

            int the_width = (int) newClassName[i].getTextSize();
            int height_count = the_width/classname_view_width + 1 ;

            float scale = c1.getResources().getDisplayMetrics().density;
            int the_height = (int)scale* height_count*60;

            params = new RelativeLayout.LayoutParams(classname_view_width, the_height);
            params.addRule(RelativeLayout.ALIGN_START, R.id.class_name_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.class_name_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.class_name_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            newClassName[i].setLayoutParams(params);

            params = new RelativeLayout.LayoutParams(day_view_width, the_height);
            params.addRule(RelativeLayout.ALIGN_START, R.id.day_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.day_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            newClassDay[i] = new TextView(c1);
            newClassDay[i].setLayoutParams(params);
            newClassDay[i].setText(txt2.getString(5)); // Day
            newClassDay[i].setGravity(Gravity.CENTER);
            newClassDay[i].setTextSize(20);
            newClassDay[i].setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(newClassDay[i]);
            
            params = new RelativeLayout.LayoutParams(attendance_view_width, the_height);
            params.addRule(RelativeLayout.ALIGN_START, R.id.attendance_view);
            params.addRule(RelativeLayout.ALIGN_END, R.id.attendance_view);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            attendance[i] = new TextView(c1);
            attendance[i].setLayoutParams(params);
            attendance[i].setText((txt2.getString(10) + "/14")); // Attendance
            attendance[i].setGravity(Gravity.CENTER);
            attendance[i].setId(i*100);
            attendance[i].setTextSize(20);
            attendance[i].setTextColor(ContextCompat.getColor(c1, R.color.black));
            layout.addView(attendance[i]);
            
            scale = c1.getResources().getDisplayMetrics().density;
            int pixels = (int) (30 * scale + 0.5f);
            params = new RelativeLayout.LayoutParams(pixels, pixels);
            params.addRule(RelativeLayout.ALIGN_START, R.id.infos);
            params.addRule(RelativeLayout.ALIGN_END, R.id.infos);
            if(i == 1){
                params.addRule(RelativeLayout.BELOW, R.id.day_view);
            }
            else params.addRule(RelativeLayout.BELOW, i-1);
            int margin = (the_height - pixels)/2;
            params.setMargins(0, margin, 0, margin);

            seeInfos[i] = new Button(c1);
            seeInfos[i].setLayoutParams(params);
            seeInfos[i].setId(txt2.getInt(0));
            seeInfos[i].setGravity(Gravity.CENTER);
            seeInfos[i].setBackground(ContextCompat.getDrawable(c1, R.drawable.edittt));
            seeInfos[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    Intent intent = new Intent(c1, classAttributes.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 1);
                }
            });
            layout.addView(seeInfos[i]);
        }
    }

}