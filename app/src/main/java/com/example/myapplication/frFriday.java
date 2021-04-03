package com.example.myapplication;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class frFriday extends Fragment  {
    DatabaseHelper db;
    public frFriday(){

    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friday, container, false);
        db = new DatabaseHelper(getActivity());
        final Cursor databaseRowsCurDay = db.getRowsByDay("Friday");

        TextView time[] = new TextView[20];
        TextView className[] = new TextView[20];

        LinearLayout layout = view.findViewById(R.id.main_layout);
        for (int i=0; databaseRowsCurDay.moveToNext(); i++){
            time[i] = new TextView(getContext());
            className[i] = new TextView(getContext());
            int id = databaseRowsCurDay.getInt(0);

            LinearLayout linNew = new LinearLayout(getContext());
            linNew.setId(id);
            linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linNew.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(linNew);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            float scale = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics().density;
            int pixels = (int) (3 * scale + 0.5f);
            params.setMarginStart(pixels);
            time[i].setLayoutParams(params);
            time[i].setGravity(Gravity.START);
            time[i].setTextSize(20);
            linNew.addView(time[i]);

            className[i].setLayoutParams(new LinearLayout.LayoutParams(695, LinearLayout.LayoutParams.WRAP_CONTENT));
            className[i].setGravity(Gravity.CENTER);
            className[i].setTextSize(20);
            linNew.addView(className[i]);

            Button delete = new Button(getContext());
            delete.setId(id*100);
            scale = getContext().getResources().getDisplayMetrics().density;
            pixels = (int) (25 * scale + 0.5f);
            delete.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            delete.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trash_bin));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    id /= 100;
                    db.deleteById(id);
                    ((ViewGroup) view.getParent().getParent()).removeView((ViewGroup) view.getParent());
                }
            });
            linNew.addView(delete);

            Button seeInfos = new Button(getContext());
            seeInfos.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            seeInfos.setId(id*10000);
            seeInfos.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edittt));
            seeInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    id /= 10000;
                    Cursor cr = db.getRowByID(id);
                    cr.moveToFirst();
                    String crn = cr.getString(9);
                    if(crn.equals("-")){
                        Intent intent = new Intent(getContext(), add_routine_task.class);
                        intent.putExtra("id", id);
                        startActivityForResult(intent, 1);
                    }
                    else{
                        Intent intent = new Intent(getContext(), classAttributes.class);
                        intent.putExtra("id", id);
                        startActivityForResult(intent, 1);
                    }
                }
            });
            linNew.addView(seeInfos);

            time[i].setText(databaseRowsCurDay.getString(7));
            className[i].setText(databaseRowsCurDay.getString(3));
        }

        Button add_button = view.findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog choice = new Dialog(getContext());
                choice.setContentView(R.layout.choice);
                choice.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                choice.show();
                Button add_class = choice.findViewById(R.id.extra_class);
                add_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getActivity(), com.example.myapplication.classAttributes.class);
                        in.putExtra("day", "Friday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });
                Button add_task = choice.findViewById(R.id.daily_task);
                add_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getActivity(), com.example.myapplication.add_routine_task.class);
                        in.putExtra("day", "Friday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });

            }
        });

        return view;
    }
}
