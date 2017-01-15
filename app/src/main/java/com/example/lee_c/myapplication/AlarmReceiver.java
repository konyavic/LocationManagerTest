package com.example.lee_c.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static PowerManager.WakeLock wl;

    public AlarmReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "onReceive");
        if (wl == null) {
            Log.d("AlarmReceiver", "wakelock acquire");
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmReceiver");
            wl.acquire();
        }

        Intent service = new Intent(context, LocationService.class);
        context.startService(service);
    }

    public static void releaseWakeLockIfNeeded() {
        if (wl != null) {
            Log.d("AlarmReceiver", "wakelock release");
            wl.release();
            wl = null;
        }
    }
}
