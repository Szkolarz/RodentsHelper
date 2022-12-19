package com.example.rodentshelper.Notifications;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;
import com.example.rodentshelper.ROOM.DateFormat;

import java.util.Locale;

public class SetUpNotifications {

    private int hour, minute;
    private boolean ifTimeSet;

    public void notificationWeight (NotificationsActivity notificationsActivity,
                               TextView textView1_notifications, TextView textView2_notifications,
                               CheckBox checkBoxNotifications1) {


        ifTimeSet = false;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(notificationsActivity, R.style.AlertWhiteButtons);
        View mView = notificationsActivity.getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        mBuilder.setTitle("Częstotliwość powiadomień");
        Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner_weight);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(notificationsActivity,
                android.R.layout.simple_spinner_item,
                notificationsActivity.getResources().getStringArray(R.array.weightList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(3);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
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

                        SharedPreferences prefsNotificationWeight = notificationsActivity.getSharedPreferences("prefsNotificationWeight", notificationsActivity.MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
                        prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", true);
                        prefsEditorNotificationWeight.apply();

                        NotificationWeight notificationWeight = new NotificationWeight();
                        notificationWeight.setUpNotificationWeight(notificationsActivity);

                        setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
                        ifTimeSet = true;
                    }
                };


                TimePickerDialog timePickerDialog = new TimePickerDialog(notificationsActivity, onTimeSetListener, hour, minute, true);

                timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (!ifTimeSet) {
                            checkBoxNotifications1.setChecked(false);
                            setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
                        }
                    }
                });


                timePickerDialog.setTitle("Wybierz godzinę, o której dostaniesz powiadomienia");
                timePickerDialog.show();

            }
        });

        mBuilder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkBoxNotifications1.setChecked(false);
                setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, notificationsActivity);
                dialog.dismiss();
            }
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
            String periodicity = daoNotifications.getPeriodicityFromNotificationWeight();
            Long nextNotificationTime = daoNotifications.getNextNotificationTimeWeight();

            textView1_notifications.setText("Częstotliwość: " + periodicity);
            textView2_notifications.setText("Godzina wysyłania powiadomienia: ~" + String.format(Locale.getDefault(), "%02d:%02d", hour, minute) +
                    "\nNastępne powiadomienie: " + DateFormat.formatTimestampToDate(nextNotificationTime));

            System.out.println(nextNotificationTime + " KTÓRY");

        }
        db.close();
    }

}
