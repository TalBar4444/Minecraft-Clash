package com.example.myapp1.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.myapp1.R;
import com.google.android.material.button.MaterialButton;

public class ActivitySettings extends AppCompatActivity  {
    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    private AppCompatCheckBox settings_checkBox_controllers;
    private AppCompatCheckBox settings_checkBox_tilt;
    private MaterialButton settings_BTN_easy;
    private MaterialButton settings_BTN_medium;
    private MaterialButton settings_BTN_hard;
    private MaterialButton settings_BTN_master;
    private int gameMode,gameLevel;
    public static final int CONTROLLERS_MODE = 0;
    public static final int TILT_MODE = 1;
    public static final int LEVEL_EASY = 0;
    public static final int LEVEL_MEDIUM = 1;
    public static final int LEVEL_HARD = 2;
    public static final int LEVEL_MASTER = 3;
    private MaterialButton settings_BTN_backToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        checkSensors();
        onActionBTNMode();
        onActionBTNLevel();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkSensors() {
        SensorManager mySensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null)
            settings_checkBox_tilt.setVisibility(View.GONE);
    }

    private void onActionBTNMode() {
        settings_checkBox_controllers.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                settings_checkBox_tilt.setChecked(false);
                gameMode = CONTROLLERS_MODE;
            } else {
                if(!(settings_checkBox_tilt.isChecked())) {
                    gameMode = CONTROLLERS_MODE;
                }
            }
        });

        settings_checkBox_tilt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                settings_checkBox_controllers.setChecked(false);
                gameMode = TILT_MODE;
            }
        });
    }

    private void onActionBTNLevel() {
        settings_BTN_easy.setOnClickListener(v -> gameLevel=LEVEL_EASY);
        settings_BTN_medium.setOnClickListener(v -> gameLevel=LEVEL_MEDIUM);
        settings_BTN_hard.setOnClickListener(v -> gameLevel=LEVEL_HARD);
        settings_BTN_master.setOnClickListener(v -> gameLevel=LEVEL_MASTER);
    }

    private void findViews(){
        settings_checkBox_controllers = findViewById(R.id.settings_checkbox_controllers);
        settings_checkBox_tilt = findViewById(R.id.settings_checkBox_tilt);
        settings_BTN_easy = findViewById(R.id.settings_BTN_easy);
        settings_BTN_medium = findViewById(R.id.settings_BTN_medium);
        settings_BTN_hard = findViewById(R.id.settings_BTN_hard);
        settings_BTN_master = findViewById(R.id.settings_BTN_master);
        settings_BTN_backToMenu = findViewById(R.id.settings_BTN_backToMenu);

    }

    private void initViews() {
        settings_BTN_backToMenu.setOnClickListener(v -> backToMenu());
    }

    private void backToMenu(){
        Intent intent = new Intent(this, ActivityMain.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ActivityGame.KEY_MODE, gameMode);
        bundle.putInt(ActivityGame.KEY_LEVEL, gameLevel);
        intent.putExtra(ActivityMain.KEY_BUNDLE, bundle);
        startActivity(intent);
    }
}
