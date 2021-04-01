package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class add_task extends AppCompatActivity {
    Context c1 = this;
    ListView list1;
    ListView list2;
    ListView list3;
    static final String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};

    static final String[] numbers2 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    static final String[] numbers3 = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        Intent intent = getIntent();
        list1 = (ListView) findViewById(R.id.l1);
        list2 = (ListView) findViewById(R.id.l2);
        list3 = (ListView) findViewById(R.id.l3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, numbers);
        list1.setAdapter(adapter);
        list1.setItemsCanFocus(true);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list1.setSelection(i);
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, numbers2);
        list2.setAdapter(adapter2);
        list2.setItemsCanFocus(true);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list2.setSelection(i);
            }
        });
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(c1, android.R.layout.simple_list_item_1, android.R.id.text1, numbers3);
        list3.setAdapter(adapter3);
        list3.setItemsCanFocus(true);
        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list3.setSelection(i);
            }
        });
    }
}
