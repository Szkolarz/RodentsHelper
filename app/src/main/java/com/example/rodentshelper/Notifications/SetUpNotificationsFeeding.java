package com.example.rodentshelper.Notifications;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.Notifications.Separate.NotificationFeeding;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;
import com.example.rodentshelper.ROOM.DateFormat;

import java.util.Locale;

public class SetUpNotificationsFeeding {

    private int hour, minute;
    private boolean ifTimeSet = false;


    public void notificationFeeding (NotificationsActivity notificationsActivity,
                               TextView textView3_notifications, TextView textView4_notifications,
                               CheckBox checkBoxNotifications2) {

            ifTimeSet = false;

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    ifTimeSet = false;

                    AppDatabase db = Room.databaseBuilder(notificationsActivity,
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAONotifications daoNotifications = db.daoNotifications();

                    if (FlagSetupFeeding.getFlagIsNotificationFirst()) {
                        daoNotifications.deleteNotificationFeeding();
                    }
                    daoNotifications.insertRecordNotification(new NotificationsModel(null, hour, minute,
                            null, System.currentTimeMillis(), null,"feeding"));


                    SharedPreferences prefsNotificationFeeding = notificationsActivity.getSharedPreferences("prefsNotificationFeeding", notificationsActivity.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
                    prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", true);
                    prefsEditorNotificationFeeding.apply();

                    if (FlagSetupFeeding.getFlagIsNotificationFirst()) {
                        SharedPreferences prefsRequestCodeFeeding = notificationsActivity.getSharedPreferences("prefsRequestCodeFeeding", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditorRequestCodeFeeding = prefsRequestCodeFeeding.edit();
                        prefsEditorRequestCodeFeeding.putBoolean("prefsRequestCodeFeeding", true);
                        prefsEditorRequestCodeFeeding.apply();
                    } else {
                        SharedPreferences prefsRequestCodeFeeding = notificationsActivity.getSharedPreferences("prefsRequestCodeFeeding", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditorRequestCodeFeeding = prefsRequestCodeFeeding.edit();
                        prefsEditorRequestCodeFeeding.putBoolean("prefsRequestCodeFeeding", false);
                        prefsEditorRequestCodeFeeding.apply();
                    }


                    NotificationFeeding notificationFeeding = new NotificationFeeding();
                    notificationFeeding.setUpNotificationFeeding(notificationsActivity);
                    ifTimeSet = true;

                    if (!FlagSetupFeeding.getFlagIsNotificationFirst()) {

                        setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, notificationsActivity);

                        Alerts alert = new Alerts();
                        alert.simpleInfo("Dodano nowe powiadomienie", "Pomyślnie dodano nowe powiadomienie!", notificationsActivity);
                        FlagSetupFeeding.setFlagIsNotificationFirst(true);
                    } else {
                        FlagSetupFeeding.setFlagIsNotificationFirst(false);

                        notificationFeeding(notificationsActivity, textView3_notifications,
                                textView4_notifications, checkBoxNotifications2);
                    }

                    db.close();

                }
            };


            TimePickerDialog timePickerDialog = new TimePickerDialog(notificationsActivity, onTimeSetListener, hour, minute, true);

            timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (!ifTimeSet) {
                        System.out.println("DISSMIS");
                        checkBoxNotifications2.setChecked(false);
                        setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, notificationsActivity);
                    }
                }
            });


            timePickerDialog.setTitle("Wybierz godzinę, o której dostaniesz powiadomienia");
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
            SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
            prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", false);
            prefsEditorNotificationFeeding.apply();

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

            Long nextNotificationTime = daoNotifications.getNextNotificationTimeFeeding();

            textView3_notifications.setText("Powiadomienia są wysyłane codziennie");

            textView4_notifications.setText("Godziny wysyłania powiadomień: " + String.format(Locale.getDefault(), "%02d:%02d", hour1, minute1));
            textView4_notifications.append(" oraz " + String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
            textView4_notifications.append("\nNastępne powiadomienie: " + DateFormat.formatTimestampToDate(nextNotificationTime));


        }
        db.close();
    }

}
