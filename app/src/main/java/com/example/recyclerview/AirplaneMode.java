package com.example.recyclerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.net.Inet4Address;

public class AirplaneMode extends BroadcastReceiver {
    private static final String TAG = "AirplaneMode";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Airplane mode toggled" + context.getClass().getName());
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
