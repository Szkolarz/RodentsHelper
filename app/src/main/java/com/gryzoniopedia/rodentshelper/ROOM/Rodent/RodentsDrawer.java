package com.gryzoniopedia.rodentshelper.ROOM.Rodent;

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
                finish();
                break;
            case R.id.nav_database_management:
                Intent intentDb = new Intent(activity, ActivityDatabaseManagement.class);
                activity.startActivity(intentDb);
                finish();
                break;
            case R.id.nav_filter:
                Intent intentFilter = new Intent(activity, ActivityDatabaseManagement.class);
                activity.startActivity(intentFilter);
                finish();
                break;
            case R.id.nav_start_settings:
                alerto();
                break;
            case R.id.nav_about_app:
                Intent intentAboutApp = new Intent(activity, ActivityAboutApp.class);
                activity.startActivity(intentAboutApp);
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    private void alerto () {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        View inflateView;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflateView = inflater.inflate(R.layout.btn_start_settings, null);

        dialog.setCustomTitle(inflateView);
        SwitchCompat switch_encyclopedia = inflateView.findViewById(R.id.switch_encyclopedia);
        AlertDialog alertDialog = dialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        RadioGroup radioGroupStartup_rodents = inflateView.findViewById(R.id.radioGroupStartup_rodents);
        RadioButton radioButtonPets_rodents = inflateView.findViewById(R.id.radioButtonPets_rodents);
        RadioButton radioButtonEncyclopedia_rodents = inflateView.findViewById(R.id.radioButtonEncyclopedia_rodents);

        SharedPreferences spRodentsRadioStartup = activity.getSharedPreferences("spRodentsRadioStartup", MODE_PRIVATE);
        radioButtonEncyclopedia_rodents.getText();
        if (spRodentsRadioStartup.getInt("spRodentsRadioStartup", 1) == 1)
            radioButtonPets_rodents.setChecked(true);
        if (spRodentsRadioStartup.getInt("spRodentsRadioStartup", 1) == 2)
            radioButtonEncyclopedia_rodents.setChecked(true);

        RadioButton radioButton;
        int selectedRadio = radioGroupStartup_rodents.getCheckedRadioButtonId();
        radioButton = inflateView.findViewById(selectedRadio);
        System.out.println(selectedRadio + " uj");
        radioButton.setTextColor(Color.parseColor("#FFFFFF"));
        CompoundButtonCompat.setButtonTintList(radioButton, ContextCompat.getColorStateList(activity, R.color.white));

    }



}
