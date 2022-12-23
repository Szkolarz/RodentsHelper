package com.example.rodentshelper.Notifications.Separate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.room.Room;

import com.example.rodentshelper.Notifications.NotificationReceiverFeeding;
import com.example.rodentshelper.Notifications.NotificationReceiverVisit;
import com.example.rodentshelper.Notifications.NotificationsModel;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

public class NotificationVisit {


   public void setUpNotificationVisit(Context notificationsActivity, String timeKey, String dateFormat) {

       Intent notifyIntent = new Intent(notificationsActivity, NotificationReceiverVisit.class);

       SharedPreferences prefsNotificationVisit = notificationsActivity.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);

       AppDatabase db = Room.databaseBuilder(notificationsActivity,
               AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
       DAONotifications daoNotifications = db.daoNotifications();

       AlarmManager alarmManager = (AlarmManager) notificationsActivity.getSystemService(Context.ALARM_SERVICE);
       PendingIntent pendingIntent = null;

        //if prefs == true
       if (prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {




       String[] parts = Objects.requireNonNull(timeKey.split("[:]"));
       Integer hour = Integer.valueOf(parts[0]);
       Integer minute = Integer.valueOf(parts[1]);

       daoNotifications.insertRecordNotification(new NotificationsModel(null, hour, minute,
               null, System.currentTimeMillis(), Long.valueOf(Date.valueOf(dateFormat).getTime()),"visit"));



           Integer requestCode = daoNotifications.getLastIdFromNotificationVisit();
       Calendar calendar = Calendar.getInstance();


       System.out.println(requestCode + " request");
       Long nextNotificationTimeVisit = daoNotifications.getNextNotificationTimeVisit(requestCode);
           System.out.println(nextNotificationTimeVisit + " next");
       calendar.setTimeInMillis(nextNotificationTimeVisit);
       calendar.set(Calendar.HOUR_OF_DAY, hour);
       calendar.set(Calendar.MINUTE, minute);
       calendar.set(Calendar.SECOND, 0);

       daoNotifications.updateNextNotificationTimeVisit(calendar.getTimeInMillis(), requestCode);



       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, requestCode, notifyIntent, PendingIntent.FLAG_MUTABLE);
       } else {
           pendingIntent = PendingIntent.getBroadcast
                   (notificationsActivity, requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       }


       System.out.println(hour + " godzina");
       System.out.println(minute + " minuta");


       alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);


       System.out.println("Włączono alarm");


       }

       db.close();


   }


}


