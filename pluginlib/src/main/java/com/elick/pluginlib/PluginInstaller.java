package com.elick.pluginlib;

import android.content.Context;

import java.io.File;

public class PluginInstaller {
    private static final String TAG = PluginInstaller.class.getName();
    public static final String SCHEME_ASSETS = "assets://";
    public static final String SCHEME_FILE = "file://";
    public static final String APK_SUFFIX = ".apk";
    public static final String FILE_NAME = "plugin.apk";
    private static final String PLUGIN_ROOT_PATH = "tianyu_plugin";

    public PluginInstaller() {
    }

    public static File getPluginRootpath(Context var0) {
        File var1 = var0.getDir("tianyu_plugin", 0);
        if (!var1.exists()) {
            var1.mkdir();
        }

        return var1;
    }
}

