package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.R;

public class FirstStart extends AppCompatActivity {

    private ImageButton imageButtonHamster, imageButtonRabbit, imageButtonChinchilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        imageButtonHamster = findViewById(R.id.imageButtonHamster);
        imageButtonRabbit = findViewById(R.id.imageButtonRabbit);
        imageButtonChinchilla = findViewById(R.id.imageButtonChinchilla);

        //1 = hamster
        //2 = rabbit
        //3 = chinchilla
        SharedPreferences spFirstStart = getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        //int firstStart = spFirstStart.getInt("firstStart", 1);


        imageButtonHamster.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 1);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);

        });

        imageButtonRabbit.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 2);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);

        });

        imageButtonChinchilla.setOnClickListener(view -> {

            SharedPreferences.Editor spEitorFirstStart = spFirstStart.edit();
            spEitorFirstStart.putInt("prefsFirstStart", 3);
            spEitorFirstStart.apply();

            Intent intent = new Intent(FirstStart.this, ViewRodents.class);
            startActivity(intent);

        });

    }
}