package com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.ColumnInfo;

import com.example.rodentshelper.R;
import com.google.android.material.navigation.NavigationView;
import com.gryzoniopedia.rodentshelper.ActivityAboutApp;
import com.gryzoniopedia.rodentshelper.DatabaseManagement.ActivityDatabaseManagement;
import com.gryzoniopedia.rodentshelper.MainViews.FirstStart;
import com.gryzoniopedia.rodentshelper.MainViews.ViewEncyclopedia;
import com.gryzoniopedia.rodentshelper.MainViews.ViewHealth;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

import java.sql.Date;

public class RodentsDrawer extends ViewRodents implements NavigationView.OnNavigationItemSelectedListener {

    public Context activityGlobal;


    private final Toolbar toolbar;
    private final Activity activity;

    public RodentsDrawer(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
    }




    public void createDrawer () {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawerlayout);

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigationView_rodents);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_choose_rodent:
                Intent intent = new Intent(activity, FirstStart.class);
                activity.startActivity(intent);
                activity.finish();
                break;
            case R.id.nav_database_management:
                Intent intentDb = new Intent(activity, ActivityDatabaseManagement.class);
                activity.startActivity(intentDb);
                activity.finish();
                break;
            case R.id.nav_filter:
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
                View inflateView = inflater.inflate(R.layout.custom_dialog_rodents_filter, null);
                RodentsFilter rodentsFilter = new RodentsFilter();
                rodentsFilter.setUpFilter(activity, inflateView);
                break;
            case R.id.nav_start_settings:
                alertWithRodentFilter();
                break;
            case R.id.nav_about_app:
                Intent intentAboutApp = new Intent(activity, ActivityAboutApp.class);
                activity.startActivity(intentAboutApp);
                activity.finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    private void alertWithRodentFilter () {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        View inflateView;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflateView = inflater.inflate(R.layout.btn_start_settings, null);

        dialog.setCustomTitle(inflateView);

        AlertDialog alertDialog = dialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        RadioButton radioButtonPets_rodents = inflateView.findViewById(R.id.radioButtonPets_rodents);
        RadioButton radioButtonEncyclopedia_rodents = inflateView.findViewById(R.id.radioButtonEncyclopedia_rodents);
        RadioButton radioButtonMap_rodents = inflateView.findViewById(R.id.radioButtonMap_rodents);

        ImageButton imageButtonRodentSetting = inflateView.findViewById(R.id.imageButtonRodentSetting);
        ImageButton imageButtonEncyclopediaSetting = inflateView.findViewById(R.id.imageButtonEncyclopediaSetting);
        ImageButton imageButtonMapSetting = inflateView.findViewById(R.id.imageButtonMapSetting);


        radioButtonPets_rodents.setOnClickListener(v -> {
            radioButtonPets_rodents.setChecked(true);
            radioButtonEncyclopedia_rodents.setChecked(false);
            radioButtonMap_rodents.setChecked(false);
            setPrefsStartSettings(1);
            colorRadios(radioButtonPets_rodents, radioButtonEncyclopedia_rodents, radioButtonMap_rodents,
                    imageButtonRodentSetting, imageButtonEncyclopediaSetting, imageButtonMapSetting);
        });

        radioButtonEncyclopedia_rodents.setOnClickListener(v -> {
            radioButtonEncyclopedia_rodents.setChecked(true);
            radioButtonPets_rodents.setChecked(false);
            radioButtonMap_rodents.setChecked(false);
            setPrefsStartSettings(2);
            colorRadios(radioButtonPets_rodents, radioButtonEncyclopedia_rodents, radioButtonMap_rodents,
                    imageButtonRodentSetting, imageButtonEncyclopediaSetting, imageButtonMapSetting);
        });

        radioButtonMap_rodents.setOnClickListener(v -> {
            radioButtonMap_rodents.setChecked(true);
            radioButtonPets_rodents.setChecked(false);
            radioButtonEncyclopedia_rodents.setChecked(false);
            setPrefsStartSettings(3);
            colorRadios(radioButtonPets_rodents, radioButtonEncyclopedia_rodents, radioButtonMap_rodents,
                    imageButtonRodentSetting, imageButtonEncyclopediaSetting, imageButtonMapSetting);
        });


        colorRadios(radioButtonPets_rodents, radioButtonEncyclopedia_rodents, radioButtonMap_rodents,
                imageButtonRodentSetting, imageButtonEncyclopediaSetting, imageButtonMapSetting);

    }


    private void colorRadios (RadioButton radioButtonPets_rodents, RadioButton radioButtonEncyclopedia_rodents,
                              RadioButton radioButtonMap_rodents, ImageView imageButtonRodentSetting,
                              ImageView imageButtonEncyclopediaSetting, ImageView imageButtonMapSetting) {

        SharedPreferences spStartSettings = activity.getSharedPreferences("spStartSettings", MODE_PRIVATE);

        System.out.println(spStartSettings.getInt("spStartSettings", 1) + " iel");

        if (spStartSettings.getInt("spStartSettings", 1) == 1)
            radioButtonPets_rodents.setChecked(true);
        else if (spStartSettings.getInt("spStartSettings", 1) == 2)
            radioButtonEncyclopedia_rodents.setChecked(true);
        else
            radioButtonMap_rodents.setChecked(true);


        if (radioButtonPets_rodents.isChecked()) {
            radioButtonEncyclopedia_rodents.setChecked(false);
            radioButtonMap_rodents.setChecked(false);

            imageButtonEncyclopediaSetting.setColorFilter(Color.parseColor("#0A4A97"));
            imageButtonMapSetting.setColorFilter(Color.parseColor("#0A4A97"));

            imageButtonRodentSetting.setColorFilter(Color.WHITE);
            radioButtonPets_rodents.setTextColor(Color.parseColor("#FFFFFF"));
            CompoundButtonCompat.setButtonTintList(radioButtonPets_rodents, ContextCompat.getColorStateList(activity, R.color.white));

            radioButtonEncyclopedia_rodents.setTextColor(Color.parseColor("#014D70"));
            radioButtonMap_rodents.setTextColor(Color.parseColor("#014D70"));

            CompoundButtonCompat.setButtonTintList(radioButtonEncyclopedia_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
            CompoundButtonCompat.setButtonTintList(radioButtonMap_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
        }
        if (radioButtonEncyclopedia_rodents.isChecked()) {
            radioButtonPets_rodents.setChecked(false);
            radioButtonMap_rodents.setChecked(false);

            imageButtonRodentSetting.setColorFilter(Color.parseColor("#0A4A97"));
            imageButtonMapSetting.setColorFilter(Color.parseColor("#0A4A97"));

            imageButtonEncyclopediaSetting.setColorFilter(Color.WHITE);

            radioButtonEncyclopedia_rodents.setTextColor(Color.parseColor("#FFFFFF"));
            CompoundButtonCompat.setButtonTintList(radioButtonEncyclopedia_rodents, ContextCompat.getColorStateList(activity, R.color.white));

            radioButtonPets_rodents.setTextColor(Color.parseColor("#014D70"));
            radioButtonMap_rodents.setTextColor(Color.parseColor("#014D70"));

            CompoundButtonCompat.setButtonTintList(radioButtonPets_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
            CompoundButtonCompat.setButtonTintList(radioButtonMap_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
        }

        if (radioButtonMap_rodents.isChecked()) {
            radioButtonPets_rodents.setChecked(false);
            radioButtonEncyclopedia_rodents.setChecked(false);

            imageButtonRodentSetting.setColorFilter(Color.parseColor("#0A4A97"));
            imageButtonEncyclopediaSetting.setColorFilter(Color.parseColor("#0A4A97"));

            imageButtonMapSetting.setColorFilter(Color.WHITE);

            radioButtonMap_rodents.setTextColor(Color.parseColor("#FFFFFF"));
            CompoundButtonCompat.setButtonTintList(radioButtonMap_rodents, ContextCompat.getColorStateList(activity, R.color.white));

            radioButtonPets_rodents.setTextColor(Color.parseColor("#014D70"));
            radioButtonEncyclopedia_rodents.setTextColor(Color.parseColor("#014D70"));

            CompoundButtonCompat.setButtonTintList(radioButtonPets_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
            CompoundButtonCompat.setButtonTintList(radioButtonEncyclopedia_rodents, ContextCompat.getColorStateList(activity, R.color.radioButtonTint));
        }


    }


    private void setPrefsStartSettings (int prefNumber) {

        SharedPreferences spStartSettings = activity.getSharedPreferences("spStartSettings", MODE_PRIVATE);
        SharedPreferences.Editor spEditorStartSettings = spStartSettings.edit();

        if (prefNumber == 1) {
            spEditorStartSettings.putInt("spStartSettings", 1);
            spEditorStartSettings.apply();
        } else if (prefNumber == 2) {
            spEditorStartSettings.putInt("spStartSettings", 2);
            spEditorStartSettings.apply();
        } else {
            spEditorStartSettings.putInt("spStartSettings", 3);
            spEditorStartSettings.apply();
        }

    }



}
