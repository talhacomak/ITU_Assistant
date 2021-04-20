package com.example.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {
    public static String key_update_enable = "is_update";
    public static String key_update_version = "version";
    public static String key_update_url = "update_url";


    private Context context;
    private OnUpdateCheckListener onUpdateCheckListener;

    public UpdateHelper(Context context, OnUpdateCheckListener onUpdateCheckListener){
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    public void check(){
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if(remoteConfig.getBoolean(key_update_enable)){
            String current_version = remoteConfig.getString(key_update_version);
            String app_version = getAppVersion(context);
            String update_url = remoteConfig.getString(key_update_url);
            if(!TextUtils.equals(current_version, app_version) && onUpdateCheckListener != null){
                onUpdateCheckListener.onUpdateCheckListener(update_url);
            }
        }
    }

    private String getAppVersion(Context context) {
        String result = "";
        try {
            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        }
        catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return result;
    }

    public interface OnUpdateCheckListener{
        void onUpdateCheckListener(String urlApp);
    }

    public static Builder with(Context context){
        return  new Builder(context);
    }

    public static class Builder{
        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(OnUpdateCheckListener onUpdateCheckListener){
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }

        public UpdateHelper build(){
            return new UpdateHelper(context, onUpdateCheckListener);
        }

        public UpdateHelper check(){
            UpdateHelper updateHelper = build();
            updateHelper.check();
            return updateHelper;
        }
    }
}
