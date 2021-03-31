package com.example.myapplication;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class frMonday extends Fragment  {
    public frMonday(){

    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monday, container, false);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor databaseRowsCurDay = db.getRowsByDay("Monday");

        TextView time[] = new TextView[20];
        TextView className[] = new TextView[20];

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.main_layout);
        for (int i=0; databaseRowsCurDay.moveToNext(); i++){
            time[i] = new TextView(getContext());
            className[i] = new TextView(getContext());
            int crn = Integer.parseInt(databaseRowsCurDay.getString(9));

            LinearLayout linNew = new LinearLayout(getContext());
            linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linNew.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(linNew);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            float scale = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics().density;
            int pixels = (int) (3 * scale + 0.5f);
            params.setMarginStart(pixels);
            time[i].setLayoutParams(params);
            time[i].setGravity(Gravity.START);
            time[i].setId(100 + i);
            time[i].setTextSize(20);
            linNew.addView(time[i]);

            className[i].setLayoutParams(new LinearLayout.LayoutParams(695, LinearLayout.LayoutParams.WRAP_CONTENT));
            className[i].setGravity(Gravity.CENTER);
            className[i].setId(200 + i);
            className[i].setTextSize(20);
            linNew.addView(className[i]);

            Button delete = new Button(getContext());
            delete.setId(crn*10);
            scale = getContext().getResources().getDisplayMetrics().density;
            pixels = (int) (25 * scale + 0.5f);
            delete.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            delete.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trash_bin));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            linNew.addView(delete);

            Button seeInfos = new Button(getContext());
            seeInfos.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            seeInfos.setId(crn);
            seeInfos.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edittt));
            seeInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int crn = view.getId();
                    Intent intent = new Intent(getContext(), classAttributes.class);
                    intent.putExtra("crn", crn);
                    startActivityForResult(intent, 1);
                }
            });
            linNew.addView(seeInfos);

            time[i].setText(databaseRowsCurDay.getString(7));
            className[i].setText(databaseRowsCurDay.getString(3));
        }

        Button add_button = (Button) view.findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog choice = new Dialog(getContext());
                choice.setContentView(R.layout.choice);
                choice.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                choice.show();
                Button add_class = choice.findViewById(R.id.extra_class);
                Button add_task = choice.findViewById(R.id.daily_task);
                add_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getActivity(), add_class_page.class);
                        in.putExtra("day", "Monday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });
                add_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getActivity(), add_class_page.class);
                        in.putExtra("day", "Monday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });

            }
        });

        return view;
    }
}
