package com.example.myapp1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.myapp1.interfaces.CallBack_StepsDetector;

public class StepsDetector {
    private SensorManager mySensorManager;
    private Sensor sensor;
    private int sensorType = Sensor.TYPE_ACCELEROMETER;
    private CallBack_StepsDetector callBack_stepsDetector;

    public StepsDetector(Context context, CallBack_StepsDetector callBack_stepsDetector) {
        this.callBack_stepsDetector = callBack_stepsDetector;
        mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mySensorManager.getDefaultSensor(sensorType);
    }

    public void start() {
        mySensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mySensorManager.unregisterListener(sensorListener);
    }

    SensorEventListener sensorListener = new SensorEventListener() {

        long last = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == sensorType) {
                if (System.currentTimeMillis() < last + 500)
                    return;
                float x = event.values[0];
                callBack_stepsDetector.movePlayer(x);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}