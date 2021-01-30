package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

public class toastMessage {
    static void tM(String message, Context c1){
        Toast.makeText(c1, message, Toast.LENGTH_SHORT).show();
    }
}
