package com.example.rodentshelper.DatabaseManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

import java.io.File;
import java.io.IOException;
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


        buttonImport = findViewById(R.id.buttonImport);
        buttonExport = findViewById(R.id.buttonExport);


        buttonExport.setOnClickListener(view -> {

            File dbMain = getDatabasePath("rodents_helper");
            File dbShm = getDatabasePath("rodents_helper-shm");
            File dbWal = getDatabasePath("rodents_helper-wal");

            ExportAndImport exportAndImport = new ExportAndImport();
            exportAndImport.exportDatabase(dbMain, dbShm, dbWal);
        });


        buttonImport.setOnClickListener(view -> {
            System.out.println(getDatabasePath("rodents_helper") + " database path");


            ExportAndImport exportAndImport = new ExportAndImport();

            String s = getDatabasePath("rodents_helper").toString();
            String s1 = s.substring(0,s.lastIndexOf("/") + 1);
            s1.trim();
            File databaseDirectory = new File(s1);


             ActivityDatabaseManagement.this.getApplicationContext().deleteDatabase("rodents_helper"); //<<<< ADDED before building Database.


            exportAndImport.deleteRecursive(databaseDirectory);

            try {
                exportAndImport.importDatabase(ActivityDatabaseManagement.this, "ap", databaseDirectory);
            } catch (IOException | InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }






        });





    }





}