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


public class ekle_cikar extends AppCompatActivity {
    int position=0;
    Context c1 = this;
    FragmentManager manager;
    public static frPzrtsjava myfragPz;
    public static frSalijava myfragSl;
    public static frCarsambajava myfragCar;
    public static frCarsambajava myfragCar1;
    public static frPersembejava myfragPer;
    public static frPersembejava myfragPer1;
    public static frCumajava myfragCuma;
    public static frCumajava myfragCuma1;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dersekle);
        Intent intent = getIntent();
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        myfragPz = new frPzrtsjava();
        transaction.add(R.id.grup, myfragPz, "P");

        myfragSl = new frSalijava();
        transaction.add(R.id.grup, myfragSl,"S");

        myfragCar = new frCarsambajava();
        transaction.add(R.id.grup, myfragCar, "C");

        myfragPer = new frPersembejava();
        transaction.add(R.id.grup, myfragPer, "Per");

        myfragCuma = new frCumajava();
        transaction.add(R.id.grup, myfragCuma, "Cuma");

        transaction.commit();

        Bundle extras = getIntent().getExtras();
        //******************************************
        if (extras != null) { //EKLEMEDEN GERİ DÖNÜŞ
            if(extras.containsKey("hour") && extras.containsKey("min")){
                //int devamsiz = getIntent().getIntExtra("dev", 0);
                int int1 = getIntent().getIntExtra("hour", 0);
                int int2 = getIntent().getIntExtra("min", 0);
                String dersAdi = getIntent().getStringExtra("dersAdi");
                String str = getIntent().getStringExtra("day");

                switch (str){
                    case "Pazartesi":
                        myfragPz = (frPzrtsjava) manager.findFragmentByTag("P");
                        if(myfragPz == null) myfragPz = new frPzrtsjava();
                        Bundle b2 = new Bundle();
                        b2.putInt("hour", int1);
                        b2.putInt("min", int2);
                        b2.putString("dersAdi", dersAdi);
                        myfragPz.setArguments(b2);
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.grup, myfragPz, "P");
                        transaction1.commit();
                        break;
                    case "Sali":
                        myfragSl = (frSalijava) manager.findFragmentByTag("S");
                        if(myfragSl == null) myfragSl = new frSalijava();
                        Bundle b3 = new Bundle();
                        b3.putInt("hour", int1);
                        b3.putInt("min", int2);
                        b3.putString("dersAdi", dersAdi);
                        myfragSl.setArguments(b3);
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.grup, myfragSl, "S");
                        transaction2.commit();
                        break;
                    case "Carsamba":
                        myfragCar1= (frCarsambajava) manager.findFragmentByTag("C");
                        if(myfragCar1 == null) myfragCar1 = new frCarsambajava();
                        Bundle b4 = new Bundle();
                        b4.putInt("hour", int1);
                        b4.putInt("min", int2);
                        b4.putString("dersAdi", dersAdi);

                        myfragCar1.setArguments(b4);
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.grup, myfragCar1, "C");
                        transaction3.commit();
                        break;
                    case "Persembe":
                        myfragPer1 = (frPersembejava) manager.findFragmentByTag("Per");
                        if(myfragPer1 == null) myfragPer1 = new frPersembejava();
                        Bundle b5 = new Bundle();
                        b5.putInt("hour", int1);
                        b5.putInt("min", int2);
                        b5.putString("dersAdi", dersAdi);

                        myfragPer1.setArguments(b5);
                        FragmentTransaction transaction4 = manager.beginTransaction();
                        transaction4.replace(R.id.grup, myfragPer1, "Per");
                        transaction4.commit();
                        break;
                    case "Cuma":
                        myfragCuma1 = (frCumajava) manager.findFragmentByTag("Cuma");
                        if(myfragCuma1 == null) myfragCuma1 = new frCumajava();
                        Bundle b6 = new Bundle();
                        b6.putInt("hour", int1);
                        b6.putInt("min", int2);
                        b6.putString("dersAdi", dersAdi);
                        myfragCuma1.setArguments(b6);
                        FragmentTransaction transaction5 = manager.beginTransaction();
                        transaction5.replace(R.id.grup, myfragCuma1, "Cuma");
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
                    case 0: openSali();
                            break;
                    case 1: openCarsamba();
                        break;
                    case 2: openPersembe();
                        break;
                    case 3: openCuma();
                        break;
                    case 4: openPzrts();
                        break;
                    default: break;
                }
            }
            if(event2.getX() > event1.getX()){
                switch (position){
                    case 2: openSali();
                        break;
                    case 3: openCarsamba();
                        break;
                    case 4: openPersembe();
                        break;
                    case 0: openCuma();
                        break;
                    case 1: openPzrts();
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

    public void openPzrts(View v){

        position = 0;
        myfragPz = (frPzrtsjava) manager.findFragmentByTag("P");
        if(myfragPz == null) myfragPz = new frPzrtsjava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragPz, "P");
        transaction.commit();
    }
    public void openSali(View f){
        position = 1;
        myfragSl = (frSalijava) manager.findFragmentByTag("S");
        if(myfragSl == null) myfragSl = new frSalijava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragSl, "S");
        transaction.commit();
    }
    public void openCarsamba(View f){
        position = 2;
        myfragCar = (frCarsambajava) manager.findFragmentByTag("C");
        if(myfragCar == null) myfragCar = new frCarsambajava();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragCar, "C");
        transaction.commit();
    }
    public void openPersembe(View f){
        position = 3;
        myfragPer = (frPersembejava) manager.findFragmentByTag("Per");
        if(myfragPer == null) myfragPer = new frPersembejava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragPer, "Per");
        transaction.commit();
    }
    public void openCuma(View f){
        position = 4;
        myfragCuma = (frCumajava) manager.findFragmentByTag("Cuma");
        if(myfragCuma == null) myfragCuma = new frCumajava();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragCuma, "Cuma");
        transaction.commit();
    }

    public void openPzrts(){

        position = 0;
        myfragPz = (frPzrtsjava) manager.findFragmentByTag("P");
        if(myfragPz == null) myfragPz = new frPzrtsjava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragPz, "P");
        transaction.commit();
    }
    public void openSali(){
        position = 1;
        myfragSl = (frSalijava) manager.findFragmentByTag("S");
        if(myfragSl == null) myfragSl = new frSalijava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragSl, "S");
        transaction.commit();
    }
    public void openCarsamba(){
        position = 2;
        myfragCar = (frCarsambajava) manager.findFragmentByTag("C");
        if(myfragCar == null) myfragCar = new frCarsambajava();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragCar, "C");
        transaction.commit();
    }
    public void openPersembe(){
        position = 3;
        myfragPer = (frPersembejava) manager.findFragmentByTag("Per");
        if(myfragPer == null) myfragPer = new frPersembejava();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragPer, "Per");
        transaction.commit();
    }
    public void openCuma(){
        position = 4;
        myfragCuma = (frCumajava) manager.findFragmentByTag("Cuma");
        if(myfragCuma == null) myfragCuma = new frCumajava();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.grup, myfragCuma, "Cuma");
        transaction.commit();
    }
}
