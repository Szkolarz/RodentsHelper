package com.gryzoniopedia.rodentshelper.MainViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.ActivityProgressBar;
import com.gryzoniopedia.rodentshelper.DatabaseManagement.ActivityDatabaseManagement;
import com.gryzoniopedia.rodentshelper.MainViews.GoogleMaps.GoogleMaps;
import com.gryzoniopedia.rodentshelper.Notifications.NotificationsActivity;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;

import java.util.Objects;

public class ViewOther extends AppCompatActivity {

    private LinearLayout linearLayoutLoadingMapProgress;
    private ProgressBar progressBarMapLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Inne");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewOther.this, ViewRodents.class);
                startActivity(intent);
                finish();
            }
        });



        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;
        linearLayoutLoadingMapProgress = findViewById(R.id.linearLayoutLoadingMapProgress);
        progressBarMapLoading = findViewById(R.id.progressBarMapLoading);

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

        ImageView imageButtonOther_map, imageButtonOther_notifications;
        TextView textView4_other;

        textView4_other = findViewById(R.id.textView4_other);
        imageButton4_other.setColorFilter(Color.WHITE);
        textView4_other.setTextColor(Color.WHITE);

        imageButtonOther_map = findViewById(R.id.imageButtonOther_map);
        imageButtonOther_notifications = findViewById(R.id.imageButtonOther_notifications);

        imageButtonOther_map.setOnClickListener(view -> viewMap(linearLayoutLoadingMapProgress, progressBarMapLoading));
        imageButtonOther_notifications.setOnClickListener(view -> viewNotifications());
    }


    private void viewMap(LinearLayout linearLayoutLoadingMapProgress, ProgressBar progressBarMapLoading) {

        runOnUiThread(() -> {
            linearLayoutLoadingMapProgress.setVisibility(View.VISIBLE);
            progressBarMapLoading.setIndeterminate(true);
        });

        Thread threadActivity = new Thread(() -> runOnUiThread(() -> {
            Intent intent = new Intent(this, GoogleMaps.class);
            startActivity(intent);

        })); threadActivity.start();
    }


    @Override
    public void onStop() {
        super.onStop();

        linearLayoutLoadingMapProgress.setVisibility(View.GONE);
        progressBarMapLoading.setIndeterminate(false);
    }


    private void viewNotifications()
    {
        Intent intent = new Intent(ViewOther.this, NotificationsActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ViewOther.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}