package com.example.rodentshelper.Notifications;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.R;


import java.util.Calendar;
import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity {

    public static final Integer POST_NOTIFICATIONS = 1;

    ImageButton imageButtonQuestion_notifications1, imageButtonQuestion_notifications2,
                imageButtonQuestion_notifications3, imageButtonVisit_notifications;
    CheckBox checkBoxNotifications1, checkBoxNotifications2;
    TextView textView4_other, textView2_notifications;

    ProgressBar progressBar_encyclopedia;
    LinearLayout linearLayout_encyclopedia;

    private int hour, minute;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

        textView4_other = findViewById(R.id.textView4_other);
        imageButton4_other.setColorFilter(Color.WHITE);
        textView4_other.setTextColor(Color.WHITE);

        textView2_notifications = findViewById(R.id.textView2_notifications);
        checkBoxNotifications1 = findViewById(R.id.checkBoxNotifications1);
        checkBoxNotifications2 = findViewById(R.id.checkBoxNotifications2);



        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());

        boolean areNotificationsEnabled = managerCompat.areNotificationsEnabled();

        if (!areNotificationsEnabled) {
            System.out.println("dsfdfs");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    POST_NOTIFICATIONS);
        }



        checkBoxNotifications1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Notifications1 notifications1 = new Notifications1();
                //notifications1.showNotification(NotificationsActivity.this);


                Notifications1 notifications1 = new Notifications1();
                notifications1.showNotification(NotificationsActivity.this);



                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        textView2_notifications.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationsActivity.this, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Wybierz godzinę");
                timePickerDialog.show();
            }
        });








    }




}