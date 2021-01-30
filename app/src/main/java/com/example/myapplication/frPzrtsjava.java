package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frPzrtsjava extends Fragment  {

    public frPzrtsjava(){

    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pztsi, container, false);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor txt2 = db.getRowsByDay("Pazartesi");

        TextView time[] = new TextView[20];
        TextView ders[] = new TextView[20];

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.main_layout);
        for (int i=0; txt2.moveToNext(); i++){
            time[i] = new TextView(getContext());
            ders[i] = new TextView(getContext());

            LinearLayout linNew = new LinearLayout(getContext());
            linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linNew.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(linNew);

            time[i].setLayoutParams(new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT));
            time[i].setGravity(Gravity.CENTER);
            time[i].setId(100 + i);
            time[i].setTextSize(20);
            linNew.addView(time[i]);

            ders[i].setLayoutParams(new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT));
            ders[i].setGravity(Gravity.CENTER);
            ders[i].setId(200 + i);
            ders[i].setTextSize(20);
            linNew.addView(ders[i]);

            time[i].setText(txt2.getString(1));
            ders[i].setText(txt2.getString(3));
        }


        Button btnnew = (Button) view.findViewById(R.id.eklemeButonu);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ekleme_sayfasi.class);
                in.putExtra("day", "Pazartesi");
                startActivity(in);
            }
        });

        return view;
    }
}
