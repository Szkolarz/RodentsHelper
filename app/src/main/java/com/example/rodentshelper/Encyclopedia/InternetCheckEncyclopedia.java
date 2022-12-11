package com.example.rodentshelper.Encyclopedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.SQL.Querries;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class InternetCheckEncyclopedia {

    //private String DBversionActual = "0.1_05122022";



    public boolean isNetworkConnected(ViewEncyclopedia viewEncyclopedia) {
        ConnectivityManager cm = (ConnectivityManager) viewEncyclopedia.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void checkInternet(ViewEncyclopedia viewEncyclopedia, LinearLayout linearLayout_encyclopedia,
                              ProgressBar progressBar_encyclopedia, TextView textViewProgress_encyclopedia, ProgressDialog alertUpdate) throws SQLException, ExecutionException, InterruptedException {

        Querries dbQuerries = new Querries();

        System.out.println( isNetworkConnected(viewEncyclopedia) + " qqqqqqqqqqqqqq");

        AsyncActivity internetAsyncCheck = new AsyncActivity();
        //internetAsyncCheck.execute();






        SharedPreferences prefsFirstDownload = viewEncyclopedia.getSharedPreferences("prefsFirstDownload", Context.MODE_PRIVATE);
        boolean firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);

        VersionCodeCheck versionCodeCheck = new VersionCodeCheck();

        if (versionCodeCheck.getTestCountRecords(viewEncyclopedia) <= 0) {
            SharedPreferences.Editor editorFirstDownload = prefsFirstDownload.edit();
            editorFirstDownload.putBoolean("firstDownload", true);
            editorFirstDownload.apply();
            firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);
        }

        boolean internetCheck = isNetworkConnected(viewEncyclopedia);

        if (!internetCheck)
            alertUpdate.cancel();

        if (!firstDownload && internetCheck ) {

            Thread thread1 = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {
                //declaring connection policy
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    if (new AsyncActivity().execute().get()) {


                        if(!versionCodeCheck.isVersionUpToDate(viewEncyclopedia, dbQuerries)) {
                            alertUpdate.cancel();
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

                                    //==true
                                    try {
                                        versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                                    } catch (ExecutionException | InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    textViewProgress_encyclopedia.setVisibility(View.GONE);
                                    progressBar_encyclopedia.setVisibility(View.GONE);
                                    linearLayout_encyclopedia.setVisibility(View.VISIBLE);

                                }));

                                thread.start();

                            });

                            alert.setNegativeButton("Nie", (dialogInterface, i) ->
                                    Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show());

                            alert.create().show();
                        } else
                            alertUpdate.cancel();

                    } else {
                        alertUpdate.cancel();
                        Toast.makeText(viewEncyclopedia, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException | SQLException e) {
                    e.printStackTrace();
                }

                textViewProgress_encyclopedia.setVisibility(View.GONE);
                progressBar_encyclopedia.setVisibility(View.GONE);
                linearLayout_encyclopedia.setVisibility(View.VISIBLE);

            }));

            thread1.start();
        }









        if (firstDownload) {
            alertUpdate.dismiss();

            AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
            alert.setTitle("Pobieranie danych");
            alert.setMessage("Encyklopedia jest modułem, który musi zostać pobrany z internetu. " +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.\n\n" +
                    "Czy chcesz pobrać teraz zawartość bazy danych do aplikacji?");



            alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                progressBar_encyclopedia.setVisibility(View.VISIBLE);
                linearLayout_encyclopedia.setVisibility(View.GONE);



                Thread thread = new Thread(() -> {
                    //declaring connection policy
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if (internetCheck) { //==true
                        try {
                            versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Alerts alert1 = new Alerts();
                        alert1.simpleInfo("Brak internetu", "Nie masz połączenia z internetem. " +
                                "Użyj innej sieci lub spróbuj ponownie później.", viewEncyclopedia);
                    }

                    viewEncyclopedia.runOnUiThread(() -> {


                        textViewProgress_encyclopedia.setVisibility(View.GONE);
                        progressBar_encyclopedia.setVisibility(View.GONE);
                        linearLayout_encyclopedia.setVisibility(View.VISIBLE);
                    });

                });

                thread.start();

            });
            alert.setNegativeButton("Nie", (dialogInterface, i) -> {
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
                viewEncyclopedia.finish();
            });

            alert.setOnCancelListener(dialogInterface -> {
                viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
                viewEncyclopedia.finish();
            });

            alert.create().show();

        }





    }






}
