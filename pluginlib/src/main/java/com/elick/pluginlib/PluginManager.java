package com.elick.pluginlib;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static PluginManager _instance;
    private DexClassLoader dexClassLoader;
    private Resources resources;
    private Context context = TYPlugin.getApplicationContext();
    private PackageInfo packageInfo;
    private String pluginCls;
    private Executor mExecutor = Executors.newCachedThreadPool();

    public static PluginManager getInstance() {
        if (_instance==null){
            synchronized(PluginManager.class) {
                if (_instance == null) {
                    synchronized(PluginManager.class) {
                        _instance = new PluginManager();
                    }
                }
            }
        }
        return _instance;
    }

    private PluginManager() {
    }

    public void loadDex(String var1) {
        File var2 = PluginInstaller.getPluginRootpath(this.context);
        this.dexClassLoader = new DexClassLoader(var1, var2.getAbsolutePath(), (String)null, this.context.getClassLoader());
        this.packageInfo = this.context.getPackageManager().getPackageArchiveInfo(var1, PackageManager.GET_ACTIVITIES);
        Bundle var3;
        if (null != this.packageInfo) {
            var3 = this.packageInfo.applicationInfo.metaData;
            if (var3 != null) {
                String var4 = var3.getString("TY_INF");
                if (var4 != null) {
                    this.pluginCls = var4;
                }
            }
        }

        var3 = null;
        Reflector var8 = Reflector.on(AssetManager.class);

        try {
            AssetManager var7 = (AssetManager)var8.constructor(new Class[0]).newInstance(new Object[0]);
            var8.method("addAssetPath", new Class[]{String.class}).callByCaller(var7, new Object[]{var1});
            DisplayMetrics var5 = this.context.getResources().getDisplayMetrics();
            Log.e("lala", var5.toString());
            this.resources = new Resources(var7, var5, this.context.getResources().getConfiguration());
        } catch (Reflector.ReflectedException var6) {
            var6.printStackTrace();
        }

    }

    public void exec(Runnable var1) {
        this.mExecutor.execute(var1);
    }

    public ActivityInfo getActivityByName(String var1) {
        if (TextUtils.isEmpty(var1)) {
            return null;
        } else {
            if (this.packageInfo != null) {
                ActivityInfo[] var2 = this.packageInfo.activities;
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    ActivityInfo var5 = var2[var4];
                    if (var1.equals(var5.name)) {
                        return var5;
                    }
                }
            }

            return null;
        }
    }

    public DexClassLoader getDexClassLoader() {
        return this.dexClassLoader;
    }

    public Resources getResources() {
        return this.resources;
    }

    public PackageInfo getPackageInfo() {
        return this.packageInfo;
    }

    public String getPackageName(){
        return this.packageInfo.packageName;
    }

    public String getPluginCls() {
        return this.pluginCls;
    }
}

