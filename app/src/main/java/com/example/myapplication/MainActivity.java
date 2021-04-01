package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button routine, attendance_button, note, settings, tasks;
    Context c1 = this;
    static final int Contact_Request = 1;
    FirebaseDatabase remote_db;
    static DatabaseHelper localDb;
    DatabaseReference dbref, dbrefCRN;
    ArrayList<String> values, crns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);

        //Window window = ((Activity) c1).getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        //window.setStatusBarColor(ContextCompat.getColor(c1, R.color.black));

        remote_db = FirebaseDatabase.getInstance();
        localDb = new DatabaseHelper(c1);
        //localDb.deleteTable();
        localDb.createTable();
        crns = new ArrayList<>();
        values = new ArrayList<>();

        settings = findViewById(R.id.button3);
        attendance_button = findViewById(R.id.button2);
        routine = findViewById(R.id.button);
        tasks = findViewById(R.id.button5);
        note = findViewById(R.id.button4);

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, routine.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        attendance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, attendance.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, notes.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, settings.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, tasks.class);
                startActivityForResult(intent, Contact_Request);
            }
        });

        dbref = remote_db.getReference("Students/1/crns");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                Iterable<DataSnapshot> keys = snapshot.getChildren(); // key'ler remote db'deki CRN'ler
                for (DataSnapshot key: keys){
                    Cursor localCRNs = localDb.getAllData();
                    String xkey = key.getKey();
                    boolean check = true;
                    while (localCRNs.moveToNext()){  // localdeki CRN'lerle remote'dan gelenleri karşılaştır
                        String x = localCRNs.getString(9);
                        if(x.equals(xkey)){
                            check = false;
                            break;
                        }
                    }
                    if(check){ // çakışma olmadıysa local db'ye ekle
                        crns.add(xkey);
                    }
                }
                dbrefCRN = remote_db.getReference("crn");
                dbrefCRN.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(int i=0; i<crns.size(); i++){
                            DataSnapshot snapshot2 = snapshot.child(crns.get(i));
                            Iterable<DataSnapshot> keys = snapshot2.getChildren();
                            values.add(crns.get(i));
                            for (DataSnapshot key : keys) {
                                values.add(key.getValue().toString());
                            }
                            boolean res = localDb.addData(values.get(1), values.get(2), values.get(3), values.get(4),
                                    values.get(5), values.get(6), values.get(7), values.get(8), values.get(0)); // ekleme işlemi için sıralamayı ayarla, key.getValue eklicen
                            values.clear();
                        }
                        ProgmaticViews();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    static class RequestTask_Get1 extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... str) {


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);

        }

    }

    public void ProgmaticViews(){
        final Cursor txt2 = localDb.getRowsByDay(day_of_weekday());
        for (int i=0; txt2.moveToNext(); i++){
            LinearLayout layout = findViewById(R.id.derslayout);

            LinearLayout linNew = new LinearLayout(c1);
            linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linNew.setId(i);
            linNew.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(linNew);

            TextView newClassTime = new TextView(c1);
            newClassTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            newClassTime.setText(txt2.getString(7)); // start time
            newClassTime.setGravity(Gravity.CENTER);
            newClassTime.setTextSize(20);
            newClassTime.setTextColor(ContextCompat.getColor(c1, R.color.black));
            linNew.addView(newClassTime);

            TextView newClassName = new TextView(c1);
            newClassName.setLayoutParams(new LinearLayout.LayoutParams(745, LinearLayout.LayoutParams.WRAP_CONTENT));
            newClassName.setText(txt2.getString(3)); // class or task name
            newClassName.setGravity(Gravity.CENTER);
            newClassName.setTextSize(20);
            newClassName.setTextColor(ContextCompat.getColor(c1, R.color.black));
            linNew.addView(newClassName);

            if(txt2.getString(9).equals("-1")) continue; // if it is a task

            TextView attendanceView = new TextView(c1);
            attendanceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            attendanceView.setText((txt2.getString(10) + "/14"));
            attendanceView.setGravity(Gravity.CENTER);
            attendanceView.setTextSize(20);
            attendanceView.setId(txt2.getInt(9)*10);
            attendanceView.setTextColor(ContextCompat.getColor(c1, R.color.black));
            linNew.addView(attendanceView);

            final CheckBox attendance = new CheckBox(c1);
            attendance.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            attendance.setGravity(Gravity.END);
            attendance.setBackgroundColor(ContextCompat.getColor(c1, R.color.white));
            attendance.setId(txt2.getInt(9));
            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    Cursor rows = localDb.getRowByCRN(crn);
                    if(rows.moveToNext()){
                        int att = rows.getInt(10);
                        TextView attendanceView = findViewById(crn*10);
                        if(attendance.isChecked()){
                            attendanceView.setText((att +1 + "/14"));
                            localDb.updateAttendance(att +1, crn);
                        }
                        else{
                            attendanceView.setText((att -1 + "/14"));
                            localDb.updateAttendance(att + -1, crn);
                        }
                    }
                }
            });
            linNew.addView(attendance);

            /*
            RadioGroup rdgr = new RadioGroup(c1);
            rdgr.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            rdgr.setOrientation(LinearLayout.HORIZONTAL);
            rdgr.setGravity(Gravity.RIGHT);
            rdgr.setBackgroundColor(ContextCompat.getColor(c1, R.color.grey));
            linNew.addView(rdgr);

            RadioButton rb1 = new RadioButton(c1);
            rb1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rb1.setGravity(Gravity.CENTER);
            rb1.setId(txt2.getInt(9));
            rb1.setOnClickListener(new View.OnClickListener() {
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
            rdgr.addView(rb1);

            RadioButton rb2 = new RadioButton(c1);
            rb2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rb2.setGravity(Gravity.CENTER);
            rb2.setId(txt2.getInt(9)*10);
            rb2.setTextColor(ContextCompat.getColor(c1, R.color.black));
            rb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    crn /= 10;
                    Cursor rows = localDb.getRowByCRN(crn);
                    if(rows.moveToNext()){
                        int att = rows.getInt(10);
                        localDb.updateAttendance(att + 1, crn);
                    }
                }
            });
            rdgr.addView(rb2);
             */
        }
    }

    public static String day_of_weekday(){
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        String day;
        switch (day_of_week){
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 1:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            default:
                day = "noday";
                break;
        }
        return day;
    }

    public void open_ders(View view){
        Intent intent = new Intent(c1, routine.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_dvm(View view){
        Intent intent = new Intent(c1, attendance.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_notlar(View view){
        Intent intent = new Intent(c1, notes.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_settings(View view){
        Intent intent = new Intent(c1, settings.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_calender(View view){
        Intent intent = new Intent(c1, tasks.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_GPS(View view){

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
