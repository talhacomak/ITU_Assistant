package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class gorevEkle extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
    }
}
