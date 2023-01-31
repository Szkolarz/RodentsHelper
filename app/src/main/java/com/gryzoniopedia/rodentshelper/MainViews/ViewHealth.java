package com.gryzoniopedia.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.ViewVets;
import com.gryzoniopedia.rodentshelper.ROOM.Visits.ViewVisits;

import java.util.Objects;

public class ViewHealth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Zdrowie");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ViewHealth.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

        //flag reset for proper working
        FlagSetup.setFlagIsFromHealth(true);
        System.out.println(FlagSetup.getFlagIsFromHealth() + "QQQQ");
        FlagSetup.setFlagRodentAdd(0);
        FlagSetup.setFlagVetAdd(0);
        FlagSetup.setFlagVisitAdd(0);
        FlagSetup.setFlagMedAdd(0);

        ImageView imageButtonVet1, imageButtonMed, imageButtonVisit;
        TextView textView3_health;

        imageButtonVet1 = findViewById(R.id.imageButtonVet1);
        imageButtonMed = findViewById(R.id.imageButtonMed);
        imageButtonVisit = findViewById(R.id.imageButtonVisit);


        textView3_health = findViewById(R.id.textView3_health);
        imageButton3_health.setColorFilter(Color.WHITE);
        textView3_health.setTextColor(Color.WHITE);


        imageButtonVet1.setOnClickListener(view -> viewVets());

        imageButtonMed.setOnClickListener(view -> viewMeds());

        imageButtonVisit.setOnClickListener(view -> viewVisits());
    }



    public void viewVets()
    {
        Intent intent = new Intent(ViewHealth.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewMeds()
    {
        Intent intent = new Intent(ViewHealth.this, ViewMedicaments.class);
        startActivity(intent);
    }

    public void viewVisits()
    {
        Intent intent = new Intent(ViewHealth.this, ViewVisits.class);
        startActivity(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ViewHealth.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}