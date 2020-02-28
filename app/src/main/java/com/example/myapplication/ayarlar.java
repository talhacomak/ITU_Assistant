package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class ayarlar extends AppCompatActivity {
    Context c1 = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayarlar_lay);
        Intent intent = getIntent();
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_pref shr = new shared_pref();
                shr.clear(c1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c1, MainActivity.class);
        startActivity(intent);
    }
}
