package com.wildcardenter.myfab.schoolbuslocation.services;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wildcardenter.myfab.schoolbuslocation.R;
import com.wildcardenter.myfab.schoolbuslocation.activities.DriverLandingActivity;

import java.util.HashMap;

import static com.wildcardenter.myfab.schoolbuslocation.App.CHANNEL_ID;

public class LocationForegroundService extends Service {
    private static final String TAG = "LocationForegroundServi";
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getLocation();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAuth=FirebaseAuth.getInstance();
        Intent intent1 = new Intent(this, DriverLandingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Location Sharing is On")
                .setContentText("You are sharing your Real-time Location")
                .addAction(R.drawable.ic_pause_circle_filled_black_24dp, "Stop Service", pendingIntent)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("longitude", location.getLongitude());
                hashMap.put("latitude", location.getLatitude());
                hashMap.put("speed", location.getSpeed());
                if (mAuth!=null) {
                    FirebaseDatabase.getInstance().getReference("Shared_locations").child(mAuth.getUid()).setValue(hashMap);
                    Toast.makeText(LocationForegroundService.this, location.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.e(TAG, "provider is : " + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(LocationForegroundService.this, provider + " Enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(LocationForegroundService.this, provider + " Disabled", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,15000,0,locationListener);
    }


    @Override
    public void onDestroy() {
        locationManager.removeUpdates(locationListener);
        super.onDestroy();
    }
}
