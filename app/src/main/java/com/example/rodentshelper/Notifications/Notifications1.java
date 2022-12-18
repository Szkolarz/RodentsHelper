package com.example.rodentshelper.Notifications;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;

import java.util.Calendar;

public class Notifications1 {
    private static final int NOTIFICATION_ID = 3;



   public void showNotification(Context notificationsActivity) {

       Intent notifyIntent = new Intent(notificationsActivity, MyReceiver.class);


       PendingIntent pendingIntent;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, 2, notifyIntent, PendingIntent.FLAG_MUTABLE);
       } else {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       }


       AlarmManager alarmManager = (AlarmManager) notificationsActivity.getSystemService(Context.ALARM_SERVICE);


       // every day at 9 am
       Calendar calendar = Calendar.getInstance();

       calendar.setTimeInMillis(System.currentTimeMillis());

       // if it's after or equal 9 am schedule for next day
       /*if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 24) {
           Log.e(TAG, "Alarm will schedule for next day!");
           calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
       }
       else{
           Log.e(TAG, "Alarm will schedule for today!");
       }*/
       Log.e(TAG, "Alarm will schedule for today!");
       calendar.set(Calendar.HOUR_OF_DAY, 0);
       calendar.set(Calendar.MINUTE, 9);
       calendar.set(Calendar.SECOND, 0);


       alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
               AlarmManager.INTERVAL_DAY, pendingIntent);


                /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                        1000 * 60, pendingIntent);*/
       //1000 * 60 * 60 * 24
   }





}


