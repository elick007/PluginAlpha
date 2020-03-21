package com.elick.pluginlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.elick.pluginlib.context.CustomContextWrapper;
import com.elick.pluginlib.context.PluginContext;

public class ProxyActivity extends FragmentActivity {
    private static final String TAG = "ProxyActivity";
    IActivityInterface iActivityInterface;
    private String clsName;
    private CustomContextWrapper pluginContext;

    public ProxyActivity() {
    }

    protected void attachBaseContext(Context var1) {
        super.attachBaseContext(var1);
        PluginLog.log("ProxyActivity", "attachBaseContext");
    }

    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        PluginLog.log("ProxyActivity", "onCreate");
        this.clsName = this.getIntent().getStringExtra("ty_plugin_cls_name");

        try {
            Object var2 = Reflector.on(this.clsName, true, this.getClassLoader()).constructor(new Class[0]).newInstance(new Object[0]);
            this.iActivityInterface = (IActivityInterface)var2;
        } catch (Reflector.ReflectedException var3) {
            var3.printStackTrace();
        }

        if (this.iActivityInterface == null) {
            PluginLog.warningLog("ProxyActivity", "load IActivityInterface was fail");
            this.finish();
        }

        this.iActivityInterface.attch(this);
        this.pluginContext = new PluginContext(this);
        this.iActivityInterface.attachBaseContext(this.pluginContext);
        ActivityInfo var4 = PluginManager.getInstance().getActivityByName(this.clsName);
        if (var4 != null) {
            this.changeActivityInfo(this, var4);
        }

        this.iActivityInterface.onCreate(var1);

    }

    protected void onResume() {
        super.onResume();
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onResume();
        }

    }

    protected void onStart() {
        super.onStart();
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onStart();
        }

    }

    protected void onPause() {
        super.onPause();
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onPause();
        }

    }

    protected void onStop() {
        super.onStop();
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onStop();
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onDestroy();
        }

    }

    protected void onSaveInstanceState(Bundle var1) {
        super.onSaveInstanceState(var1);
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onSaveInstanceState(var1);
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onConfigurationChanged(var1);
        }

    }

    public void onRequestPermissionsResult(int var1,  String[] var2,  int[] var3) {
        super.onRequestPermissionsResult(var1, var2, var3);
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onRequestPermissionsResult(var1, var2, var3);
        }

    }

    protected void onNewIntent(Intent var1) {
        super.onNewIntent(var1);
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onNewIntent(var1);
        }

    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (this.iActivityInterface != null) {
            this.iActivityInterface.onActivityResult(var1, var2, var3);
        }

    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onRestoreInstanceState(Bundle var1) {
        super.onRestoreInstanceState(var1);
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        return super.onOptionsItemSelected(var1);
    }

    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    private void changeActivityInfo(Activity var1, ActivityInfo var2) {
        //代理activity ActivityInfo
        ActivityInfo var3 = null;

        try {
            var3 = (ActivityInfo)Reflector.with(var1).field("mActivityInfo").get();
        } catch (Reflector.ReflectedException var5) {
            var5.printStackTrace();
        }

        if (null != var2) {
            var2.applicationInfo = PluginManager.getInstance().getPackageInfo().applicationInfo;
            if (var3 != null) {
                //hook代理activity,将插件activity的activityInfo设置到代理activity
                var3.applicationInfo = var2.applicationInfo;
                var3.configChanges = var2.configChanges;
                var3.descriptionRes = var2.descriptionRes;
                var3.enabled = var2.enabled;
                var3.exported = var2.exported;
                var3.flags = var2.flags;
                var3.icon = var2.icon;
                var3.labelRes = var2.labelRes;
                var3.logo = var2.logo;
                var3.metaData = var2.metaData;
                var3.name = var2.name;
                var3.nonLocalizedLabel = var2.nonLocalizedLabel;
                var3.packageName = var2.packageName;
                var3.permission = var2.permission;
                var3.screenOrientation = var2.screenOrientation;
                setRequestedOrientation(var2.screenOrientation);
                var3.softInputMode = var2.softInputMode;
                var3.targetActivity = var2.targetActivity;
                var3.taskAffinity = var2.taskAffinity;
                var3.theme = var2.theme;
            }
        }

        if (null != var3) {
            if (var3.nonLocalizedLabel != null) {
                var1.setTitle(var3.nonLocalizedLabel);
            } else if (var3.labelRes != 0) {
                var1.setTitle(var3.labelRes);
            } else if (var3.applicationInfo != null) {
                if (var3.applicationInfo.nonLocalizedLabel != null) {
                    var1.setTitle(var3.applicationInfo.nonLocalizedLabel);
                } else if (var3.applicationInfo.labelRes != 0) {
                    var1.setTitle(var3.applicationInfo.labelRes);
                } else {
                    var1.setTitle(var3.applicationInfo.packageName);
                }
            }
        }

        if (null != var2) {
            this.getWindow().setSoftInputMode(var2.softInputMode);
            PluginLog.log("ProxyActivity", "changeActivityInfo->changeTheme:  theme = " + var2.getThemeResource() + ", icon = " + var2.getIconResource() + ", logo = " + var2.logo + ", labelRes" + var2.labelRes);
        }

    }
}
