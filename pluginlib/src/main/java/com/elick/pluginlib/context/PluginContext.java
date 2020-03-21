package com.elick.pluginlib.context;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elick.pluginlib.TYGameActivity;

public class PluginContext extends CustomContextWrapper {
    public PluginContext(Context var1) {
        super(var1);
    }

    public void startActivity(Intent var1) {
        super.startActivity(this.buildIntent(var1));
    }

    public void startActivity(Intent var1, Bundle var2) {
        super.startActivity(this.buildIntent(var1), var2);
    }

    public Object getSystemService(String var1) {
        return super.getSystemService(var1);
    }

    Intent buildIntent(Intent var1) {
        ComponentName var2 = var1.getComponent();
        ComponentName var3 = new ComponentName(this, TYGameActivity.class);
        var1.setComponent(var3);
        var1.putExtra("ty_plugin_cls_name", var2.getClassName());
        return var1;
    }
}