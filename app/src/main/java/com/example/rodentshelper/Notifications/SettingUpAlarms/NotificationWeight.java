package com.example.rodentshelper.Notifications.SettingUpAlarms;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.room.Room;

import com.example.rodentshelper.Notifications.Receivers.NotificationReceiverWeight;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;

import java.util.Calendar;

public class NotificationWeight {
    private static final int NOTIFICATION_ID = 3;



   public void setUpNotificationWeight(Context notificationsActivity) {

       Intent notifyIntent = new Intent(notificationsActivity.getApplicationContext(), NotificationReceiverWeight.class);

       AppDatabase db = Room.databaseBuilder(notificationsActivity,
               AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
       DAONotifications daoNotifications = db.daoNotifications();
       Integer requestCode = daoNotifications.getIdFromNotificationWeight();

       SharedPreferences prefsNotificationWeight = notificationsActivity.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);

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
       if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {

           cancelAlarm (notificationsActivity, alarmManager, notifyIntent, requestCode);

           Calendar calendar = Calendar.getInstance();



           Integer hour = daoNotifications.getHourFromNotificationWeight();
           Integer minute = daoNotifications.getMinuteFromNotificationWeight();
           String periodicity = daoNotifications.getSendTimeFromNotificationWeight();
           Long unixTimeStamps = daoNotifications.getUnixTimestampsFromNotificationWeight();

           calendar.setTimeInMillis(unixTimeStamps);



       /*if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= hour) {
           Log.e(TAG, "Alarm will schedule for next day!");
           calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
       }
       else{
           Log.e(TAG, "Alarm will schedule for today!");
       }*/

           if (periodicity.equals("Codziennie")) {
               Log.e(TAG, "Alarm will schedule for next day!");
               calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
           } else if (periodicity.equals("Co tydzień")) {
               Log.e(TAG, "Alarm will schedule for next week!");
               calendar.add(Calendar.DAY_OF_YEAR, 7);
           } else if (periodicity.equals("Co dwa tygodnie")) {
               calendar.add(Calendar.DAY_OF_YEAR, 14);
           } else if (periodicity.equals("Co miesiąc")) {
               calendar.add(Calendar.DAY_OF_YEAR, 30);
           } else if (periodicity.equals("Co trzy miesiące")) {
               calendar.add(Calendar.DAY_OF_YEAR, 90);
           } else if (periodicity.equals("Co pół roku")) {
               calendar.add(Calendar.DAY_OF_YEAR, 182);
           }

           System.out.println(hour + " godzina");
           System.out.println(minute + " minuta");

           calendar.set(Calendar.HOUR_OF_DAY, hour);
           calendar.set(Calendar.MINUTE, minute);
           calendar.set(Calendar.SECOND, 0);

           System.out.println(calendar.getTimeInMillis() + " time in milli");

           //setWindows unfortunately sometimes does not want to fire
           //so we need to swap it to 'setExact' to be sure that
           //it will work on every device
           /*alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                   10000 * 60, pendingIntent);*/
           alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);

           System.out.println(requestCode + " WAZENIE REQUEST");

           daoNotifications.updateNextNotificationTimeWeight(calendar.getTimeInMillis());
           db.close();

           System.out.println("Włączono alarm wazenie");
       } else {
           alarmManager.cancel(pendingIntent);
           System.out.println("Wyłączono alarm wazenie");
       }




                /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                        1000 * 60, pendingIntent);*/
       //1000 * 60 * 60 * 24    600000
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


