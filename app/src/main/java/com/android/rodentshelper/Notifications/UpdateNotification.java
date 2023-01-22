package com.android.rodentshelper.Notifications;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationVisit;
import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationWeight;
import com.android.rodentshelper.ROOM.DAONotifications;
import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding;
import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding2;
import com.android.rodentshelper.ROOM.AppDatabase;

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
        SharedPreferences prefsNotificationFeeding2 = context.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();
        //if = true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
        System.out.println("ABABA");

            long actualTimeStamp = System.currentTimeMillis();
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
                // next notificatoin time is setting up in NotificationFeeding

            }

        }

        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {

            long actualTimeStamp = System.currentTimeMillis();

            Long nextNotificationDate;
            Integer id_notification;



                id_notification = daoNotifications.getFirstIdFromNotificationFeeding();
                nextNotificationDate = daoNotifications.getNextNotificationTimeFeeding(id_notification);


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



        if (prefsNotificationFeeding2.getBoolean("prefsNotificationFeeding2", false)) {

            long actualTimeStamp = System.currentTimeMillis();

            Long nextNotificationDate;
            Integer id_notification;

                id_notification = daoNotifications.getLastIdFromNotificationFeeding();
                nextNotificationDate = daoNotifications.getNextNotificationTimeFeeding(id_notification);


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

        db.close();
    }



    public void checkNotificationPreferences (Context context) {

        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding = context.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding2 = context.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationVisit = context.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);

        //if == true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
            NotificationWeight notificationWeight = new NotificationWeight();
            notificationWeight.setUpNotificationWeight(context.getApplicationContext());
        }



        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {
            NotificationFeeding notificationFeeding = new NotificationFeeding();

                notificationFeeding.setUpNotificationFeeding(context.getApplicationContext());
        }


        if (prefsNotificationFeeding2.getBoolean("prefsNotificationFeeding2", false)) {
            NotificationFeeding2 notificationFeeding2 = new NotificationFeeding2();
            //it resets both of the alarms, that's why it has a loop

                notificationFeeding2.setUpNotificationFeeding(context.getApplicationContext());

        }



        if (prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotifications daoNotifications = db.daoNotifications();

            List<NotificationsModel> notificationsModel = daoNotifications.getAllNotificationsVisit();

            String timeKey, dateFormat, sendTime;
            Integer id_visit;

            NotificationVisit notificationVisit = new NotificationVisit();
            for (int i=0; i<notificationsModel.size(); i++) {
                timeKey = notificationsModel.get(i).getHour().toString();
                timeKey += (":");
                timeKey += (notificationsModel.get(i).getMinute().toString());

                dateFormat = notificationsModel.get(i).getNext_notification_time().toString();
                sendTime = notificationsModel.get(i).getSend_time();
                id_visit = notificationsModel.get(i).getId_visit();

                notificationVisit.setUpNotificationVisit(context, timeKey, dateFormat, sendTime, id_visit);
            }
        }
    }
}
