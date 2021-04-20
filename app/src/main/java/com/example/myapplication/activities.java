package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyhope.eventcalenderlibrary.ActivityDatabase;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class activities extends AppCompatActivity {
    Context c1 = this;
    DatePicker datePicker;
    String date;
    LinearLayout layout;

    ActivityDatabase activityDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities);

        //datePicker = findViewById(R.id.calendar1);
        //initDatePicker();
        layout = findViewById(R.id.main_layout);
        activityDB = new ActivityDatabase(c1);

        placeActivities();

        Button btn = findViewById(R.id.add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(c1, add_activity.class);
                if(date !=null) {
                    in.putExtra("date", date);
                }
                startActivityForResult(in, 2);
            }
        });

    }

    private void placeActivities() {
        final Cursor txt2 = activityDB.getAllData();
        for (int i=1; txt2.moveToNext(); i++){
            final LinearLayout linNew = new LinearLayout(c1);
            int id = txt2.getInt(0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 5, 15, 5);
            linNew.setLayoutParams(params);
            linNew.setId(id);
            linNew.setBackground(ContextCompat.getDrawable(c1, R.drawable.rounded_corners));
            linNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c1, add_activity.class);
                    intent.putExtra("id", view.getId());
                    startActivityForResult(intent, 2);
                }
            });
            linNew.setOrientation(LinearLayout.HORIZONTAL);

            TextView eventText = new TextView(c1);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            width = width*4/5;
            params = new LinearLayout.LayoutParams(width-50, 120);
            params.setMargins(25, 20, 0, 60);
            eventText.setLayoutParams(params);
            String text = "<b>" + txt2.getString(1) + "</b> <br>" + txt2.getString(7) + " | " + txt2.getString(6);
            eventText.setText(Html.fromHtml(text));
            eventText.setTextSize(20);
            eventText.setTextColor(ContextCompat.getColor(c1, R.color.black));
            linNew.addView(eventText);

            Button delete = new Button(c1);
            delete.setId(id*100);
            float scale = c1.getResources().getDisplayMetrics().density;
            int pixels = (int) (25 * scale + 0.5f);
            params = new LinearLayout.LayoutParams(pixels, pixels);
            params.setMargins(0, 30, 0, 0);
            delete.setLayoutParams(params);
            delete.setBackground(ContextCompat.getDrawable(c1, R.drawable.trash_bin));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    id /= 100;
                    activityDB.deleteById(id);
                    ((ViewGroup) view.getParent().getParent()).removeView((ViewGroup) view.getParent());
                }
            });
            linNew.addView(delete);

            Button seeInfos = new Button(c1);
            params = new LinearLayout.LayoutParams(pixels, pixels);
            params.setMargins(5, 30, 0, 0);
            seeInfos.setLayoutParams(params);
            seeInfos.setId(id);
            seeInfos.setBackground(ContextCompat.getDrawable(c1, R.drawable.edittt));
            seeInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int view_id = view.getId();
                    Intent intent = new Intent(c1, add_activity.class);
                    intent.putExtra("id", view_id);
                    startActivityForResult(intent, 2);
                }
            });
            linNew.addView(seeInfos);

            layout.addView(linNew);
        }
    }

    private void initDatePicker(){
        DatePicker.OnDateChangedListener onDateChange = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = add_activity.makeDateString(dayOfMonth, monthOfYear+1, year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, onDateChange);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c1, MainActivity.class);
        startActivity(intent);
    }
}
