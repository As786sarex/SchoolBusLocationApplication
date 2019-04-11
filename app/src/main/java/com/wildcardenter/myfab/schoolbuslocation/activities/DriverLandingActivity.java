package com.wildcardenter.myfab.schoolbuslocation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.wildcardenter.myfab.schoolbuslocation.R;
import com.wildcardenter.myfab.schoolbuslocation.services.LocationForegroundService;

public class DriverLandingActivity extends AppCompatActivity {

    private static final String TAG = "DriverLandingActivity";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_landing);
        mAuth = FirebaseAuth.getInstance();

    }

    public void startService(View view) {
        Intent intent = new Intent(this, LocationForegroundService.class);
        startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(this, LocationForegroundService.class);
        stopService(intent);
    }

    public void startMap(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi() {
        if (mAuth.getCurrentUser() != null) {
            Log.d("Login", "User is Signed in");
        } else {
            Intent intent = new Intent(DriverLandingActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
