package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.health.HealthStats;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

public class ViewHealth extends AppCompatActivity {

    ImageView imageButtonVet1, imageButtonMed, imageButtonVisit, imageButton3_health;
    TextView textView3_health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        //flag reset for proper working
        FlagSetup.setFlagIsFromHealth(true);
        FlagSetup.setFlagRodentAdd(0);
        FlagSetup.setFlagVetAdd(0);
        FlagSetup.setFlagVisitAdd(0);
        FlagSetup.setFlagMedAdd(0);


        imageButtonVet1 = findViewById(R.id.imageButtonVet1);
        imageButtonMed = findViewById(R.id.imageButtonMed);
        imageButtonVisit = findViewById(R.id.imageButtonVisit);


        imageButton3_health = findViewById(R.id.imageButton3_health);
        textView3_health = findViewById(R.id.textView3_health);
        imageButton3_health.setColorFilter(Color.WHITE);
        textView3_health.setTextColor(Color.WHITE);


        imageButtonVet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVets();
            }
        });

        imageButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMeds();
            }
        });

        imageButtonVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });






    }

    public void onClickNavRodent(View view) {
        Intent intent = new Intent(ViewHealth.this, ViewRodents.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavEncyclopedia(View view) {
        Intent intent = new Intent(ViewHealth.this, ViewEncyclopedia.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavHealth(View view) {

    }

    public void onClickNavOther(View view) {
        Intent intent = new Intent(ViewHealth.this, ViewOther.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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