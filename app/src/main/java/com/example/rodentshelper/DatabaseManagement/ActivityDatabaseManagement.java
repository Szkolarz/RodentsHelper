package com.example.rodentshelper.DatabaseManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.MainViews.GoogleMaps.GoogleMaps;
import com.example.rodentshelper.Notifications.NotificationsActivity;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.Objects;

public class ActivityDatabaseManagement extends AppCompatActivity {

    private Button buttonImport, buttonExport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Import/eksport danych");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDatabaseManagement.this, ViewRodents.class);
                startActivity(intent);
                finish();
            }
        });



        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        buttonImport = findViewById(R.id.buttonImport);
        buttonExport = findViewById(R.id.buttonExport);



        buttonExport.setOnClickListener(view -> {
            System.out.println(getDatabasePath("rodents_helper") + " database path");

            File s = getDatabasePath("rodents_helper");
            ExportDatabase exportDatabase = new ExportDatabase();
            exportDatabase.exportDatabase(s);
        });


        buttonImport.setOnClickListener(view -> {
            System.out.println(getDatabasePath("rodents_helper") + " database path");


            ExportDatabase exportDatabase = new ExportDatabase();

            String s = getDatabasePath("rodents_helper").toString();
            String s1 = s.substring(0,s.lastIndexOf("/") + 1);
            s1.trim();
            File databaseDirectory = new File(s1);
            exportDatabase.deleteRecursive(databaseDirectory);

            try {
                exportDatabase.importDatabase(ActivityDatabaseManagement.this, "a1", databaseDirectory);
            } catch (IOException | InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }






        });





    }





}