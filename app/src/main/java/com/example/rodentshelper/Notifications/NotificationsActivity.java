package com.example.rodentshelper.Notifications;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotifications;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.Vet.VetModel;


import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity {

    public static final Integer POST_NOTIFICATIONS = 1;

    ImageButton imageButtonQuestion_notifications1, imageButtonQuestion_notifications2,
                imageButtonQuestion_notifications3, imageButtonVisit_notifications;
    CheckBox checkBoxNotifications1, checkBoxNotifications2;
    TextView textView4_other, textView1_notifications, textView2_notifications;

    ProgressBar progressBar_encyclopedia;
    LinearLayout linearLayout_encyclopedia;

    private int hour, minute;
    private boolean ifTimeSet = false;



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

        textView1_notifications = findViewById(R.id.textView1_notifications);
        textView2_notifications = findViewById(R.id.textView2_notifications);
        checkBoxNotifications1 = findViewById(R.id.checkBoxNotifications1);
        checkBoxNotifications2 = findViewById(R.id.checkBoxNotifications2);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());

        boolean areNotificationsEnabled = managerCompat.areNotificationsEnabled();

        if (!areNotificationsEnabled) {

            AlertDialog.Builder alert = new AlertDialog.Builder(NotificationsActivity.this, R.style.AlertDialogStyleUpdate);
            alert.setTitle("Uprawnienia powiadomień");
            alert.setMessage("Android 13 oraz wyższe wersje wymagają odpowiednich uprawnień w ustawieniach telefonu, "+
                    "aby móc wyświetlać powiadomienia. W następnym okienku musisz zezwolić na uprawnienia, "+
                    "by móc korzystać z tego modułu.\n\nW przypadku odmówienia uprawnień możesz je przypisać "+
                    "ręcznie w ustawieniach systemu Android.");

            alert.setPositiveButton("Rozumiem", (dialogInterface, i) -> {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        POST_NOTIFICATIONS);
            });
            alert.show();
        }


        SharedPreferences prefsNotificationWeight = getSharedPreferences("prefsNotificationWeight", MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding = getSharedPreferences("prefsNotificationFeeding", MODE_PRIVATE);

        //if = true
        if (prefsNotificationWeight.getBoolean("prefsNotificationWeight", false)) {
            checkBoxNotifications1.setChecked(true);
        }
        if (prefsNotificationFeeding.getBoolean("prefsNotificationFeeding", false)) {
            checkBoxNotifications2.setChecked(true);
        }

        SetUpNotifications setUpNotifications = new SetUpNotifications();
        setUpNotifications.setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, NotificationsActivity.this);

        checkBoxNotifications1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Notifications1 notifications1 = new Notifications1();
                //notifications1.showNotification(NotificationsActivity.this);



                if (checkBoxNotifications1.isChecked()) {
                    checkBoxNotifications1.setText("Włączone");
                    setUpNotifications.notificationWeight(NotificationsActivity.this, textView1_notifications,
                            textView2_notifications, checkBoxNotifications1);

                } else {
                    SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
                    prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", false);
                    prefsEditorNotificationWeight.apply();

                    NotificationWeight notificationWeight = new NotificationWeight();
                    //it's turning off alarm in 'if'
                    notificationWeight.setUpNotificationWeight(NotificationsActivity.this);
                    setUpNotifications.setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, NotificationsActivity.this);
                }

            }

        });

    }

}