package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmIntent extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Intent in = new Intent(context, add_activity.class);
        in.putExtras(intent.getExtras());
        in.putExtra("alarm", "alarm");
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }

}
