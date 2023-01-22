package com.android.rodentshelper.Notifications;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.room.Room;

import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding;
import com.android.rodentshelper.ROOM.DAONotifications;
import com.android.rodentshelper.Alerts;
import com.android.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding2;
import com.android.rodentshelper.ROOM.AppDatabase;

import java.util.Locale;

public class SetUpNotificationsFeeding {

    private int hour, minute;

    public void notificationFeeding (NotificationsActivity notificationsActivity,
                               TextView textView3_notifications, TextView textView4_notifications,
                               CheckBox checkBoxNotifications2) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;

            AppDatabase db = Room.databaseBuilder(notificationsActivity,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotifications daoNotifications = db.daoNotifications();

            if (FlagSetupFeeding.getFlagIsNotificationFirst()) {
                daoNotifications.deleteNotificationFeeding();
            }
            daoNotifications.insertRecordNotification(new NotificationsModel(null, hour, minute,
                    null, System.currentTimeMillis(), null,"feeding"));

            SharedPreferences prefsNotificationFeeding = notificationsActivity.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
            prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", true);
            prefsEditorNotificationFeeding.apply();

            SharedPreferences prefsNotificationFeeding2 = notificationsActivity.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorNotificationFeeding2 = prefsNotificationFeeding2.edit();
            prefsEditorNotificationFeeding2.putBoolean("prefsNotificationFeeding2", true);
            prefsEditorNotificationFeeding2.apply();

            NotificationFeeding notificationFeeding = new NotificationFeeding();
            NotificationFeeding2 notificationFeeding2 = new NotificationFeeding2();

            if (FlagSetupFeeding.getFlagIsNotificationFirst()) {
                notificationFeeding.setUpNotificationFeeding(notificationsActivity.getApplicationContext());
            } else {
                notificationFeeding2.setUpNotificationFeeding(notificationsActivity.getApplicationContext());
            }


            if (!FlagSetupFeeding.getFlagIsNotificationFirst()) {
                checkBoxNotifications2.setChecked(true);
                setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, notificationsActivity);

                Alerts alert = new Alerts();
                alert.simpleInfo("Dodano nowe powiadomienie", "Pomyślnie dodano nowe powiadomienie!" +
                        "Powiadomienia o karmieniu będą wysyłane codziennie, zaczynając od jutra.", notificationsActivity);
                FlagSetupFeeding.setFlagIsNotificationFirst(true);
            } else {
                FlagSetupFeeding.setFlagIsNotificationFirst(false);
                notificationFeeding(notificationsActivity, textView3_notifications,
                        textView4_notifications, checkBoxNotifications2);
            }

            db.close();

        };

            TimePickerDialog timePickerDialog = new TimePickerDialog(notificationsActivity, onTimeSetListener, hour, minute, true);

            timePickerDialog.setOnCancelListener (dialog -> {
                    checkBoxNotifications2.setChecked(false);
                    setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, notificationsActivity);

            });


            timePickerDialog.setTitle("Wybierz godzinę, o której dostaniesz powiadomienie");
            timePickerDialog.show();

    }

    public void setUpCheckbox(CheckBox checkbox, TextView textView3_notifications, TextView textView4_notifications,
                              NotificationsActivity notificationsActivity) {
        AppDatabase db = Room.databaseBuilder(notificationsActivity,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();

        if (!checkbox.isChecked()) {

            daoNotifications.deleteNotificationFeeding();

            textView3_notifications.setVisibility(View.GONE);
            textView4_notifications.setVisibility(View.GONE);
            checkbox.setText("Wyłączone");


            SharedPreferences prefsNotificationFeeding = notificationsActivity.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);
            SharedPreferences prefsNotificationFeeding2 = notificationsActivity.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
            SharedPreferences.Editor prefsEditorNotificationFeeding2 = prefsNotificationFeeding2.edit();
            prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", false);
            prefsEditorNotificationFeeding.apply();
            prefsEditorNotificationFeeding2.putBoolean("prefsNotificationFeeding2", false);
            prefsEditorNotificationFeeding2.apply();

        } else {
            textView3_notifications.setVisibility(View.VISIBLE);
            textView4_notifications.setVisibility(View.VISIBLE);
            checkbox.setText("Włączone");

            Integer idFeeding = daoNotifications.getFirstIdFromNotificationFeeding();


            Integer hour1 = daoNotifications.getHourFromNotificationFeeding(idFeeding);
            Integer minute1 = daoNotifications.getMinuteFromNotificationFeeding(idFeeding);
            idFeeding = daoNotifications.getLastIdFromNotificationFeeding();
            Integer hour2 = daoNotifications.getHourFromNotificationFeeding(idFeeding);
            Integer minute2 = daoNotifications.getMinuteFromNotificationFeeding(idFeeding);

            //Long nextNotificationTime = daoNotifications.getNextNotificationTimeFeeding();

            textView3_notifications.setText("Powiadomienia są wysyłane codziennie");

            textView4_notifications.setText("Godziny wysyłania powiadomień: " + String.format(Locale.getDefault(), "%02d:%02d", hour1, minute1));
            textView4_notifications.append(" oraz " + String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
        }
        db.close();
    }

}
