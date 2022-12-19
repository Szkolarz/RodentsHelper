package com.example.rodentshelper.Notifications;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;

import java.util.Calendar;

public class UpdateNotification {


    //This method works in while loop only when users' phone was turned off
    //and has missed notification in specified time.
    //Method just adds +1 day in while loop until actual nextNotificationTime
    //is greater than actual System.currentTimeMillis();
    public void checkIfUserHasMissedNotification (Context context) {
        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);


        //if = true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotifications daoNotifications = db.daoNotifications();


            Long actualTimeStamp = System.currentTimeMillis();
            Long unixTimeStamps = daoNotifications.getUnixTimestampsFromNotificationWeight();
            Long nextNotificationTime = daoNotifications.getNextNotificationTimeWeight();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(unixTimeStamps);


            //if nextNotificationTime + 15 minutes
            if ((nextNotificationTime + (10000 * 90)) < actualTimeStamp) {
                while ((nextNotificationTime + (10000 * 90)) < actualTimeStamp) {
                    //+ one day
                    nextNotificationTime += 1000 * 60 * 60 * 24;
                    calendar.add(Calendar.DAY_OF_YEAR, 1);

                }
                daoNotifications.updateUnixTimestamp(calendar.getTimeInMillis());
            }
            db.close();
        }

    }


    public void updateAlarm (Context context) {
        NotificationWeight notificationWeight = new NotificationWeight();
        notificationWeight.setUpNotificationWeight(context);
    }


    public void checkNotificationPreferences (Context context) {
        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
        //if == true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
            updateAlarm(context);
        }


    }

}
