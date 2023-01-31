package com.gryzoniopedia.rodentshelper.Notifications.SettingUpAlarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ROOM.DAONotifications;
import com.gryzoniopedia.rodentshelper.Notifications.Receivers.NotificationReceiverFeeding2;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;

import java.util.Calendar;

public class NotificationFeeding2 {

   public void setUpNotificationFeeding(Context notificationsActivity) {

       Intent notifyIntent = new Intent(notificationsActivity.getApplicationContext(), NotificationReceiverFeeding2.class);

       AppDatabase db = Room.databaseBuilder(notificationsActivity,
               AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
       DAONotifications daoNotifications = db.daoNotifications();


       Integer requestCode;
       /** the last id **/
       requestCode = daoNotifications.getLastIdFromNotificationFeeding();

       SharedPreferences prefsNotificationFeeding2 = notificationsActivity.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);

       PendingIntent pendingIntent;
       AlarmManager alarmManager = (AlarmManager) notificationsActivity.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
       } else {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       }

       //if prefs == true
       if (prefsNotificationFeeding2.getBoolean("prefsNotificationFeeding2", false)) {

           cancelAlarm (notificationsActivity.getApplicationContext(), alarmManager, notifyIntent, requestCode);

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
           PendingIntent pendingIntentCancel;

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               pendingIntentCancel = PendingIntent.getBroadcast
                       (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
               alarmManager.cancel(pendingIntentCancel);
           } else {
               pendingIntentCancel = PendingIntent.getBroadcast
                       (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
               alarmManager.cancel(pendingIntentCancel);
           }

           System.out.println("Wyłączono alarm");
       }
       db.close();
   }


    private void cancelAlarm (Context notificationsActivity, AlarmManager alarmManager,
                              Intent notifyIntent, Integer requestCode) {
        PendingIntent pendingIntentCancel;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntentCancel = PendingIntent.getBroadcast
                    (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
            alarmManager.cancel(pendingIntentCancel);
        } else {
            pendingIntentCancel = PendingIntent.getBroadcast
                    (notificationsActivity.getApplicationContext(), requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntentCancel);
        }

        System.out.println("Wyłączono alarm");
    }
}