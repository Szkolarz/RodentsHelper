package com.example.rodentshelper.Notifications.SettingUpAlarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.room.Room;

import com.example.rodentshelper.Notifications.Receivers.NotificationReceiverFeeding;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;

import java.util.Calendar;

public class NotificationFeeding {


   public void setUpNotificationFeeding(Context notificationsActivity) {

       Intent notifyIntent = new Intent(notificationsActivity, NotificationReceiverFeeding.class);

       AppDatabase db = Room.databaseBuilder(notificationsActivity,
               AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
       DAONotifications daoNotifications = db.daoNotifications();



       SharedPreferences prefsRequestCodeFeeding = notificationsActivity.getSharedPreferences("prefsRequestCodeFeeding", Context.MODE_PRIVATE);
       SharedPreferences.Editor prefsEditorRequestCodeFeeding = prefsRequestCodeFeeding.edit();
       Integer requestCode;

       if (prefsRequestCodeFeeding.getBoolean("prefsRequestCodeFeeding", true)) {
           System.out.println("codeFeed1");
           requestCode = daoNotifications.getFirstIdFromNotificationFeeding();
           System.out.println(requestCode + "req1");
       } else {
           System.out.println("codeFeed2");
           requestCode = daoNotifications.getLastIdFromNotificationFeeding();
           System.out.println(requestCode + "req2");

       }



       SharedPreferences prefsNotificationFeeding = notificationsActivity.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);

       PendingIntent pendingIntent;
       AlarmManager alarmManager = (AlarmManager) notificationsActivity.getSystemService(Context.ALARM_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
       } else {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       }

       //if prefs == true
       if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {

           Calendar calendar = Calendar.getInstance();


           Integer hour = daoNotifications.getHourFromNotificationFeeding(requestCode);
           Integer minute = daoNotifications.getMinuteFromNotificationFeeding(requestCode);
           Long unixTimeStamps = daoNotifications.getUnixTimestampsFromNotificationFeeding(requestCode);

           calendar.setTimeInMillis(unixTimeStamps);

           calendar.add(Calendar.DAY_OF_YEAR, 1);

           System.out.println(hour + " godzina");
           System.out.println(minute + " minuta");

           calendar.set(Calendar.HOUR_OF_DAY, hour);
           calendar.set(Calendar.MINUTE, minute);
           calendar.set(Calendar.SECOND, 0);


           alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);

           daoNotifications.updateNextNotificationTimeFeeding(calendar.getTimeInMillis(), requestCode);


           System.out.println("Włączono alarm");



       } else {
           alarmManager.cancel(pendingIntent);
           System.out.println("Wyłączono alarm");
       }

       db.close();


   }


}


