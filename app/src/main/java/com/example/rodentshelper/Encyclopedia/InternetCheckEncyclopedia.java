package com.example.rodentshelper.Encyclopedia;

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

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.ImageCompress;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.SQL.Querries;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class InternetCheckEncyclopedia {

    //private String DBversionActual = "0.1_05122022";


    public void checkInternet(ViewEncyclopedia viewEncyclopedia, LinearLayout linearLayout_encyclopedia,
                              ProgressBar progressBar_encyclopedia, TextView textViewProgress_encyclopedia) throws SQLException {

        Querries dbQuerries = new Querries();


        AsyncActivity internetAsyncCheck = new AsyncActivity();
        internetAsyncCheck.execute();




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        SharedPreferences prefsFirstDownload = viewEncyclopedia.getSharedPreferences("prefsFirstDownload", Context.MODE_PRIVATE);
        boolean firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);

        Boolean internetCheck = AsyncActivity.getInternetConnectionInfo();

        //sometimes internetCheck can't finish for unknown reason, that's why it is set to null
        if (internetCheck == null)
            internetCheck = true;


        if (!firstDownload && internetCheck) {

            VersionCodeCheck versionCodeCheck = new VersionCodeCheck();

            if(!versionCodeCheck.isVersionUpToDate(viewEncyclopedia, dbQuerries)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
                alert.setTitle("Aktualizacja danych dostępna!");
                alert.setMessage("W internetowej bazie danych pojawiła się aktualizacja. " +
                        "Oznacza to poprawki w formie informacji naniesionych na sekcję 'Encyklopedia'.\n\n" +
                        "Czy chcesz pobrać teraz aktualizację bazy danych do aplikacji?");

                alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                    textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                    progressBar_encyclopedia.setVisibility(View.VISIBLE);
                    linearLayout_encyclopedia.setVisibility(View.GONE);

                    Thread thread = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {

                        AsyncActivity internetAsyncCheck1 = new AsyncActivity();
                        internetAsyncCheck1.execute();
                        Boolean internetCheck1 = AsyncActivity.getInternetConnectionInfo();
                        if (internetCheck1) { //==true
                            VersionCodeCheck versionCodeCheck1 = new VersionCodeCheck();
                            versionCodeCheck1.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);

                        } else {
                            Alerts alert12 = new Alerts();
                            alert12.simpleInfo("Brak internetu", "Nie masz połączenia z internetem. " +
                                    "Użyj innej sieci lub spróbuj ponownie później.", viewEncyclopedia);
                        }
                        textViewProgress_encyclopedia.setVisibility(View.GONE);
                        progressBar_encyclopedia.setVisibility(View.GONE);
                        linearLayout_encyclopedia.setVisibility(View.VISIBLE);

                    }));

                    thread.start();

                });
                alert.setNegativeButton("Nie", (dialogInterface, i) ->
                        Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show());

                alert.setOnCancelListener(new AlertDialog.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
                        viewEncyclopedia.finish();
                    }

                });

                alert.create().show();
            }

        }






        if (firstDownload) {


            AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
            alert.setTitle("Pobieranie danych");
            alert.setMessage("Encyklopedia jest modułem, który musi zostać pobrany z internetu. " +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.\n\n" +
                    "Czy chcesz pobrać teraz zawartość bazy danych do aplikacji?");



            alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                progressBar_encyclopedia.setVisibility(View.VISIBLE);
                linearLayout_encyclopedia.setVisibility(View.GONE);

                Thread thread = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {

                    AsyncActivity internetAsyncCheck1 = new AsyncActivity();
                    internetAsyncCheck1.execute();
                    Boolean internetCheck1 = AsyncActivity.getInternetConnectionInfo();
                    if (internetCheck1) { //==true
                        VersionCodeCheck versionCodeCheck = new VersionCodeCheck();
                        versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                    } else {
                        Alerts alert1 = new Alerts();
                        alert1.simpleInfo("Brak internetu", "Nie masz połączenia z internetem. " +
                                "Użyj innej sieci lub spróbuj ponownie później.", viewEncyclopedia);
                    }
                    textViewProgress_encyclopedia.setVisibility(View.GONE);
                    progressBar_encyclopedia.setVisibility(View.GONE);
                    linearLayout_encyclopedia.setVisibility(View.VISIBLE);

                }));

                thread.start();

            });
            alert.setNegativeButton("Nie", (dialogInterface, i) -> {
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
            });

            alert.setOnCancelListener(new AlertDialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
                    viewEncyclopedia.finish();
                }

            });

            alert.create().show();

        }





    }






}
