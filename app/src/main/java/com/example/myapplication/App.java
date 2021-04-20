package com.example.myapplication;

import android.app.Application;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> default_values = new HashMap<>();
        default_values.put(UpdateHelper.key_update_url, false);
        default_values.put(UpdateHelper.key_update_version, "1.0");
        default_values.put(UpdateHelper.key_update_enable, "url_for_create");
        remoteConfig.setDefaultsAsync(default_values);
        remoteConfig.fetch(60) // fetch every 60 sec
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            remoteConfig.fetchAndActivate();
                        }
                    }
                });
    }
}
