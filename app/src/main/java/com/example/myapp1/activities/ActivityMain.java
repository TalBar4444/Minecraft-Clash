package com.example.myapp1.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapp1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class ActivityMain extends AppCompatActivity {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final int KEY_VIEW = 1;
    public static final int DEFAULT_MODE = 0;
    private MaterialButton menu_BTN_score;
    private MaterialButton menu_BTN_settings;
    private MaterialButton menu_BTN_newGame;
    private Bundle bundle;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getBundleExtra(ActivitySettings.KEY_BUNDLE);
        findViews();
        onActionBTN();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
    }

    private void findViews(){
        menu_BTN_newGame = findViewById(R.id.menu_BTN_newGame);
        menu_BTN_score = findViewById(R.id.menu_BTN_score);
        menu_BTN_settings = findViewById(R.id.menu_BTN_settings);
    }
    private void onActionBTN() {
        menu_BTN_newGame.setOnClickListener(v -> openGame());
        menu_BTN_score.setOnClickListener(v -> openScoreTable());
        menu_BTN_settings.setOnClickListener(v -> openSettings());
    }

    private void openGame() {
        Intent intent = new Intent(this, ActivityGame.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            myBundle.putInt(ActivityGame.KEY_MODE, DEFAULT_MODE);
            myBundle.putInt(ActivityGame.KEY_LEVEL, DEFAULT_MODE);
            intent.putExtra(ActivityGame.KEY_BUNDLE, myBundle);
            updateLocationInBundle(myBundle);
        }
        else {
            updateLocationInBundle(bundle);
            intent.putExtra(ActivityGame.KEY_BUNDLE, bundle);

        }
        startActivity(intent);
        finish();
    }
    private void updateLocationInBundle(Bundle bundle){
        try{
            bundle.putDouble(ActivityScoreTable.KEY_LAT, currentLocation.getLatitude());
            bundle.putDouble(ActivityScoreTable.KEY_LNG, currentLocation.getLongitude());
        }catch (Exception e){
            bundle.putDouble(ActivityScoreTable.KEY_LAT, 0);
            bundle.putDouble(ActivityScoreTable.KEY_LNG, 0);
        }
    }

    private void openScoreTable() {
        Intent intent = new Intent(this, ActivityScoreTable.class);
        if(bundle == null) {
            Bundle myBundle = new Bundle();
            myBundle.putInt(ActivityScoreTable.KEY_STATE, KEY_VIEW);
            intent.putExtra(ActivityGame.KEY_BUNDLE, myBundle);
        }
        else {
            bundle.putInt(ActivityScoreTable.KEY_STATE, KEY_VIEW);
            intent.putExtra(ActivityGame.KEY_BUNDLE, bundle);
        }
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null){
                currentLocation = location;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getCurrentLocation();
            } else
                finish();
        }
    }

}