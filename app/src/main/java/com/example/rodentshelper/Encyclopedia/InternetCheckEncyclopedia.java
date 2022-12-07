package com.example.rodentshelper.Encyclopedia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.Encyclopedia.Treats.InsertRecords;
import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.Encyclopedia.Treats.ViewTreats;
import com.example.rodentshelper.ImageCompress;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.Notes.ViewNotes;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.SQL.Querries;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InternetCheckEncyclopedia {

    private String DBversionActual = "0.1_05122022";


    public void checkInternet(ViewEncyclopedia viewEncyclopedia, LinearLayout linearLayout_encyclopedia,
                              ProgressBar progressBar_encyclopedia, TextView textViewProgress_encyclopedia) throws SQLException {

        Querries dbQuerries = new Querries();


        AsyncActivity internetAsyncCheck = new AsyncActivity();
        internetAsyncCheck.execute();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        SharedPreferences prefsFirstDownload = viewEncyclopedia.getSharedPreferences("prefsFirstDownload", viewEncyclopedia.MODE_PRIVATE);
        Boolean firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);

        Boolean internetCheck = internetAsyncCheck.getInternetConnectionInfo();




        if (firstDownload == false && internetCheck == true) {

            VersionCodeCheck versionCodeCheck = new VersionCodeCheck();

            if(!versionCodeCheck.isVersionUpToDate(viewEncyclopedia, dbQuerries)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
                alert.setTitle("Aktualizacja danych dostępna!");
                alert.setMessage("W internetowej bazie danych pojawiła się aktualizacja. " +
                        "Oznacza to poprawki w formie informacji naniesionych na sekcję 'Encyklopedia'.\n\n" +
                        "Czy chcesz pobrać teraz aktualizację bazy danych do aplikacji?");

                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                        progressBar_encyclopedia.setVisibility(View.VISIBLE);
                        linearLayout_encyclopedia.setVisibility(View.GONE);

                        Thread thread = new Thread(() -> {

                            viewEncyclopedia.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Boolean internetCheck = internetAsyncCheck.getInternetConnectionInfo();
                                    if (internetCheck) { //==true
                                        VersionCodeCheck versionCodeCheck = new VersionCodeCheck();
                                        versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                                    } else {
                                        Alerts alert = new Alerts();
                                        alert.simpleInfo("Brak internetu", "Nie masz połączenia z internetem." +
                                                "Użyj innej sieci lub spróbuj ponownie później.", viewEncyclopedia);
                                    }
                                    textViewProgress_encyclopedia.setVisibility(View.GONE);
                                    progressBar_encyclopedia.setVisibility(View.GONE);
                                    linearLayout_encyclopedia.setVisibility(View.VISIBLE);
                                    Toast.makeText(viewEncyclopedia, "Pomyślnie pobrano dane", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        thread.start();

                    }
                });
                alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();
            }

        }






        if (firstDownload == true) {


            AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
            alert.setTitle("Pobieranie danych");
            alert.setMessage("Encyklopedia jest modułem, który musi zostać pobrany z internetu. " +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.\n\n" +
                    "Czy chcesz pobrać teraz zawartość bazy danych do aplikacji?");

            AsyncActivity internetAsyncCheck1 = new AsyncActivity();
            internetAsyncCheck1.execute();
            Boolean internetCheck1 = internetAsyncCheck.getInternetConnectionInfo();


            alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                    progressBar_encyclopedia.setVisibility(View.VISIBLE);
                    linearLayout_encyclopedia.setVisibility(View.GONE);

                    Thread thread = new Thread(() -> {

                        viewEncyclopedia.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Boolean internetCheck = internetAsyncCheck.getInternetConnectionInfo();
                                if (internetCheck == true) { //==true
                                    VersionCodeCheck versionCodeCheck = new VersionCodeCheck();
                                    versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                                } else {
                                    Alerts alert = new Alerts();
                                    alert.simpleInfo("Brak internetu", "Nie masz połączenia z internetem. " +
                                            "Użyj innej sieci lub spróbuj ponownie później.", viewEncyclopedia);
                                }
                                textViewProgress_encyclopedia.setVisibility(View.GONE);
                                progressBar_encyclopedia.setVisibility(View.GONE);
                                linearLayout_encyclopedia.setVisibility(View.VISIBLE);
                                Toast.makeText(viewEncyclopedia, "Pomyślnie pobrano dane", Toast.LENGTH_SHORT).show();
                            }
                        });

                    });

                    thread.start();


                }
            });
            alert.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(viewEncyclopedia, ViewRodents.class);
                    viewEncyclopedia.startActivity(intent);
                    viewEncyclopedia.finish();
                }
            });
            alert.create().show();

        }









    }






}
