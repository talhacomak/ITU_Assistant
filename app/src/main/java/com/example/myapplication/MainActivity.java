package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button ekleCikar;
    Button devamSiz;
    Button not;
    Button ayarlar;
    Context c1 = this;
    static final int Contact_Request = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        String day;
        switch (day_of_week){
            case 2:
                day = "Pazartesi";
                break;
            case 3:
                day = "Sali";
                break;
            case 1:
                day = "Carsamba";
                break;
            case 5:
                day = "Persembe";
                break;
            case 6:
                day = "Cuma";
                break;
            default:
                day = "noday";
                break;
        }

        shared_pref sharenew = new shared_pref();
        Set<String> dersAdi = sharenew.getStringSet(c1, day);
        Set<String> dersSaati = sharenew.getStringSet(c1, day+"_Saat");
        Toast.makeText(c1, "asfd", Toast.LENGTH_LONG);
        if (sharenew.isExist(c1, "day") && sharenew.isExist(c1, day) ){
            final String[] dersAdiAr = dersAdi.toArray(new String[dersAdi.size()]);
            String[] dersSaatiAr = dersSaati.toArray(new String[dersSaati.size()]);
            for (int i=0; i<dersAdiAr.length && i<dersSaatiAr.length; i++){
                if(dersAdiAr[i] == null) continue;

                LinearLayout layout = (LinearLayout) findViewById(R.id.derslayout);

                LinearLayout linNew = new LinearLayout(this);
                linNew.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linNew.setId(i);
                linNew.setOrientation(LinearLayout.HORIZONTAL);
                //int id = linNew.getId();
                layout.addView(linNew);

                TextView newDersSaati = new TextView(this);
                newDersSaati.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                newDersSaati.setText(dersSaatiAr[i]);
                newDersSaati.setGravity(Gravity.CENTER);
                newDersSaati.setTextSize(20);
                newDersSaati.setTextColor(ContextCompat.getColor(c1, R.color.black));
                linNew.addView(newDersSaati);

                TextView newDers = new TextView(this);
                newDers.setLayoutParams(new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT));
                newDers.setText(dersAdiAr[i]);
                newDers.setGravity(Gravity.CENTER);
                newDers.setTextSize(20);
                newDers.setTextColor(ContextCompat.getColor(c1, R.color.black));
                linNew.addView(newDers);

                //RelativeLayout rl = new RelativeLayout(this);
                //rl.setLayoutParams();

                //LinearLayout lay = (LinearLayout) findViewById(id);

                RadioGroup rdgr = new RadioGroup(this);
                rdgr.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                rdgr.setOrientation(LinearLayout.HORIZONTAL);
                rdgr.setGravity(Gravity.RIGHT);
                rdgr.setBackgroundColor(ContextCompat.getColor(c1, R.color.gri));
                linNew.addView(rdgr);

                RadioButton rb1 = new RadioButton(this);
                rb1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rb1.setGravity(Gravity.CENTER);
                rdgr.addView(rb1);

                RadioButton rb2 = new RadioButton(this);
                rb2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rb2.setGravity(Gravity.CENTER);
                rb2.setTextColor(ContextCompat.getColor(c1, R.color.black));
                final int j = i;
                final String str = day;
                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(c1, devamsizliklar.class);
                        intent.putExtra("false", str);
                        intent.putExtra("false2", dersAdiAr[j]);
                        startActivityForResult(intent, Contact_Request);
                    }
                });
                rdgr.addView(rb2);

            }
        }



        ayarlar = (Button) findViewById(R.id.button3);
        devamSiz = (Button) findViewById(R.id.button2);
        ekleCikar = (Button) findViewById(R.id.button);
        Button gorev = (Button) findViewById(R.id.button5);
        not = (Button) findViewById(R.id.button4);

        ekleCikar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, ekle_cikar.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        devamSiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, devamsizliklar.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, not_ekle.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        ayarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, ayarlar.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
        gorev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, gorev_ekle.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
    }

    public void open_ders(View view){
        Intent intent = new Intent(c1, ekle_cikar.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_dvm(View view){
        Intent intent = new Intent(c1, devamsizliklar.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_notlar(View view){
        Intent intent = new Intent(c1, not_ekle.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_settings(View view){
        Intent intent = new Intent(c1, ayarlar.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_calender(View view){
        Intent intent = new Intent(c1, gorev_ekle.class);
        startActivityForResult(intent, Contact_Request);
    }

    public void open_GPS(View view){

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
