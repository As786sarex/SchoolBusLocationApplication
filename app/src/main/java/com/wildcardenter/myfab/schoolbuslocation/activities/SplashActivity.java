package com.wildcardenter.myfab.schoolbuslocation.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wildcardenter.myfab.schoolbuslocation.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(()->{
            startActivity(new Intent(SplashActivity.this, DriverLandingActivity.class));
            finish();
        },3000);
    }
}
