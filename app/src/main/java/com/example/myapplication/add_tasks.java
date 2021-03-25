package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.Contact_Request;

public class add_tasks extends AppCompatActivity {
    Context c1 = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);
        Intent intent = getIntent();

        Button btn = (Button) findViewById(R.id.add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(c1, calender_tasks.class);
                startActivityForResult(in, Contact_Request);
            }
        });

    }
}
