package com.wildcardenter.myfab.schoolbuslocation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.wildcardenter.myfab.schoolbuslocation.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    public static final int LOCATION_ACCESS_RC=121;
    private static final int RC_SIGN_IN = 313;
    private static final int RC_SIGN_IN_DRIVER = 314;
    private static final String TAG = "LogInActivity";
    Button signInBtnParents, signInBtnDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        signInBtnParents = findViewById(R.id.SignInBtnParents);
        signInBtnDriver = findViewById(R.id.signInBtnDriver);
        signInBtnDriver.setOnClickListener(v -> createSignInIntentDriver());
        signInBtnParents.setOnClickListener(v -> {
            createSignInIntent();
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_ACCESS_RC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case LOCATION_ACCESS_RC:if (grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Log.i(TAG, "Permission has been denied by user");
            } else {
                Log.i(TAG, "Permission has been granted by user");
            }

        }
    }

    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.GreenTheme)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    public void createSignInIntentDriver() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.GreenTheme)
                        .build(),
                RC_SIGN_IN_DRIVER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                if (response != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {

                        if (response.isNewUser()) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("email", user.getEmail());
                            hashMap.put("Id", user.getUid());
                            hashMap.put("name", user.getDisplayName());
                            hashMap.put("isDriver", false);
                            hashMap.put("busNo","");
                            hashMap.put("imgUrl","https://images.vexels.com/media/users/3/145908/preview2/52eabf633ca6414e60a7677b0b917d92-male-avatar-maker.jpg");
                            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                                    .setValue(hashMap);
                            startActivity(new Intent(LogInActivity.this,ParentLandingActivity.class));
                            finish();
                        }
                    }
                    // ...
                } else {
                    Log.d(TAG,response.getError().getMessage());
                }
            }

        }
        if (requestCode == RC_SIGN_IN_DRIVER) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                if (response != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {

                        if (response.isNewUser()) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("email", user.getEmail());
                            hashMap.put("Id", user.getUid());
                            hashMap.put("name", user.getDisplayName());
                            hashMap.put("isDriver", true);
                            hashMap.put("busNo","4450");
                            hashMap.put("imgUrl","https://images.vexels.com/media/users/3/145908/preview2/52eabf633ca6414e60a7677b0b917d92-male-avatar-maker.jpg");
                            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                                    .setValue(hashMap);
                            startActivity(new Intent(LogInActivity.this,DriverLandingActivity.class));
                            finish();
                        }
                    }
                    // ...
                } else {

                    Log.d(TAG,response.getError().getMessage());
                }
            }
        }
    }
}
