package com.example.rodentshelper.DatabaseManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ActivityDatabaseManagement extends AppCompatActivity {

    private Button buttonImport, buttonExport,
            buttonLogin_importExport, buttonRegister_importExport;
    private LinearLayout linearLayout_notLogged, linearLayout_logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Import/eksport danych");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textViewLoginName_export_import, textViewExportDate_export_import, textViewImportDate_export_import;
        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other, imageViewLogOff;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);


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
        buttonLogin_importExport = findViewById(R.id.buttonLogin_importExport);
        buttonRegister_importExport = findViewById(R.id.buttonRegister_importExport);


        linearLayout_notLogged = findViewById(R.id.linearLayout_notLogged);
        linearLayout_logged = findViewById(R.id.linearLayout_logged);


        SharedPreferences prefsCloudSave = getApplicationContext().getSharedPreferences("prefsCloudSave", Context.MODE_PRIVATE);

        //if user is logged on
        if (prefsCloudSave.getBoolean("prefsCloudSave", false)) {
            linearLayout_logged.setVisibility(View.VISIBLE);

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO dao = db.dao();

            List<CloudAccountModel> cloudAccountModel;
            cloudAccountModel = dao.getAllCloudAccountData();
            db.close();

            imageViewLogOff = findViewById(R.id.imageViewLogOff);
            textViewLoginName_export_import = findViewById(R.id.textViewLoginName_export_import);
            textViewExportDate_export_import = findViewById(R.id.textViewExportDate_export_import);
            textViewImportDate_export_import = findViewById(R.id.textViewImportDate_export_import);
            SharedPreferences prefsLoginName = getApplicationContext().getSharedPreferences("prefsLoginName", Context.MODE_PRIVATE);
            textViewLoginName_export_import.setText(prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));


            for (int i=0; i<cloudAccountModel.size(); i++) {
                if (prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy").equals(cloudAccountModel.get(i).getLogin())) {
                    try {
                        textViewExportDate_export_import.setText(DateFormat.formatDate(cloudAccountModel.get(i).getExport_date()));
                    } catch (NullPointerException e){
                        System.out.println("Empty export; error: " + e);
                    }
                    try {
                        textViewImportDate_export_import.setText(DateFormat.formatDate(cloudAccountModel.get(i).getImport_date()));
                    } catch (NullPointerException e){
                        System.out.println("Empty import; error: " + e);
                    }
                }

            }

            buttonExport.setOnClickListener(view -> {

                File dbMain = getDatabasePath("rodents_helper");
                File dbShm = getDatabasePath("rodents_helper-shm");
                File dbWal = getDatabasePath("rodents_helper-wal");

                ExportAndImport.exportDatabase(dbMain, dbShm, dbWal, prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"), ActivityDatabaseManagement.this);
                reloadActivity();
            });


            buttonImport.setOnClickListener(view -> {
                System.out.println(getDatabasePath("rodents_helper") + " database path");

                ExportAndImport exportAndImport = new ExportAndImport();

                String s = getDatabasePath("rodents_helper").toString();
                String s1 = s.substring(0,s.lastIndexOf("/") + 1);
                s1.trim();
                File databaseDirectory = new File(s1);

                try {
                    ActivityDatabaseManagement.this.deleteDatabase("rodents_helper"); //<<<< ADDED before building Database.
                    AppDatabase db1 = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();

                    Date dateGet = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = df.format(dateGet);

                    exportAndImport.deleteRecursive(databaseDirectory);
                    ExportAndImport.importDatabase(ActivityDatabaseManagement.this, prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"), databaseDirectory);

                    dao.updateCloudAccountImportDate((java.sql.Date.valueOf(formattedDate)), prefsLoginName.getString("prefsLoginName", "nie wykryto nazwy"));
                    db1.close();
                } catch (IOException | InterruptedException | SQLException e) {
                    throw new RuntimeException(e);
                }
                reloadActivity();
            });


            imageViewLogOff.setOnClickListener(view -> {
                SharedPreferences.Editor prefsEditorCloudSave = prefsCloudSave.edit();
                prefsEditorCloudSave.putBoolean("prefsCloudSave", false);
                prefsEditorCloudSave.apply();

                reloadActivity();
            });



        } else {
            linearLayout_notLogged.setVisibility(View.VISIBLE);

            buttonLogin_importExport.setOnClickListener(view -> {
                startActivity(new Intent(ActivityDatabaseManagement.this, ActivityLogin.class));
                finish();
            });

            buttonRegister_importExport.setOnClickListener(view -> {
                startActivity(new Intent(ActivityDatabaseManagement.this, ActivityRegister.class));
                finish();
            });
        }



        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }


    private void reloadActivity() {
        startActivity(new Intent(ActivityDatabaseManagement.this, ActivityDatabaseManagement.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ActivityDatabaseManagement.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }





}