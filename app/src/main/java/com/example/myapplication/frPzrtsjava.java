package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class frPzrtsjava extends Fragment  {
    int minT[] = new int[10];
    String days_ar[] = new String[10];
    String strArray[] = new String[10];
    String dersAdiAr[] = new String[10];

    public frPzrtsjava(){

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pztsi, container, false);

        TextView t[] = new TextView[10];
        t[0] = view.findViewById(R.id.saat1);
        t[1] = view.findViewById(R.id.saat2);
        t[2] = view.findViewById(R.id.saat3);
        t[3] = view.findViewById(R.id.saat4);
        t[4] = view.findViewById(R.id.saat5);
        t[5] = view.findViewById(R.id.saat6);
        t[6] = view.findViewById(R.id.saat7);
        t[7] = view.findViewById(R.id.saat8);
        t[8] = view.findViewById(R.id.saat9);
        t[9] = view.findViewById(R.id.saat10);

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
        for(say=0; say<10; say++){
            days_ar[say] = "-";
        }

        for(say=0; say<10; say++){
            strArray[say] = "-";
        }

        for(say=0; say<10; say++){
            dersAdiAr[say] = "-";
        }


        FileProcess fp = new FileProcess();
        String txt = fp.readFromFile(getActivity(), "Pazartesix");
        if (!txt.equals("") ){
            String k[] = txt.split(" \\| ");
            for (int i= 0; i< k.length; i++){
                dersAdiAr[i] = k[i];
            }
        }
        String txt2 = fp.readFromFile(getActivity(), "Pazartesi" + "_Saatx");
        if (!txt2.equals("") ){
            String k[] = txt2.split(" \\| ");
            for (int i= 0; i< k.length; i++){
                strArray[i] = k[i];
            }
        }
        String txt3 = fp.readFromFile(getActivity(), "daysx");
        if (!txt3.equals("") ){
            String k[] = txt3.split(" \\| ");
            for (int i= 0; i< k.length; i++){
                days_ar[i] = k[i];
            }
        }
        String txt4 = fp.readFromFile(getActivity(), "minTx");
        if (!txt4.equals("")){
            String k[] = txt4.split(" \\| ");
            for (int i= 0; i< k.length; i++){
                minT[i] = Integer.parseInt(k[i]);
            }
        }

        int hour=0, min=0;
        String dersAdi = "Pazartesi";
        if(getArguments() != null){

            Bundle b3 = getArguments();
            hour = b3.getInt("hour");
            min = b3.getInt("min");
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
            days_ar[say] = "Pazartesi";

            String text="";
            for (int i=0; i<dersAdiAr.length; i++){
                if(dersAdiAr[i].equals("-")) continue;
                text += (dersAdiAr[i] + " | ");
            }
            fp.writeToFile(text, getActivity(), "Pazartesix");

            text = "";
            for (int i=0; i<strArray.length; i++){
                if(strArray[i].equals("-")) continue;
                text += strArray[i] + " | ";
            }
            fp.writeToFile(text, getActivity(), "Pazartesi_Saatx");

            text = "";
            for (int i=0; i<days_ar.length; i++){
                if(days_ar[i].equals("-")) continue;
                text += days_ar[i] + " | ";
            }
            fp.writeToFile(text, getActivity(), "daysx");

            text = "";
            for (int i=0; i<minT.length; i++){
                if(minT[i] == -1) continue;
                text += (minT[i] + " | ");
            }
            fp.writeToFile(text, getActivity(), "minTx");

        }



        for(int i=0; i<strArray.length; i++){
            if(strArray[i].equals("-") || !days_ar[i].equals("Pazartesi")) continue;
            t[i].setText(strArray[i]);
            d[i].setText(dersAdiAr[i]);
        }




        Button btnnew = (Button) view.findViewById(R.id.eklemeButonu);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ekle_pzrtesi_fra.class);
                in.putExtra("day", "Pazartesi");
                startActivity(in);
            }
        });

        return view;
    }


}
