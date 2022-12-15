package com.example.rodentshelper.MainViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.Encyclopedia.InternetCheckEncyclopedia;
import com.example.rodentshelper.Encyclopedia.Common.ViewEncyclopediaData;
import com.example.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class ViewEncyclopedia extends AppCompatActivity {

    ImageView imageButtonGeneral, imageButtonFood, imageButtonSupply, imageButtonSound, imageButton2_encyclopedia;
    TextView textView2_encyclopedia, textViewProgress_encyclopedia;

    ProgressBar progressBar_encyclopedia;
    LinearLayout linearLayout_encyclopedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);


        imageButtonGeneral = findViewById(R.id.imageButtonGeneral);
        imageButtonFood = findViewById(R.id.imageButtonFood);
        imageButtonSupply = findViewById(R.id.imageButtonSupply);
        imageButtonSound = findViewById(R.id.imageButtonSound);

        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        textView2_encyclopedia = findViewById(R.id.textView2_encyclopedia);
        imageButton2_encyclopedia.setColorFilter(Color.WHITE);
        textView2_encyclopedia.setTextColor(Color.WHITE);

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


        imageButtonGeneral.setOnClickListener(view -> viewVets());

        imageButtonFood.setOnClickListener(view -> viewTreats());

        imageButtonSupply.setOnClickListener(view -> viewCageSupply());

        imageButtonSound.setOnClickListener(view -> viewVisits());

    }



    public void onClickNavRodent(View view) {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavEncyclopedia(View view) {}

    public void onClickNavHealth(View view) {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewHealth.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavOther(View view) {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewOther.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    public void viewVets()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewTreats()
    {
        FragmentFlag.setEncyclopediaTypeFlag(2);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewEncyclopediaData.class);
        startActivity(intent);
    }

    public void viewCageSupply()
    {
        FragmentFlag.setEncyclopediaTypeFlag(3);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewEncyclopediaData.class);
        startActivity(intent);
    }

    public void viewOther()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewOther.class);
        startActivity(intent);
    }

    public void viewMeds()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewMedicaments.class);
        startActivity(intent);
    }

    public void viewVisits()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewVisits.class);
        startActivity(intent);
    }

    public void viewRodents()
    {
        finish();
        Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
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