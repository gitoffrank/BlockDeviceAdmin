package com.test.www.blockdeviceadmin;

import android.content.Intent;
import android.content.IntentFilter;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "com.android.settings.DeviceAdminDemo";
    MyReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver=new MyReceiver();
        registerReceiver(mReceiver, new IntentFilter(ACTION));
        Intent i=new Intent(MainActivity.ACTION);
        sendBroadcast(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver,  new IntentFilter(ACTION));
    }
    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }
}
