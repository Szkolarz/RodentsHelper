package com.example.rodentshelper.MainViews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.Encyclopedia.InternetCheckEncyclopedia;
import com.example.rodentshelper.Encyclopedia.Common.ViewTreats;
import com.example.rodentshelper.Encyclopedia.VersionCodeCheck;
import com.example.rodentshelper.ImageCompress;
import com.example.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Sprawdzanie aktualizacji...");
        progress.setMessage("Proszę czekać...");

        if (internetCheckEncyclopedia.isNetworkConnected(ViewEncyclopedia.this))
            progress.show();


            Thread thread = new Thread(() -> {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            internetCheckEncyclopedia.checkInternet(viewEncyclopedia, linearLayout_encyclopedia,
                                    progressBar_encyclopedia, textViewProgress_encyclopedia, progress);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            });

            thread.start();




        imageButtonGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVets();
            }
        });

        imageButtonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTreats();
            }
        });

        imageButtonSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCageSupply();
            }
        });

        imageButtonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });






    }

    //public void onClickNavHealth(View view) {}

    public void onClickNavHealth(View view)
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewHealth.class);
        startActivity(intent);
    }

    public void onClickNavRodent(View view)
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
        startActivity(intent);
    }

    public void onClickNavEncyclopedia(View view) {}

    public void onClickNavOther(View view) {}



    public void viewVets()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewTreats()
    {
        FragmentFlag.setEncyclopediaTypeFlag(2);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewTreats.class);
        startActivity(intent);
    }

    public void viewCageSupply()
    {
        FragmentFlag.setEncyclopediaTypeFlag(3);
        Intent intent = new Intent(ViewEncyclopedia.this, ViewTreats.class);
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



    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }*/


}