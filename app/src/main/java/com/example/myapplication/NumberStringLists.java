package com.example.myapplication;

public class NumberStringLists {
    static String[] dayNumbers;
    static String[] hourNumbers;
    static String[] minNumbers;

    NumberStringLists(){
        dayNumbers = new String[31];
        hourNumbers = new String[25];
        minNumbers = new String[60];
        for(int i=0; i<25; i++){
            dayNumbers[i] = String.valueOf(i);
            hourNumbers[i] = String.valueOf(i);
            minNumbers[i] = String.valueOf(i);
        }
        for(int i=0; i<31; i++){
            dayNumbers[i] = String.valueOf(i);
            minNumbers[i] = String.valueOf(i);
        }
        for(int i=0; i<60; i++){
            minNumbers[i] = String.valueOf(i);
        }
    }
}
