package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class devamsizliklar extends AppCompatActivity {
    Context c1 = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devamsiz);
        Intent intent = getIntent();

        TextView t[] = new TextView[17];
        t[0] = (TextView) findViewById(R.id.day1);
        t[1] = (TextView) findViewById(R.id.day2);
        t[2] = (TextView) findViewById(R.id.day3);
        t[3] = (TextView) findViewById(R.id.day4);
        t[4] = (TextView) findViewById(R.id.day5);
//        t[5] = (TextView) findViewById(R.id.day6);
//        t[6] = (TextView) findViewById(R.id.day7);
//        t[7] = (TextView) findViewById(R.id.day8);
//        t[8] = (TextView) findViewById(R.id.day9);
//        t[9] = (TextView) findViewById(R.id.day10);
//        t[10] = (TextView) findViewById(R.id.day11);
//        t[11] = (TextView) findViewById(R.id.day12);
//        t[12] = (TextView) findViewById(R.id.day13);
//        t[13] = (TextView) findViewById(R.id.day14);
//        t[14] = (TextView) findViewById(R.id.day15);
//        t[15] = (TextView) findViewById(R.id.day16);
//        t[16] = (TextView) findViewById(R.id.day17);

        TextView d[] = new TextView[17];
        d[0] = (TextView) findViewById(R.id.ders1);
        d[1] = (TextView) findViewById(R.id.ders2);
        d[2] = (TextView) findViewById(R.id.ders3);
        d[3] = (TextView) findViewById(R.id.ders4);
        d[4] = (TextView) findViewById(R.id.ders5);
        d[5] = (TextView) findViewById(R.id.ders6);
//        d[6] = (TextView) findViewById(R.id.ders7);
//        d[7] = (TextView) findViewById(R.id.ders8);
//        d[8] = (TextView) findViewById(R.id.ders9);
//        d[9] = (TextView) findViewById(R.id.ders10);
//        d[10] = (TextView) findViewById(R.id.ders11);
//        d[11] = (TextView) findViewById(R.id.ders12);
//        d[12] = (TextView) findViewById(R.id.ders13);
//        d[13] = (TextView) findViewById(R.id.ders14);
//        d[14] = (TextView) findViewById(R.id.ders15);
//        d[15] = (TextView) findViewById(R.id.ders16);
//        d[16] = (TextView) findViewById(R.id.ders17);

        TextView dvm[] = new TextView[17];
        dvm[0] = (TextView) findViewById(R.id.dvm1);
        dvm[1] = (TextView) findViewById(R.id.dvm2);
        dvm[2] = (TextView) findViewById(R.id.dvm3);
        dvm[3] = (TextView) findViewById(R.id.dvm4);
        dvm[4] = (TextView) findViewById(R.id.dvm5);
//        dvm[5] = (TextView) findViewById(R.id.dvm6);
//        dvm[6] = (TextView) findViewById(R.id.dvm7);
//        dvm[7] = (TextView) findViewById(R.id.dvm8);
//        dvm[8] = (TextView) findViewById(R.id.dvm9);
//        dvm[9] = (TextView) findViewById(R.id.dvm10);
//        dvm[10] = (TextView) findViewById(R.id.dvm11);
//        dvm[11] = (TextView) findViewById(R.id.dvm12);
//        dvm[12] = (TextView) findViewById(R.id.dvm13);
//        dvm[13] = (TextView) findViewById(R.id.dvm14);
//        dvm[14] = (TextView) findViewById(R.id.dvm15);
//        dvm[15] = (TextView) findViewById(R.id.dvm16);
//        dvm[16] = (TextView) findViewById(R.id.dvm17);

        TextView dvmTop[] = new TextView[17];
        dvmTop[0] = (TextView) findViewById(R.id.dvmTop1);
        dvmTop[1] = (TextView) findViewById(R.id.dvmTop2);
        dvmTop[2] = (TextView) findViewById(R.id.dvmTop3);
        dvmTop[3] = (TextView) findViewById(R.id.dvmTop4);
        dvmTop[4] = (TextView) findViewById(R.id.dvmTop5);
//        dvmTop[5] = (TextView) findViewById(R.id.dvmTop6);
//        dvmTop[6] = (TextView) findViewById(R.id.dvmTop7);
//        dvmTop[7] = (TextView) findViewById(R.id.dvmTop8);
//        dvmTop[8] = (TextView) findViewById(R.id.dvmTop9);
//        dvmTop[9] = (TextView) findViewById(R.id.dvmTop10);
//        dvmTop[10] = (TextView) findViewById(R.id.dvmTop11);
//        dvmTop[11] = (TextView) findViewById(R.id.dvmTop12);
//        dvmTop[12] = (TextView) findViewById(R.id.dvmTop13);
//        dvmTop[13] = (TextView) findViewById(R.id.dvmTop14);
//        dvmTop[14] = (TextView) findViewById(R.id.dvmTop15);
//        dvmTop[15] = (TextView) findViewById(R.id.dvmTop16);
//        dvmTop[16] = (TextView) findViewById(R.id.dvmTop17);

        shared_pref preference;
        String[] dersAdiAr = null;
        String[] dersSaatiAr = null;
        String[] dvmm = new String[17];
        for(int i=0; i<17; i++){
            dvmm[i] = "0 /";
        }
        int i=0;
        preference = new shared_pref();
        if(preference.isExist(c1, "Pazartesi")){
            Set<String> s1 = preference.getStringSet(c1, "Pazartesi");
            Set<String> s2 = preference.getStringSet(c1, "Pazartesi_Saat");
            Set<String> s3 = preference.getStringSet(c1, "all_dev");
            dersAdiAr = s1.toArray(new String[s1.size()]);
            dersSaatiAr = s2.toArray(new String[s2.size()]);
            if(preference.isExist(c1, "all_dev")) dvmm = s3.toArray(new String[s3.size()]);
            for (i=0; i<dersAdiAr.length && i<dersSaatiAr.length; i++){
                if(dersAdiAr[i] == null) continue;
                t[i].setText("Pazartesi: " + dersSaatiAr[i]);
                d[i].setText(dersAdiAr[i]);
                dvm[i].setText(dvmm[i]);
                dvmTop[i].setText("14");
            }
        }
        if(preference.isExist(c1, "Sali")){
            Set<String> s1 = preference.getStringSet(c1, "Sali");
            Set<String> s2 = preference.getStringSet(c1, "Sali_Saat");
            Set<String> s3 = preference.getStringSet(c1, "all_dev");
            dersAdiAr = s1.toArray(new String[s1.size()]);
            dersSaatiAr = s2.toArray(new String[s2.size()]);
            if(preference.isExist(c1, "all_dev")) dvmm = s3.toArray(new String[s3.size()]);
            for (; i<dersAdiAr.length && i<dersSaatiAr.length; i++){
                if(dersAdiAr[i] == null) continue;
                t[i].setText("Sali: " + dersSaatiAr[i]);
                d[i].setText(dersAdiAr[i]);
                dvm[i].setText(dvmm[i]);
                dvmTop[i].setText("14");
            }
        }
        if(preference.isExist(c1, "Carsamba")){
            Set<String> s1 = preference.getStringSet(c1, "Carsamba");
            Set<String> s2 = preference.getStringSet(c1, "Carsamba_Saat");
            Set<String> s3 = preference.getStringSet(c1, "all_dev");
            dersAdiAr = s1.toArray(new String[s1.size()]);
            dersSaatiAr = s2.toArray(new String[s2.size()]);
            if(preference.isExist(c1, "all_dev")) dvmm = s3.toArray(new String[s3.size()]);
            for (; i<dersAdiAr.length && i<dersSaatiAr.length; i++){
                if(dersAdiAr[i] == null) continue;
                t[i].setText("Carsamba: " + dersSaatiAr[i]);
                d[i].setText(dersAdiAr[i]);
                dvm[i].setText(dvmm[i]);
                dvmTop[i].setText("14");
            }
        }
        if(preference.isExist(c1, "Persembe")){

        }
        if(preference.isExist(c1, "Cuma")){

        }
        if(intent.getExtras() != null){
            String day = intent.getStringExtra("false");
            String ders = intent.getStringExtra("false2");
            for (int j=0; j<i; j++){
                String compare_ders = d[j].getText().toString();
                String debug = t[j].getText().toString();
                if(debug.equals("")) continue;
                String debug2 = dvm[j].getText().toString().substring(0, dvm[j].getText().toString().length()-2);
                String compare_day = t[j].getText().toString().substring(0, day.length());
                if(compare_ders.equals(ders) && compare_day.equals(day)){
                    int k = Integer.parseInt(debug2) + 1;
                    String m = Integer.toString(k);
                    dvm[j].setText(m + " /");
                    dvmm[j] = m;
                    Set<String> setOfDvm = new HashSet<>(Arrays.asList(dvmm));
                    preference.save(c1, "all_dev", setOfDvm);
                }
            }

        }
    }
}