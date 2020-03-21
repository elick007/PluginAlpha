package com.elick.pluginlib;

import android.app.Application;

public class TYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            TYPlugin.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
