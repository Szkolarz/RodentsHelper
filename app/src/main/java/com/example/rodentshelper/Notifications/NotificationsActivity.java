package com.example.rodentshelper.Notifications;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationFeeding;
import com.example.rodentshelper.Notifications.SettingUpAlarms.NotificationWeight;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

public class NotificationsActivity extends AppCompatActivity {

    public static final Integer POST_NOTIFICATIONS = 1;

    ImageButton imageButtonQuestion_notifications1, imageButtonQuestion_notifications2,
                imageButtonQuestion_notifications3, imageButtonVisit_notifications;
    CheckBox checkBoxNotifications1, checkBoxNotifications2;
    TextView textView4_other, textView1_notifications, textView2_notifications,
             textView3_notifications, textView4_notifications;



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
        textView3_notifications = findViewById(R.id.textView3_notifications);
        textView4_notifications = findViewById(R.id.textView4_notifications);

        checkBoxNotifications1 = findViewById(R.id.checkBoxNotifications1);
        checkBoxNotifications2 = findViewById(R.id.checkBoxNotifications2);
        imageButtonVisit_notifications = findViewById(R.id.imageButtonVisit_notifications);

        imageButtonQuestion_notifications1 = findViewById(R.id.imageButtonQuestion_notifications1);
        imageButtonQuestion_notifications2 = findViewById(R.id.imageButtonQuestion_notifications2);
        imageButtonQuestion_notifications3 = findViewById(R.id.imageButtonQuestion_notifications3);

        Alerts alertInfo = new Alerts();
        imageButtonQuestion_notifications1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertInfo.simpleInfo("Powiadomienia o ważeniu",
                        "Systematyczne ważenie zwierzęcia jest bardzo istotnym elementem pozwalającym " +
                                "mieć wgląd na zdrowie pupila. W wielu przypadkach utrata wagi może " +
                                "wskazywać na jakąś chorobę, dlatego warto profilaktycznie ważyć zwierzę, by móc " +
                                "przedwcześnie zapobiegać problemom zdrowotnym dzięki wizycie u weterynarza." +
                                "\n\nMiej na uwadze, że powiadomienia są jedynie czystą informacją i przypomnieniem; " +
                                "każde znaczne odchylenie wagi od normy powinno być niezwłocznie skonsultowane z " +
                                "weterynarzem. Poglądową tabelę prawidłowej wagi wraz z możliwością zapisywania jej " +
                                "znajdziesz kolejno w opcjach: 'Pupile' > 'Opieka' > 'Ważenie'.",
                         NotificationsActivity.this);
            }
        });

        imageButtonQuestion_notifications2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertInfo.simpleInfo("Powiadomienia o karmieniu",
                        "Każdy gryzoń musi być codziennie karmiony, z reguły dwa razy dziennie. " +
                                "Dlatego też, po włączeniu powiadomienia zostaną wyświetlone dwa następujące" +
                                "po sobie zegary, w których należy ustawić godzinę przypomnienia poranną oraz" +
                                "wieczorną.\n\nMiej na uwadze, że powiadomienia są jedynie czystą informacją i przypomnieniem; " +
                                "ilość podawanej karmy powinno się odpowiednio dostosowywać - ważne, żeby pupil miał " +
                                "do niej dostęp 24 godziny na dobę, lecz jednocześnie należy pamiętać o nie przekarmianiu " +
                                "zwierzęcia. Ilość podawanego pokarmu jest więc kwestią indywidualną dla każdego pupila.",
                         NotificationsActivity.this);
            }
        });

        imageButtonQuestion_notifications3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertInfo.simpleInfo("Powiadomienia o wizytach u weterynarza",
                        "Do każdej zapisanej wizyty u weterynarza możesz przypisać powiadomienie " +
                                "uruchamiające się odpowiednio wcześnie. Przy dodawaniu (lub edycji) wizyty, w panelu 'Zdrowie' > " +
                                "'Wizyty u weterynarza', wystarczy podać jej datę oraz godzinę. Zostanie wówczas " +
                                "wyświetlony specjalny przycisk na dole ekranu pozwalający ustalić, o ile wcześniej " +
                                "od ustalonej wizyty aplikacja ma wysłać przypomnienie w formie powiadomienia.",
                         NotificationsActivity.this);
            }
        });

        imageButtonVisit_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });

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

        SetUpNotificationsWeight setUpNotificationsWeight = new SetUpNotificationsWeight();
        setUpNotificationsWeight.setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, NotificationsActivity.this);

        SetUpNotificationsFeeding setUpNotificationsFeeding = new SetUpNotificationsFeeding();
        setUpNotificationsFeeding.setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, NotificationsActivity.this);



        checkBoxNotifications1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Notifications1 notifications1 = new Notifications1();
                //notifications1.showNotification(NotificationsActivity.this);



                if (checkBoxNotifications1.isChecked()) {
                    checkBoxNotifications1.setText("Włączone");
                    setUpNotificationsWeight.notificationWeight(NotificationsActivity.this, textView1_notifications,
                            textView2_notifications, checkBoxNotifications1);

                } else {
                    SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
                    prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", false);
                    prefsEditorNotificationWeight.apply();

                    NotificationWeight notificationWeight = new NotificationWeight();
                    //it's turning off alarm in 'if'
                    notificationWeight.setUpNotificationWeight(NotificationsActivity.this);
                    setUpNotificationsWeight.setUpCheckbox(checkBoxNotifications1, textView1_notifications, textView2_notifications, NotificationsActivity.this);
                }

            }

        });

        checkBoxNotifications2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (checkBoxNotifications2.isChecked()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(NotificationsActivity.this, R.style.AlertDialogStyleUpdate);
                    alert.setTitle("Ustawianie godzin");
                    alert.setMessage("Za moment zostaną wyświetlone dwa zegary następujące po sobie. Gryzonie " +
                            "należy karmić (z reguły) dwa razy dziennie, dlatego możesz wybrać godzinę poranną oraz wieczorną.");

                    alert.setPositiveButton("Rozumiem", (dialogInterface, i) -> {
                        checkBoxNotifications2.setText("Włączone");
                        FlagSetupFeeding.setFlagIsNotificationFirst(true);
                        setUpNotificationsFeeding.notificationFeeding(NotificationsActivity.this, textView3_notifications,
                                textView4_notifications, checkBoxNotifications2);
                    });
                    alert.show();



                } else {
                    SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
                    prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", false);
                    prefsEditorNotificationFeeding.apply();

                    NotificationFeeding notificationFeeding = new NotificationFeeding();

                    //it set ups two different times given by user

                    notificationFeeding.setUpNotificationFeeding(NotificationsActivity.this);

                    System.out.println("WYLACZONE");
                    //it's turning off alarm in 'if'
                    setUpNotificationsFeeding.setUpCheckbox(checkBoxNotifications2, textView3_notifications, textView4_notifications, NotificationsActivity.this);
                }

            }

        });


    }

    public void viewVisits()
    {
        Intent intent = new Intent(NotificationsActivity.this, ViewVisits.class);
        startActivity(intent);
    }


}