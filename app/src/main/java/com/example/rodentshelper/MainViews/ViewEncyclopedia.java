package com.example.rodentshelper.MainViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Encyclopedia.Common.ViewCagesupplyAndTreats;
import com.example.rodentshelper.Encyclopedia.Common.ViewGeneralAndDiseases;
import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.Encyclopedia.InternetCheckEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ViewEncyclopedia extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_encyclopedia, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update_encyclopedia) {//finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Encyklopedia");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
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

        ImageView imageButtonGeneral = findViewById(R.id.imageButtonGeneral);
        ImageView imageButtonFood = findViewById(R.id.imageButtonFood);
        ImageView imageButtonSupply = findViewById(R.id.imageButtonSupply);
        ImageView imageButtonDisease = findViewById(R.id.imageButtonDisease);

        TextView textView2_encyclopedia = findViewById(R.id.textView2_encyclopedia);
        imageButton2_encyclopedia.setColorFilter(Color.WHITE);
        textView2_encyclopedia.setTextColor(Color.WHITE);

        TextView textViewProgress_encyclopedia;
        ProgressBar progressBar_encyclopedia;
        LinearLayout linearLayout_encyclopedia;

        textViewProgress_encyclopedia = findViewById(R.id.textViewProgress_encyclopedia);
        progressBar_encyclopedia = findViewById(R.id.progressBar_encyclopedia);
        linearLayout_encyclopedia = findViewById(R.id.linearLayout_encyclopedia);

        ViewEncyclopedia viewEncyclopedia;
        viewEncyclopedia = ViewEncyclopedia.this;

        InternetCheckEncyclopedia internetCheckEncyclopedia = new InternetCheckEncyclopedia();

        SharedPreferences prefsFirstDownload = viewEncyclopedia.getSharedPreferences("prefsFirstDownload", Context.MODE_PRIVATE);

        final ProgressDialog progress = new ProgressDialog(this);
        if (!prefsFirstDownload.getBoolean("firstDownload", true))
        {
            progress.setTitle("Sprawdzanie aktualizacji...");
            progress.setMessage("Proszę czekać...");

            if (internetCheckEncyclopedia.isNetworkConnected(ViewEncyclopedia.this))
                progress.show();
        }


            Thread thread = new Thread(() -> runOnUiThread(() -> {
                try {
                    internetCheckEncyclopedia.checkInternet(viewEncyclopedia, linearLayout_encyclopedia,
                            progressBar_encyclopedia, textViewProgress_encyclopedia, progress);
                } catch (SQLException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }));

            thread.start();


        imageButtonGeneral.setOnClickListener(view -> {
            FragmentFlag.setEncyclopediaTypeFlag(1);
            viewGeneral();
        });

        imageButtonFood.setOnClickListener(view -> viewTreats());

        imageButtonSupply.setOnClickListener(view -> viewCageSupply());

        imageButtonDisease.setOnClickListener(view -> {
            FragmentFlag.setEncyclopediaTypeFlag(4);
            viewGeneral();
        });

    }




    public void viewGeneral()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewGeneralAndDiseases.class);
        startActivity(intent);
    }

    public void viewTreats()
    {
        FragmentFlag.setEncyclopediaTypeFlag(2);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewCagesupplyAndTreats.class);
        startActivity(intent);
    }

    public void viewCageSupply()
    {
        FragmentFlag.setEncyclopediaTypeFlag(3);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewCagesupplyAndTreats.class);
        startActivity(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}