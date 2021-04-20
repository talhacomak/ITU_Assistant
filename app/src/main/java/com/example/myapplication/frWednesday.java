package com.example.myapplication;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.END;

public class frWednesday extends Fragment  {
    RoutineDatabase db;
    public frWednesday(){

    }
    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wednesday, container, false);
        db = new RoutineDatabase(getActivity());
        final Cursor databaseRowsCurDay = db.getRowsByDay("Wednesday");

        TextView[] time = new TextView[20];
        TextView[] className = new TextView[20];

        LinearLayout layout = view.findViewById(R.id.main_layout);
        for (int i=0; databaseRowsCurDay.moveToNext(); i++){
            time[i] = new TextView(getContext());
            className[i] = new TextView(getContext());
            int id = databaseRowsCurDay.getInt(0);

            LinearLayout linearlayoutLine = new LinearLayout(getContext());
            linearlayoutLine.setId(id);
            linearlayoutLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearlayoutLine.setOrientation(LinearLayout.HORIZONTAL);

            final float scale = getResources().getDisplayMetrics().density;

            LinearLayout timeLayout = new LinearLayout(getContext());
            timeLayout.setOrientation(LinearLayout.VERTICAL);
            timeLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            time[i].setText(databaseRowsCurDay.getString(7));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);// height = 50 dp
            int pixels = (int) (3 * scale + 0.5f);
            params.setMarginStart(pixels);
            time[i].setLayoutParams(params);
            time[i].setGravity(CENTER);
            time[i].setTextSize(20);
            timeLayout.addView(time[i]);
            linearlayoutLine.addView(timeLayout);


            LinearLayout taskNameLayout = new LinearLayout(getContext());
            taskNameLayout.setOrientation(LinearLayout.VERTICAL);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)scale*7);
            taskNameLayout.setGravity(CENTER);
            taskNameLayout.setLayoutParams(params);
            className[i].setText(databaseRowsCurDay.getString(3));
            className[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            className[i].setGravity(CENTER);
            className[i].setTextSize(20);
            taskNameLayout.addView(className[i]);
            linearlayoutLine.addView(taskNameLayout);


            LinearLayout deleteLayout = new LinearLayout(getContext());
            deleteLayout.setOrientation(LinearLayout.VERTICAL);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMarginEnd((int)scale*7);
            deleteLayout.setLayoutParams(params);
            deleteLayout.setGravity(END);
            Button delete = new Button(getContext());
            delete.setId(id*100);
            pixels = (int) (25 * scale + 0.5f);
            delete.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            delete.setGravity(CENTER);
            delete.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.trash_bin));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    id /= 100;
                    db.deleteById(id);
                    ((ViewGroup) view.getParent().getParent().getParent()).removeView((ViewGroup) view.getParent().getParent());
                }
            });
            deleteLayout.addView(delete);
            linearlayoutLine.addView(deleteLayout);

            LinearLayout infosLayout = new LinearLayout(getContext());
            infosLayout.setOrientation(LinearLayout.VERTICAL);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMarginEnd((int)scale*7);
            infosLayout.setLayoutParams(params);
            infosLayout.setGravity(END);
            Button seeInfos = new Button(getContext());
            params = new LinearLayout.LayoutParams(pixels, pixels);
            seeInfos.setLayoutParams(params);
            seeInfos.setId(id*10000);
            seeInfos.setGravity(CENTER);
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
            infosLayout.addView(seeInfos);
            linearlayoutLine.addView(infosLayout);

            layout.addView(linearlayoutLine);
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
                        in.putExtra("day", "Wednesday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });
                Button add_task = choice.findViewById(R.id.daily_task);
                add_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getActivity(), com.example.myapplication.add_routine_task.class);
                        in.putExtra("day", "Wednesday");
                        choice.dismiss();
                        startActivity(in);
                    }
                });

            }
        });

        return view;
    }
}
