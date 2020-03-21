package com.elick.pluginlib;

import android.util.Log;

class PluginLog {
    static void runtimeLog(String tag, String msg) {
        Log.e(tag, msg);
    }

    static void log(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void warningLog(String proxyActivity, String load_iActivityInterface_was_fail) {
        Log.w(proxyActivity, load_iActivityInterface_was_fail);
    }

    public static void installLog(String tag, String msg) {
        e(tag, msg);
    }
    private static void e(String tag,String msg){
        Log.e(tag, msg);
    }
}
