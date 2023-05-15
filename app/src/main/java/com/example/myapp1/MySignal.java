package com.example.myapp1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class MySignal {
    private Context context;
    private Vibrator vibrator;

    private static MySignal mySignal;

    private MySignal(Context context) {
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void init(Context context) {
        if (mySignal == null) {
            mySignal = new MySignal(context.getApplicationContext());
        }
    }

    public static MySignal getInstance() {
        return mySignal;
    }

    public void vibrate(long milliSec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliSec, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milliSec);
        }
    }

    public void playSound(Context context, int soundID) {
        MediaPlayer player = MediaPlayer.create(context, soundID);
        player.setVolume(110, 110);
        player.start();
    }

    public void toast(String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException ignored) {}
        });
    }

}