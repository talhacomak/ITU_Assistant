package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class devamsizliklar extends AppCompatActivity {
    Context c1 = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devamsiz);
        Intent intent = getIntent();

        TextView t[] = new TextView[17];

        TextView d[] = new TextView[17];

        TextView dvm[] = new TextView[17];

        TextView dvmTop[] = new TextView[17];

        if(intent.getExtras() != null){

        }
    }
}