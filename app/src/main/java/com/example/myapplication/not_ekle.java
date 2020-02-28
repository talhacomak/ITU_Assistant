package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.Contact_Request;

public class not_ekle extends AppCompatActivity {
    Context c1 = this;
    String str = "123";
    shared_pref share = new shared_pref();
    int say = 0;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notekle_layout);
        Intent intent = getIntent();
        LinearLayout layout = (LinearLayout) findViewById(R.id.main);

        final Button btnTag[] = new Button[10];
        for(int j = 0; j<10; j++){
            btnTag[j] = new Button(this);
        }
        int i=0;
        if(share.isExist(c1, "say")) say = share.getValueInt(c1,"say");
        int say_son = say;
        if(say > 0){
            for(i=0; i<say; i++){
                String count = "not" + i;
                final String text = share.getValue(c1, count);

                btnTag[i] = new Button(this);
                btnTag[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                String kisa;
                str = text;
                if(str.length() > 7){
                    kisa = str.substring(0, 7);
                    kisa = str + "...";
                    btnTag[i].setText(kisa);
                }
                else btnTag[i].setText(str);
                layout.addView(btnTag[i]);

                final int p = i;
                btnTag[p].setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(c1, not_ekleme_bolumu.class);
                        intent.putExtra("not", text);
                        intent.putExtra("k", p);
                        startActivityForResult(intent, Contact_Request);
                    }
                });
            }
        }
        int k = -1;
        if(intent.getExtras() != null){
            k = intent.getIntExtra("k",0);
            if(k != -1){
                i = k;
                say = i;
            }
            btnTag[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String kisa = "123";
            str = intent.getStringExtra("not");
            if(str.length() > 7){
                kisa = str.substring(0, 7);
                kisa = kisa + "...";
                btnTag[i].setText(kisa);
            }
            else btnTag[i].setText(str);
            if(k==-1) layout.addView(btnTag[i]);
            String key = "not"+say;
            share.save(c1, key, str);
            if(k == -1){
                say++;
                share.save(c1, "say", say);
            }
            else say = say_son;

            for(i=0; i<say; i++){
                final int p = i;
                String count = "not" + i;
                final String text = share.getValue(c1, count);
                btnTag[p].setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(c1, not_ekleme_bolumu.class);
                        intent.putExtra("not", text);
                        intent.putExtra("k", p);
                        startActivityForResult(intent, Contact_Request);
                    }
                });
            }
        }

        Button ekle = (Button) findViewById(R.id.button);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c1, not_ekleme_bolumu.class);
                startActivityForResult(intent, Contact_Request);
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c1, MainActivity.class);
        startActivityForResult(intent, Contact_Request);
    }
}
