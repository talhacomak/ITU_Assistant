package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class frPzrtsjava extends Fragment  {
    Context c1 = getContext();
    String days_ar[] = new String[10];
    String startTime[] = new String[10];
    String dersAdiAr[] = new String[10];

    public frPzrtsjava(){

    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pztsi, container, false);

        TextView time[] = new TextView[10];
        TextView ders[] = new TextView[10];

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.main_layout);
        for (int i=0; i<10; i++){
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
            ders[i].setId(100 + i);
            ders[i].setTextSize(20);
            linNew.addView(ders[i]);

            days_ar[i] = "-";
            startTime[i] = "-";
            dersAdiAr[i] = "-";
        }

        DatabaseHelper db = new DatabaseHelper(c1);

        Cursor txt2 = db.getRows("Pazartesi");
        if (txt2 != null ){
            boolean check = true;
            for (int i= 0; check; i++){
                dersAdiAr[i] = txt2.getString(2);
                startTime[i] = txt2.getString(4);
                check = txt2.moveToNext();
            }
        }

        for(int i=0; i<startTime.length; i++){
            if(startTime[i].equals("-") || !days_ar[i].equals("Pazartesi")) continue;
            time[i].setText(startTime[i]);
            ders[i].setText(dersAdiAr[i]);
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

    public void toastMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
