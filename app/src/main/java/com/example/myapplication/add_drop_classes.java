package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class add_drop_classes extends AppCompatActivity {
    int position=0;
    Context c1 = this;
    FragmentManager manager;
    public static frMonday myfragMonday;
    public static frTuesday myfragTuesday;
    public static frWednesday myfragWednesday;
    public static frWednesday myfragWednesday1;
    public static frThursday myfragThursday;
    public static frThursday myfragThursday1;
    public static frFriday myfragFriday;
    public static frFriday myfragFriday1;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);
        Intent intent = getIntent();
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        myfragMonday = new frMonday();
        transaction.add(R.id.grup, myfragMonday, "P");

        myfragTuesday = new frTuesday();
        transaction.add(R.id.grup, myfragTuesday,"S");

        myfragWednesday = new frWednesday();
        transaction.add(R.id.grup, myfragWednesday, "C");

        myfragThursday = new frThursday();
        transaction.add(R.id.grup, myfragThursday, "Per");

        myfragFriday = new frFriday();
        transaction.add(R.id.grup, myfragFriday, "Cuma");

        transaction.commit();

        Bundle extras = getIntent().getExtras();
        //******************************************
        if (extras != null) { //Retrun from Add Class
            if(extras.containsKey("hour") && extras.containsKey("min")){
                int int1 = getIntent().getIntExtra("hour", 0);
                int int2 = getIntent().getIntExtra("min", 0);
                String className = getIntent().getStringExtra("className");
                String day = getIntent().getStringExtra("day");

                switch (day){
                    case "Monday":
                        myfragMonday = (frMonday) manager.findFragmentByTag("Mon");
                        if(myfragMonday == null) myfragMonday = new frMonday();
                        Bundle b2 = new Bundle();
                        b2.putInt("hour", int1);
                        b2.putInt("min", int2);
                        b2.putString("className", className);
                        myfragMonday.setArguments(b2);
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.grup, myfragMonday, "Mon");
                        transaction1.commit();
                        break;
                    case "Tuesday":
                        myfragTuesday = (frTuesday) manager.findFragmentByTag("Tue");
                        if(myfragTuesday == null) myfragTuesday = new frTuesday();
                        Bundle b3 = new Bundle();
                        b3.putInt("hour", int1);
                        b3.putInt("min", int2);
                        b3.putString("className", className);
                        myfragTuesday.setArguments(b3);
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.grup, myfragTuesday, "Tue");
                        transaction2.commit();
                        break;
                    case "Wednesday":
                        myfragWednesday1= (frWednesday) manager.findFragmentByTag("Wed");
                        if(myfragWednesday1 == null) myfragWednesday1 = new frWednesday();
                        Bundle b4 = new Bundle();
                        b4.putInt("hour", int1);
                        b4.putInt("min", int2);
                        b4.putString("className", className);

                        myfragWednesday1.setArguments(b4);
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.grup, myfragWednesday1, "Wed");
                        transaction3.commit();
                        break;
                    case "Thursday":
                        myfragThursday1 = (frThursday) manager.findFragmentByTag("Thu");
                        if(myfragThursday1 == null) myfragThursday1 = new frThursday();
                        Bundle b5 = new Bundle();
                        b5.putInt("hour", int1);
                        b5.putInt("min", int2);
                        b5.putString("className", className);

                        myfragThursday1.setArguments(b5);
                        FragmentTransaction transaction4 = manager.beginTransaction();
                        transaction4.replace(R.id.grup, myfragThursday1, "Thu");
                        transaction4.commit();
                        break;
                    case "Friday":
                        myfragFriday1 = (frFriday) manager.findFragmentByTag("Fri");
                        if(myfragFriday1 == null) myfragFriday1 = new frFriday();
                        Bundle b6 = new Bundle();
                        b6.putInt("hour", int1);
                        b6.putInt("min", int2);
                        b6.putString("className", className);
                        myfragFriday1.setArguments(b6);
                        FragmentTransaction transaction5 = manager.beginTransaction();
                        transaction5.replace(R.id.grup, myfragFriday1, "Fri");
                        transaction5.commit();
                        break;
                    default:
                        break;
                }

            }

        } // end bundle get extras

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            if(event2.getX() < event1.getX()){
                switch (position){
                    case 0: viewTuesday();
                            break;
                    case 1: viewWednesday();
                        break;
                    case 2: viewThursday();
                        break;
                    case 3: viewFriday();
                        break;
                    case 4: viewMonday();
                        break;
                    default: break;
                }
            }
            if(event2.getX() > event1.getX()){
                switch (position){
                    case 2: viewTuesday();
                        break;
                    case 3: viewWednesday();
                        break;
                    case 4: viewThursday();
                        break;
                    case 0: viewFriday();
                        break;
                    case 1: viewMonday();
                        break;
                    default: break;
                }
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c1, MainActivity.class);
        startActivity(intent);
    }

    public void viewMonday(View v){

        position = 0;
        myfragMonday = (frMonday) manager.findFragmentByTag("P");
        if(myfragMonday == null) myfragMonday = new frMonday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragMonday, "P");
        transaction.commit();
    }
    public void viewTuesday(View f){
        position = 1;
        myfragTuesday = (frTuesday) manager.findFragmentByTag("S");
        if(myfragTuesday == null) myfragTuesday = new frTuesday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragTuesday, "S");
        transaction.commit();
    }
    public void viewWednesday(View f){
        position = 2;
        myfragWednesday = (frWednesday) manager.findFragmentByTag("C");
        if(myfragWednesday == null) myfragWednesday = new frWednesday();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragWednesday, "C");
        transaction.commit();
    }
    public void viewThursday(View f){
        position = 3;
        myfragThursday = (frThursday) manager.findFragmentByTag("Per");
        if(myfragThursday == null) myfragThursday = new frThursday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragThursday, "Per");
        transaction.commit();
    }
    public void viewFriday(View f){
        position = 4;
        myfragFriday = (frFriday) manager.findFragmentByTag("Cuma");
        if(myfragFriday == null) myfragFriday = new frFriday();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragFriday, "Cuma");
        transaction.commit();
    }

    public void viewMonday(){

        position = 0;
        myfragMonday = (frMonday) manager.findFragmentByTag("P");
        if(myfragMonday == null) myfragMonday = new frMonday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragMonday, "P");
        transaction.commit();
    }
    public void viewTuesday(){
        position = 1;
        myfragTuesday = (frTuesday) manager.findFragmentByTag("S");
        if(myfragTuesday == null) myfragTuesday = new frTuesday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragTuesday, "S");
        transaction.commit();
    }
    public void viewWednesday(){
        position = 2;
        myfragWednesday = (frWednesday) manager.findFragmentByTag("C");
        if(myfragWednesday == null) myfragWednesday = new frWednesday();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragWednesday, "C");
        transaction.commit();
    }
    public void viewThursday(){
        position = 3;
        myfragThursday = (frThursday) manager.findFragmentByTag("Per");
        if(myfragThursday == null) myfragThursday = new frThursday();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragThursday, "Per");
        transaction.commit();
    }
    public void viewFriday(){
        position = 4;
        myfragFriday = (frFriday) manager.findFragmentByTag("Cuma");
        if(myfragFriday == null) myfragFriday = new frFriday();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragFriday, "Cuma");
        transaction.commit();
    }
}
