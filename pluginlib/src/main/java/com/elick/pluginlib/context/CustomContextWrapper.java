package com.elick.pluginlib.context;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.elick.pluginlib.PluginManager;

public abstract class CustomContextWrapper extends ContextWrapper {
    private static final String TAG = "CustomContextWrapper";
    private ApplicationInfo mApplicationInfo = null;

    public CustomContextWrapper(Context var1) {
        super(var1);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    @Override
    public String getPackageName() {
        return PluginManager.getInstance().getPackageName();
    }
}
