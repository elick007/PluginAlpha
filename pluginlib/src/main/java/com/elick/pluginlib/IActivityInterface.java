package com.elick.pluginlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;

public interface IActivityInterface {
    void onCreate(Bundle var1);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle var1);

    boolean onTouchEvent(MotionEvent var1);

    void onBackPressed();

    void onConfigurationChanged(Configuration var1);

    void attch(Activity var1);

    void attachBaseContext(Context var1);

    void onRequestPermissionsResult(int var1,  String[] var2,  int[] var3);

    void onNewIntent(Intent var1);

    void onActivityResult(int var1, int var2, Intent var3);
}

