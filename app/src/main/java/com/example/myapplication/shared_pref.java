package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class shared_pref {
    static final String PREF_NAME = "Dosya5";
    public void save(Context context, String PREF_KEY, String VALUE){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_KEY, VALUE);
        editor.commit();
    }

    public void save(Context context, String PREF_KEY, int ARRAY[]){
        if(ARRAY.length == 0) return;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String str[] = new String[ARRAY.length];
        for(int i=0; i<ARRAY.length; i++){
            str[i] = Integer.toString(ARRAY[i]);
        }
        Set<String> STR = new HashSet<>(Arrays.asList(str));
        editor.putStringSet(PREF_KEY, STR);
        editor.commit();
    }

    public void save(Context context, String PREF_KEY, int VALUE){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREF_KEY, VALUE);
        editor.commit();
    }

    public void save(Context context, String PREF_KEY, String AR_NAME[]){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Set<String> STR = new HashSet<>(Arrays.asList(AR_NAME));
        editor.putStringSet(PREF_KEY, STR);
    }

    public void save(Context context, String PREF_KEY, Set<String> STR){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREF_KEY, STR);
        editor.commit();
    }

    public Set<String> getStringSet(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> mySet = settings.getStringSet(PREF_KEY,null);
        return mySet;
    }

    public String getValue(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String text = settings.getString(PREF_KEY,null);
        return text;
    }

    public int[] Ar(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> str = settings.getStringSet(PREF_KEY, null);
        String Array[];
        Set<String> mySet = settings.getStringSet(PREF_KEY,null);
        if(mySet != null) Array = mySet.toArray(new String[mySet.size()]);
        else return null;
        int result[] = new int[Array.length];
        for (int i=0; i<Array.length; i++){
            result[i] = Integer.parseInt(Array[i]);
        }
        return result;
    }

    public int getValueInt(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int text = settings.getInt(PREF_KEY,0);
        return text;
    }

    public String[] getStringArray(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> mySet = settings.getStringSet(PREF_KEY,null);
        String Array[];
        if(mySet != null) Array = mySet.toArray(new String[mySet.size()]);
        else Array = null;
        return Array;
    }

    public void clear(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(PREF_KEY);
        editor.commit();
    }

    public boolean isExist(Context context, String PREF_KEY){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean x = settings.contains(PREF_KEY);
        return  x;
    }
}
