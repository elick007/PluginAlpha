package com.example.myplugin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elick.pluginlib.BaseForPluginActivity;

public class PluginTestActivity extends BaseForPluginActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id=getResources().getIdentifier("plugin_test", "layout",getPackageName());
        Log.e("plugin","get plugin res id "+id);
        setContentView(id);
        TextView to_second=findViewById(R.id.to_second);
        to_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PluginTestActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
