package com.example.rodentshelper.Notifications;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding;
import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationVisit;
import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationWeight;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;

import java.util.Calendar;
import java.util.List;

public class UpdateNotification {


    //This method works in while loop only when users' phone was turned off
    //and has missed notification in specified time.
    //Method just adds +1 day in while loop until actual nextNotificationTime
    //is greater than actual System.currentTimeMillis();
    public void checkIfUserHasMissedNotification (Context context) {
        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding = context.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();
        //if = true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {


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
                daoNotifications.updateUnixTimestampWeight(calendar.getTimeInMillis());
            }

        }

        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {

            Long actualTimeStamp = System.currentTimeMillis();
            Long nextNotificationDate = daoNotifications.getNextNotificationTimeFeeding();

            Integer id_notification;


            for (int i=0; i<2; i++) {

                if (i==0) {
                    id_notification = daoNotifications.getFirstIdFromNotificationFeeding();
                } else {
                    id_notification = daoNotifications.getLastIdFromNotificationFeeding();
                }


                Long unixTimeStamps = daoNotifications.getUnixTimestampsFromNotificationFeeding(id_notification);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(unixTimeStamps);


                //if nextNotificationTime + 15 minutes
                if ((nextNotificationDate + (10000 * 90)) < actualTimeStamp) {
                    while ((nextNotificationDate + (10000 * 90)) < actualTimeStamp) {
                        //+ one day
                        nextNotificationDate += 1000 * 60 * 60 * 24;
                        calendar.add(Calendar.DAY_OF_YEAR, 1);

                    }
                    daoNotifications.updateUnixTimestampFeeding(calendar.getTimeInMillis(), id_notification);
                }
            }

        }

        db.close();
    }



    public void checkNotificationPreferences (Context context) {

        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding = context.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationVisit = context.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);

        //if == true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
            NotificationWeight notificationWeight = new NotificationWeight();
            notificationWeight.setUpNotificationWeight(context);
        }



        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {
            NotificationFeeding notificationFeeding = new NotificationFeeding();
            //it resets both of the alarms, that's why it has a loop

            for (int i=0; i<2; i++) {
                notificationFeeding.setUpNotificationFeeding(context);
            }

        }



        if (prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotifications daoNotifications = db.daoNotifications();

            List<NotificationsModel> notificationsModel = daoNotifications.getAllNotificationsVisit();

            String timeKey = null, dateFormat = null, sendTime = null;
            Integer id_visit = null;

            NotificationVisit notificationVisit = new NotificationVisit();
            for (int i=0; i<notificationsModel.size(); i++) {
                timeKey = notificationsModel.get(i).getHour().toString();
                timeKey += (":");
                timeKey += (notificationsModel.get(i).getMinute().toString());

                dateFormat = notificationsModel.get(i).getNext_notification_time().toString();
                sendTime = notificationsModel.get(i).getPeriodicity();
                id_visit = notificationsModel.get(i).getId_visit();

                notificationVisit.setUpNotificationVisit(context, timeKey, dateFormat, sendTime, id_visit);
            }
        }
    }
}
