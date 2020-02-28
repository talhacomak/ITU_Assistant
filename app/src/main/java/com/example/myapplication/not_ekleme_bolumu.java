package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.MainActivity.Contact_Request;

public class not_ekleme_bolumu extends AppCompatActivity {
    Context c1 = this;
    int k = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_ekleme_bolumu);
        Intent intent = getIntent();
        if(intent.getStringExtra("not") != null){
            TextView text = (TextView) findViewById(R.id.text);
            text.setText(intent.getStringExtra("not"));
            k = intent.getIntExtra("k",-1);
        }

        Button btn = (Button) findViewById(R.id.kaydet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.text);
                String str = text.getText().toString();
                Intent intent = new Intent(c1, not_ekle.class);
                intent.putExtra("not", str);
                intent.putExtra("k", k);
                startActivityForResult(intent, Contact_Request);
            }
        });
    }

    @Override
    public void onBackPressed(){
        TextView text = (TextView) findViewById(R.id.text);
        String str = text.getText().toString();
        Intent intent = new Intent(c1, not_ekle.class);
        intent.putExtra("not", str);
        intent.putExtra("k", k);
        startActivityForResult(intent, Contact_Request);
    }
}
