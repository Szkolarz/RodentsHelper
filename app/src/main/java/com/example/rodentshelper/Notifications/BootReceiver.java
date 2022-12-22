package com.example.rodentshelper.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.rodentshelper.Notifications.Separate.NotificationFeeding;
import com.example.rodentshelper.Notifications.Separate.NotificationWeight;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent intent1 = new Intent(context, Notifications1.class);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            /*AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotifications daoNotifications = db.daoNotifications();

            daoNotifications.updateUnixTimestamp(String.valueOf(System.currentTimeMillis()));
            db.close();*/

            /*SharedPreferences prefsAfterRebootNotificationWeight = context.getSharedPreferences("prefsAfterRebootNotificationWeight", context.MODE_PRIVATE);
            SharedPreferences.Editor prefsAfterRebootEditorNotificationWeight = prefsAfterRebootNotificationWeight.edit();
            prefsAfterRebootEditorNotificationWeight.putBoolean("prefsAfterRebootNotificationWeight", true);
            prefsAfterRebootEditorNotificationWeight.apply();*/

            SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
            SharedPreferences prefsNotificationFeeding = context.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);

            //if == true
            if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
                NotificationWeight notificationWeight = new NotificationWeight();
                notificationWeight.setUpNotificationWeight(context);
            }

            if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {
                NotificationFeeding notificationFeeding = new NotificationFeeding();
                notificationFeeding.setUpNotificationFeeding(context);
            }



            System.out.println("after boot");
        }
    }
}