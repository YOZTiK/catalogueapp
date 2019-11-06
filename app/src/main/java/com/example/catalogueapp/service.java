package com.example.catalogueapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class service extends Service {
    private Timer mTimer;
    private Handler mHandler = new Handler();

    private static final int TIMER_INTERVAL = 20000; // 2 Minute
    private static final int TIMER_DELAY = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        if (mTimer != null)
            mTimer = null;

        // Create new Timer
        mTimer = new Timer();

        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `2 min`
        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Cancel timer
        mTimer.cancel();
    }

    // Required to do some task
    // Here I just display a toast message "Hello world"
    private class DisplayToastTimerTask extends TimerTask {

        @Override
        public void run() {

            // Do something....

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Hello world", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
