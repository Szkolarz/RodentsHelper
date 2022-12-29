package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class FirstStart extends AppCompatActivity {

    private ImageButton imageButtonHamster, imageButtonRat, imageButtonChinchilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        imageButtonHamster = findViewById(R.id.imageButtonHamster);
        imageButtonRat = findViewById(R.id.imageButtonRat);
        imageButtonChinchilla = findViewById(R.id.imageButtonChinchilla);

        //1 = hamster
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
