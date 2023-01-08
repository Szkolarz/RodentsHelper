package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.Notifications.UpdateNotification;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences prefsFirstStart = getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        UpdateNotification updateNotification = new UpdateNotification();
        updateNotification.checkIfUserHasMissedNotification(SplashScreen.this);
        updateNotification.checkNotificationPreferences(SplashScreen.this);

        AsyncActivity internetAsyncCheck = new AsyncActivity();
        internetAsyncCheck.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                if (prefsFirstStart.getInt("prefsFirstStart", 0) != 0) {
                    Intent intent = new Intent(SplashScreen.this, ViewRodents.class);
                    startActivity(intent);
                    finish();
                } else if (prefsFirstStart.getInt("prefsFirstStart", 0) == 0) {
                    Intent intent = new Intent(SplashScreen.this, FirstStart.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 800);



    }
}
