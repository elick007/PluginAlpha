package com.elick.pluginlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TYPlugin {
    public static final String TYPLUGIN_ATY_CLS_NAME = "ty_plugin_cls_name";
    private static final String TAG = "TYPLugin";
    public static final int HOST_VERSION = 1;
    @SuppressLint({"StaticFieldLeak"})
    private static Context sHostContext;

    private TYPlugin() {
    }

    public static void init(Context var0) throws Exception {
        if (var0 instanceof Application) {
            sHostContext = var0;
        } else {
            if (!(var0 instanceof Activity)) {
                throw new Exception("this context must is Application or Activity");
            }

            sHostContext = var0.getApplicationContext();
        }
        install(sHostContext);
    }

    public static void install(final Context var0) {
        new Thread(){
            @Override
            public void run() {
                ensureContext(var0);
                File pluginDir = PluginInstaller.getPluginRootpath(var0);
                File var5 = new File(pluginDir, "plugin.apk");
                if (var5.exists()) {
                    PluginLog.installLog("TYPLugin", "plugin already exit");
//            PluginLiteInfo var7 = new PluginLiteInfo();
//            var7.path = var5.getAbsolutePath();
//            var3.onPackageInstalled(var7);
                } else {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = var0.getAssets().open("plugin.apk");
                        outputStream = new FileOutputStream(var5);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                        byte[] buffer = new byte[1024];
                        int size;
                        while ((size = inputStream.read(buffer)) >= 0) {
                            bufferedOutputStream.write(buffer, 0, size);
                        }
                        bufferedOutputStream.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null)
                                inputStream.close();
                            if (outputStream != null)
                                outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//            InstallAction var6 = new InstallAction(var2, var4, var3);
//            PluginManager.getInstance().exec(var6);
                }
                PluginManager.getInstance().loadDex(var5.getAbsolutePath());
            }
        }.start();

    }

    public static boolean checkVersionPluginExit(int var0) {
        File var1 = new File(PluginInstaller.getPluginRootpath(sHostContext), "" + var0);
        if (!var1.exists()) {
            var1.mkdir();
            return false;
        } else {
            File var2 = new File(var1, "plugin.apk");
            return var2.exists();
        }
    }

    public static String getPluginByVersion(int var0) {
        File var1 = new File(PluginInstaller.getPluginRootpath(sHostContext), "" + var0);
        if (!var1.exists()) {
            return null;
        } else {
            File var2 = new File(var1, "plugin.apk");
            return var2.exists() ? var2.getAbsolutePath() : null;
        }
    }

    public static <T> T loadSdkCls() {
        String var0 = PluginManager.getInstance().getPluginCls();
        if (TextUtils.isEmpty(var0)) {
            PluginLog.warningLog("TYPLugin", "this plugin has not Load");
            return null;
        } else {
            Object var1 = Reflector.on(var0, true, getClassloader()).constructor(new Class[0]).newInstance(new Object[0]);
            return (T) var1;
        }
    }

    public static ClassLoader getClassloader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    public static Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    private static Context ensureContext(Context var0) {
        return var0 != null ? var0 : sHostContext;
    }

    public static Context getApplicationContext() {
        return sHostContext;
    }
}
