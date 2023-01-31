package com.gryzoniopedia.rodentshelper.Notifications;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.Notifications.SettingUpAlarms.NotificationWeight;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotifications;
import com.gryzoniopedia.rodentshelper.ROOM.DateFormat;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.example.rodentshelper.R;

import java.util.Locale;

public class SetUpNotificationsWeight {

    private int hour, minute;
    private boolean ifTimeSet;

    public void notificationWeight (NotificationsActivity notificationsActivity,
                               TextView textView1_notifications, TextView textView2_notifications,
                               CheckBox checkBoxNotifications1) {

        ifTimeSet = false;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(notificationsActivity, R.style.AlertWhiteButtons);
        View mView = notificationsActivity.getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        mBuilder.setTitle("Częstotliwość powiadomień");
        Spinner mSpinner = mView.findViewById(R.id.spinner_weight);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(notificationsActivity,
                android.R.layout.simple_spinner_item,
                notificationsActivity.getResources().getStringArray(R.array.weightList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(3);


        mBuilder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();

            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
                hour = selectedHour;
                minute = selectedMinute;

                AppDatabase db = Room.databaseBuilder(notificationsActivity,
                        AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                DAONotifications daoNotifications = db.daoNotifications();


                daoNotifications.deleteNotificationWeight();
                daoNotifications.insertRecordNotification(new NotificationsModel(null, hour, minute,
                        mSpinner.getSelectedItem().toString(), System.currentTimeMillis(), null,"weight"));
                db.close();
                Alerts alert = new Alerts();
                alert.simpleInfo("Dodano nowe powiadomienie", "Pomyślnie dodano nowe powiadomienie!", notificationsActivity);

                SharedPreferences prefsNotificationWeight = notificationsActivity.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
                prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", true);
                prefsEditorNotificationWeight.apply();

                NotificationWeight notificationWeight = new NotificationWeight();
                notificationWeight.setUpNotificationWeight(notificationsActivity.getApplicationContext());

                setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
                ifTimeSet = true;
            };


            TimePickerDialog timePickerDialog = new TimePickerDialog(notificationsActivity, onTimeSetListener, hour, minute, true);

            timePickerDialog.setOnDismissListener(dialog1 -> {
                if (!ifTimeSet) {
                    checkBoxNotifications1.setChecked(false);
                    setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
                }
            });


            timePickerDialog.setTitle("Wybierz godzinę, o której dostaniesz powiadomienie");
            timePickerDialog.show();

        });

        mBuilder.setNegativeButton("Anuluj", (dialog, which) -> {
            checkBoxNotifications1.setChecked(false);
            setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
            dialog.dismiss();
        });

        mBuilder.setOnCancelListener(dialog -> {
            checkBoxNotifications1.setChecked(false);
            setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();


    }

    public void setUpCheckbox(CheckBox checkbox, TextView textView1_notifications, TextView textView2_notifications,
                              NotificationsActivity notificationsActivity) {
        AppDatabase db = Room.databaseBuilder(notificationsActivity,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotifications daoNotifications = db.daoNotifications();

        if (!checkbox.isChecked()) {

            daoNotifications.deleteNotificationWeight();

            textView1_notifications.setVisibility(View.GONE);
            textView2_notifications.setVisibility(View.GONE);
            checkbox.setText("Wyłączone");
            FlagSetupFeeding.setFlagIsNotificationFirst(true);

            SharedPreferences prefsNotificationWeight = notificationsActivity.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
            prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", false);
            prefsEditorNotificationWeight.apply();
        } else {
            textView1_notifications.setVisibility(View.VISIBLE);
            textView2_notifications.setVisibility(View.VISIBLE);
            checkbox.setText("Włączone");

            Integer hour = daoNotifications.getHourFromNotificationWeight();
            Integer minute = daoNotifications.getMinuteFromNotificationWeight();
            String periodicity = daoNotifications.getSendTimeFromNotificationWeight();
            Long nextNotificationTime = daoNotifications.getNextNotificationTimeWeight();

            textView1_notifications.setText("Częstotliwość: " + periodicity);
            textView2_notifications.setText("Godzina wysyłania powiadomienia: ~" + String.format(Locale.getDefault(), "%02d:%02d", hour, minute) +
                    "\nNastępne powiadomienie: " + DateFormat.formatTimestampToDate(nextNotificationTime));


        }
        db.close();
    }

}
