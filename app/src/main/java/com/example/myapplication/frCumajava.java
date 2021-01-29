package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frCumajava extends Fragment {

    int minT[] = new int[10];
    String days_ar[] = new String[10];
    String strArray[] = new String[10];
    String dersAdiAr[] = new String[10];

    public frCumajava(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cuma, container, false);
        TextView t[] = new TextView[10];
        t[0] = (TextView) view.findViewById(R.id.saat1);
        t[1] = (TextView) view.findViewById(R.id.saat2);
        t[2] = (TextView) view.findViewById(R.id.saat3);
        t[3] = (TextView) view.findViewById(R.id.saat4);
        t[4] = (TextView) view.findViewById(R.id.saat5);
        t[5] = (TextView) view.findViewById(R.id.saat6);
        t[6] = (TextView) view.findViewById(R.id.saat7);
        t[7] = (TextView) view.findViewById(R.id.saat8);
        t[8] = (TextView) view.findViewById(R.id.saat9);
        t[9] = (TextView) view.findViewById(R.id.saat10);

        TextView d[] = new TextView[10];
        d[0] = (TextView) view.findViewById(R.id.ders1);
        d[1] = (TextView) view.findViewById(R.id.ders2);
        d[2] = (TextView) view.findViewById(R.id.ders3);
        d[3] = (TextView) view.findViewById(R.id.ders4);
        d[4] = (TextView) view.findViewById(R.id.ders5);
        d[5] = (TextView) view.findViewById(R.id.ders6);
        d[6] = (TextView) view.findViewById(R.id.ders7);
        d[7] = (TextView) view.findViewById(R.id.ders8);
        d[8] = (TextView) view.findViewById(R.id.ders9);
        d[9] = (TextView) view.findViewById(R.id.ders10);


        int say, say2, counter=0;

        for(say=0; say<10; say++){
            minT[say] = -1;
        }

        int hour=0, min=0;
        String dersAdi;
        if(getArguments() != null){

            Bundle b3 = getArguments();
            String newDersadiAr[] = b3.getStringArray("dersAdiAr");
            for(int i=0; i<newDersadiAr.length; i++){
                dersAdiAr[i] = newDersadiAr[i];
            }
            String newDayAr[] = b3.getStringArray("days");
            for(int i=0; i<newDayAr.length; i++) {
                days_ar[i] = newDayAr[i];
            }
            hour = b3.getInt("hour");
            min = b3.getInt("min");
            minT = b3.getIntArray("array");
            String newStrAr[] = b3.getStringArray("strArray");
            for(int i=0; i<newStrAr.length; i++) {
                strArray[i] = newStrAr[i];
            }
            dersAdi = b3.getString("dersAdi");

            int minTotal = 60*hour + min;
            for(say=0; say<10; say++){
                if(minT[say] == -1 || (minT[say] > minTotal)){
                    for(say2 = say; say2<10; say2++){
                        if(minT[say2] > -1){
                            counter++;
                        }
                        else break;
                    }
                    for(say2=say+counter; say2>say; say2--){
                        minT[say2] = minT[say2-1];
                        days_ar[say2] = days_ar[say2-1];
                        strArray[say2] = strArray[say2-1];
                        dersAdiAr[say2] = strArray[say2-1];
                    }
                    break;
                }
            }
            if(say == 10){
                //ekrana yazdır (yok artık! kaç dersin var?)
                //try catch?
            }
            minT[say] = minTotal;
            strArray[say] = (hour + " : " + min);
            dersAdiAr[say] = dersAdi;
            days_ar[say] = "Cuma";

            Set<String> mySet = new HashSet<>(Arrays.asList(dersAdiAr));
            Set<String> mySet2 = new HashSet<>(Arrays.asList(strArray));
            shared_pref share = new shared_pref();
            share.remove(getActivity(), "days");
            share.remove(getActivity(), "Cuma");
            share.remove(getActivity(), "Cuma_Saat");
            share.save(getActivity(), "days", days_ar);
            share.save(getActivity(), "Cuma", mySet);
            share.save(getActivity(), "Cuma_Saat", mySet2);

        }
        else{
            shared_pref sharenew = new shared_pref();
            days_ar = sharenew.getStringArray(getActivity(),"days");
            Set<String> dersAdiSet = sharenew.getStringSet(getActivity(), "Cuma");
            Set<String> dersSaatiSet = sharenew.getStringSet(getActivity(), "Cuma"+"_Saat");
            if(dersAdiSet != null) dersAdiAr = dersAdiSet.toArray(new String[dersAdiSet.size()]);
            if(dersSaatiSet != null) strArray = dersSaatiSet.toArray(new String[dersSaatiSet.size()]);
        }


        for(int i=0; i<strArray.length; i++){
            if(strArray[i] == null || !days_ar[i].equals("Cuma")) continue;
            t[i].setText(strArray[i]);
            d[i].setText(dersAdiAr[i]);
        }

        for(say=0; say<10; say++){
            if(t[say].getText().toString() != ""){
                String str[] = (t[say].getText().toString()).split(" : ");
                minT[say] = Integer.parseInt(str[0])*60 + Integer.parseInt(str[1]);
            }
            else minT[say] = -1;
        }


        Button btnnew = (Button) view.findViewById(R.id.eklemeButonu);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ekleme_sayfasi.class);
                in.putExtra("day", "Cuma");
                in.putExtra("array", minT);
                in.putExtra("strArray", strArray);
                in.putExtra("dersAdiAr", dersAdiAr);
                in.putExtra("days", days_ar);
                startActivity(in);
            }
        });

        return view;
    }


}