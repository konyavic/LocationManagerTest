package com.example.lee_c.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class LocationService extends Service implements LocationListener {

    private LocationManager locationManager;
    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;
    private int networkInterval = 3;
    private int gpsInterval = 3;
    private int minDistance = 0;

    public LocationService() {
    }

    protected void initializeLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, networkInterval * ONE_SECOND_IN_MILLISECONDS, minDistance, this);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, gpsInterval * ONE_SECOND_IN_MILLISECONDS, minDistance, this);
    }

    @Override
    public void onCreate() {
        Log.d("LocationService", "onCreate");
        initializeLocationManager();

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LocationService", "onStatusChanged: " + Integer.toString(status));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationService", "onLocationChanged: " + location.toString());
        AlarmReceiver.releaseWakeLockIfNeeded();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LocationService", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LocationService", "GPS disabled");
    }
}
