package com.example.myplugin;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elick.pluginlib.BaseForPluginActivity;

public class SecondActivity extends BaseForPluginActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FrameLayout frameLayout = new FrameLayout(this);
//        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        TextView textView = new TextView(this);
//        textView.setText("second activity");
//        textView.setGravity(Gravity.CENTER);
//        frameLayout.addView(textView);
        setContentView(R.layout.second_act);
    }
}
