package com.example.myapplication;

public class convert_day_to_number {
    public static int convert(String day){
        int selection = -1;
        if(day != null){
            switch (day){
                case "Monday" : selection = 0;
                    break;
                case "Tuesday" : selection = 1;
                    break;
                case "Wednesday" : selection = 2;
                    break;
                case "Thursday" : selection = 3;
                    break;
                case "Friday" : selection = 4;
                    break;
                default:
                    break;
            }
        }
        return selection;
    }
}
