package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ekleme_sayfasi extends AppCompatActivity  {
    int hour;
    int min;
    String day;
    Context c1 = this;
    TimePicker t1;
    int selection=0;
    static final int Contact_Request = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekleme_sayfasi);


        t1= (TimePicker) findViewById(R.id.timer1);


        // FRAGMENTTAN GELENLERİ ALMA
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("day") != null){
                Toast.makeText(getApplicationContext(), "data: " + bundle.getString("day"), Toast.LENGTH_SHORT).show();
                day = bundle.getString("day");
                switch (day){
                    case "Pazartesi" : selection = 0;
                        break;
                    case "Sali" : selection = 1;
                        break;
                    case "Carsamba" : selection = 2;
                        break;
                    case "Persembe" : selection = 3;
                        break;
                    case "Cuma" : selection = 4;
                        break;
                    default:
                        break;
                }

            }
        }


        //GÜN SEÇİMİ
        Spinner sp = (Spinner) findViewById(R.id.spin);
        ArrayList<String> liste = new ArrayList<>();
        liste.add("Pazartesi");
        liste.add("Sali");
        liste.add("Carsamba");
        liste.add("Persembe");
        liste.add("Cuma");
        ArrayAdapter<String> adapter_list = new ArrayAdapter<String>(c1, android.R.layout.simple_list_item_1, liste);
        sp.setAdapter(adapter_list);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(selection);
            }
        });
        // GÜN SEÇİMİ SON


        final EditText dersinAdi = (EditText) findViewById(R.id.dersAdi);

        final EditText devamsiz = (EditText) findViewById(R.id.devamsizlik);

        final DatabaseHelper db = new DatabaseHelper(this);

        Button btn = (Button) findViewById(R.id.kaydet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dersAdi = dersinAdi.getText().toString();
                //final int devamsizlik = Integer.parseInt(devamsiz.getText().toString());
                hour = t1.getHour();
                min = t1.getMinute();

                db.addData(dersAdi, day,hour + ":" + min, "null", "null",  "null");
                Intent intent = new Intent(c1, ekle_cikar.class);
                startActivity(intent);
            }
        });
    }

}
