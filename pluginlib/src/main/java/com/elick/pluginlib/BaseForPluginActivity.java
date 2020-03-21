package com.elick.pluginlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

public class BaseForPluginActivity extends FragmentActivity implements IActivityInterface {
    private static String TAG = BaseForPluginActivity.class.getName();
    protected Activity mHost;

    public BaseForPluginActivity() {
    }

    public Window getWindow() {
        return this.mHost == null ? super.getWindow() : this.mHost.getWindow();
    }

    public void setContentView(int var1) {
        if (this.mHost == null) {
            super.setContentView(var1);
        } else {
            this.mHost.setContentView(var1);
        }

    }

    public <T extends View> T findViewById(int var1) {
        return (T) (this.mHost == null ? super.findViewById(var1) : this.mHost.findViewById(var1));
    }

    public Resources getResources() {
        return this.mHost == null ? super.getResources() : this.mHost.getResources();
    }

    public LayoutInflater getLayoutInflater() {
        return this.mHost != null ? super.getLayoutInflater() : this.mHost.getLayoutInflater();
    }

    public WindowManager getWindowManager() {
        return this.mHost == null ? super.getWindowManager() : this.mHost.getWindowManager();
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mHost != null ? ((FragmentActivity)this.mHost).getSupportFragmentManager() : super.getSupportFragmentManager();
    }

    public Object getSystemService(String var1) {
        return this.mHost != null ? this.mHost.getSystemService(var1) : super.getSystemService(var1);
    }

    public Intent getIntent() {
        return this.mHost != null ? this.mHost.getIntent() : super.getIntent();
    }

    public void setIntent(Intent var1) {
        if (this.mHost != null) {
            this.mHost.setIntent(var1);
        } else {
            super.setIntent(var1);
        }

    }

    public AssetManager getAssets() {
        return this.mHost != null ? this.mHost.getAssets() : super.getAssets();
    }

    public void setTheme(int var1) {
        if (this.mHost != null) {
            this.mHost.setTheme(var1);
        } else {
            super.setTheme(var1);
        }

    }

    public File getCacheDir() {
        return super.getCacheDir();
    }

    public void finish() {
        if (this.mHost == null) {
            super.finish();
        } else {
            this.mHost.finish();
        }

    }
    @Override
    public void startActivity(Intent var1) {
        if (this.mHost == null) {
            super.startActivity(var1);
        } else {
            ComponentName var2 = var1.getComponent();
            PluginLog.runtimeLog(TAG, "startActivityForResult");
            if (var2 != null) {
                PluginLog.runtimeLog(TAG, var2.getClassName());
                if (!TYGameActivity.class.getName().equals(var2.getClassName())) {
                    ComponentName var3 = new ComponentName(this.mHost, TYGameActivity.class);
                    var1.putExtra("ty_plugin_cls_name", var2.getClassName());
                    var1.setComponent(var3);
                }
            }

            this.mHost.startActivity(var1);
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent var1, int var2, Bundle var3) {
        if (this.mHost == null) {
            super.startActivityForResult(var1, var2, var3);
        } else {
            ComponentName var4 = var1.getComponent();
            if (var4 != null && !TYGameActivity.class.getName().equals(var4.getClassName())) {
                ComponentName var5 = new ComponentName(this.mHost, TYGameActivity.class);
                var1.putExtra("ty_plugin_cls_name", var4.getClassName());
                var1.setComponent(var5);
            }

            if (Build.VERSION.SDK_INT >= 16) {
                this.mHost.startActivityForResult(var1, var2, var3);
            } else {
                this.mHost.startActivityForResult(var1, var2);
            }
        }

    }

    @Override
    public void startActivityForResult(Intent var1, int var2) {
        if (this.mHost == null) {
            super.startActivityForResult(var1, var2);
        } else {
            ComponentName var3 = var1.getComponent();
            if (var3 != null && !TYGameActivity.class.getName().equals(var3.getClassName())) {
                ComponentName var4 = new ComponentName(this.mHost, TYGameActivity.class);
                var1.putExtra("ty_plugin_cls_name", var3.getClassName());
                var1.setComponent(var4);
            }

            this.mHost.startActivityForResult(var1, var2);
        }

    }

    public void onRequestPermissionsResult(int var1,  String[] var2,  int[] var3) {
        super.onRequestPermissionsResult(var1, var2, var3);
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
    }

    public void onNewIntent(Intent var1) {
    }

    public void attch(Activity var1) {
        this.mHost = var1;
    }

    /**
     *
     * @param context {@link com.elick.pluginlib.context.PluginContext 为插件activity设置context，更改插件startActivity的intent为坑位activity}
     */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (this.mHost == null) {
            super.onCreate(savedInstanceState);
        }

    }

    public void onStart() {
        if (this.mHost == null) {
            super.onStart();
        }

    }

    public void onResume() {
        if (this.mHost == null) {
            super.onResume();
        }

    }

    public void onPause() {
        if (this.mHost == null) {
            super.onPause();
        }

    }

    public void onStop() {
        if (this.mHost == null) {
            super.onStop();
        }

    }

    public void onDestroy() {
        if (this.mHost == null) {
            super.onDestroy();
        }

    }

    public void onSaveInstanceState(Bundle var1) {
        if (this.mHost == null) {
            super.onSaveInstanceState(var1);
        }

    }

    public void onBackPressed() {
        if (this.mHost == null) {
            super.onBackPressed();
        }

    }
}
