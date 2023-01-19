package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class FirstStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Wybierz zwierzę");
        setSupportActionBar(toolbar);


        ImageView imageButtonHamster = findViewById(R.id.imageButtonGuineaPig);
        ImageView imageButtonRat = findViewById(R.id.imageButtonRat);
        ImageView imageButtonChinchilla = findViewById(R.id.imageButtonChinchilla);

        //1 = guinea pig
        //2 = rat
        //3 = chinchilla
        SharedPreferences spFirstStart = getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        //int firstStart = spFirstStart.getInt("firstStart", 1);


        imageButtonHamster.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 1);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);
            finish();

        });

        imageButtonRat.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 2);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);
            finish();

        });

        imageButtonChinchilla.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 3);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);
            finish();

        });

    }


}
