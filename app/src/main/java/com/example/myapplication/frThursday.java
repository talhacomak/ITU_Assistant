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

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frThursday extends Fragment  {

    public frThursday(){

    }


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thursday, container, false);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor txt2 = db.getRowsByDay("Thursday");

        TextView time[] = new TextView[20];
        TextView className[] = new TextView[20];

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.main_layout);
        for (int i=0; txt2.moveToNext(); i++){
            time[i] = new TextView(getContext());
            className[i] = new TextView(getContext());

            LinearLayout linNew = new LinearLayout(getContext());
            linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linNew.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(linNew);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            final float scale = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics().density;
            int pixels = (int) (3 * scale + 0.5f);
            params.setMarginStart(pixels);
            time[i].setLayoutParams(params);
            time[i].setGravity(Gravity.START);
            time[i].setId(100 + i);
            time[i].setTextSize(20);
            linNew.addView(time[i]);

            className[i].setLayoutParams(new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT));
            className[i].setGravity(Gravity.CENTER);
            className[i].setId(200 + i);
            className[i].setTextSize(20);
            linNew.addView(className[i]);

            time[i].setText(txt2.getString(7));
            className[i].setText(txt2.getString(3));
        }


        Button btnnew = (Button) view.findViewById(R.id.add_button);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), classAttributes.class);
                in.putExtra("day", "Thursday");
                startActivity(in);
            }
        });

        return view;
    }
}
