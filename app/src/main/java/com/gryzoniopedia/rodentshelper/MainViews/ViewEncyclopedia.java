package com.gryzoniopedia.rodentshelper.MainViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Common.ViewCagesupplyAndTreats;
import com.gryzoniopedia.rodentshelper.Encyclopedia.FragmentFlag;
import com.gryzoniopedia.rodentshelper.Encyclopedia.InternetCheckEncyclopedia;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Common.ViewGeneralAndDiseases;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ViewEncyclopedia extends AppCompatActivity {

    private TextView textViewProgress_encyclopedia3;
    private TextView textViewProgress_encyclopedia2;

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_cogwheel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cogwheel_global) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(ViewEncyclopedia.this);

            View inflateView;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflateView = inflater.inflate(R.layout.btn_share, null);

            dialog.setCustomTitle(inflateView);
            SwitchCompat switch_encyclopedia = inflateView.findViewById(R.id.switch_encyclopedia);

            SharedPreferences prefsAutoUpdate = getSharedPreferences("prefsAutoUpdate", MODE_PRIVATE);

            if (prefsAutoUpdate.getBoolean("prefsAutoUpdate", true)) {
                switch_encyclopedia.setChecked(true);
            } else {
                switch_encyclopedia.setChecked(false);
            }

            switch_encyclopedia.setOnClickListener(v -> {
                if (switch_encyclopedia.isChecked()) {
                    SharedPreferences.Editor editorAutoUpdate = prefsAutoUpdate.edit();
                    editorAutoUpdate.putBoolean("prefsAutoUpdate", true);
                    editorAutoUpdate.apply();
                } else {
                    SharedPreferences.Editor editorAutoUpdate = prefsAutoUpdate.edit();
                    editorAutoUpdate.putBoolean("prefsAutoUpdate", false);
                    editorAutoUpdate.apply();
                }
            });
            AlertDialog alertDialog = dialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);

        FlagSetup.setAllowBackInEncyclopedia(true);

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
        ProgressBar progressBar_encyclopedia, progressBarHorizontal_encyclopedia;
        LinearLayout linearLayout_encyclopedia, linearLayoutUpdateCheck;

        textViewProgress_encyclopedia = findViewById(R.id.textViewProgress_encyclopedia);
        textViewProgress_encyclopedia3 = findViewById(R.id.textViewProgress_encyclopedia3);
        textViewProgress_encyclopedia2 = findViewById(R.id.textViewProgress_encyclopedia2);
        progressBar_encyclopedia = findViewById(R.id.progressBar_encyclopedia);
        progressBarHorizontal_encyclopedia = findViewById(R.id.progressBarHorizontal_encyclopedia);

        linearLayout_encyclopedia = findViewById(R.id.linearLayout_encyclopedia);
        linearLayoutUpdateCheck = findViewById(R.id.linearLayoutUpdateCheck);


        InternetCheckEncyclopedia internetCheckEncyclopedia = new InternetCheckEncyclopedia();

        SharedPreferences prefsFirstDownload = ViewEncyclopedia.this.getSharedPreferences("prefsFirstDownload", Context.MODE_PRIVATE);
        SharedPreferences prefsAutoUpdate = ViewEncyclopedia.this.getSharedPreferences("prefsAutoUpdate", Context.MODE_PRIVATE);
        boolean isAutoUpdateOn = prefsAutoUpdate.getBoolean("prefsAutoUpdate", true);


        if (!prefsFirstDownload.getBoolean("firstDownload", true) && isAutoUpdateOn) {

            if (internetCheckEncyclopedia.isNetworkConnected(ViewEncyclopedia.this)) {
                linearLayoutUpdateCheck.setVisibility(View.VISIBLE);
                progressBarHorizontal_encyclopedia.setIndeterminate(true);
            }

        }


            Thread thread = new Thread(() -> runOnUiThread(() -> {
                try {
                    internetCheckEncyclopedia.checkInternet(ViewEncyclopedia.this, linearLayout_encyclopedia,
                            progressBar_encyclopedia, textViewProgress_encyclopedia, textViewProgress_encyclopedia2,
                            textViewProgress_encyclopedia3, linearLayoutUpdateCheck);
                } catch (SQLException | ExecutionException | InterruptedException e) {
                    Log.e("ViewEncyclopedia", Log.getStackTraceString(e));
                }
            }));

            thread.start();


        imageButtonGeneral.setOnClickListener(view -> {
            FragmentFlag.setEncyclopediaTypeFlag(1);
            viewGeneral();
        });

        imageButtonFood.setOnClickListener(view -> {
            FragmentFlag.setEncyclopediaTypeFlag(2);
            viewTreats();
        });

        imageButtonSupply.setOnClickListener(view -> {
            FragmentFlag.setEncyclopediaTypeFlag(3);
            viewCageSupply();
        });

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
        Intent intent = new Intent(ViewEncyclopedia.this, ViewCagesupplyAndTreats.class);
        startActivity(intent);
    }

    public void viewCageSupply()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewCagesupplyAndTreats.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        if (FlagSetup.getAllowBackInEncyclopedia()) {
            Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
            startActivity(intent);
            finish();
        } else {
            textViewProgress_encyclopedia3.setVisibility(View.VISIBLE);
        }
    }


}