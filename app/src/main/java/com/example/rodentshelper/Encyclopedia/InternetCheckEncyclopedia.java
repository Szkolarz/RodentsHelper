package com.example.rodentshelper.Encyclopedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        AsyncActivity internetAsyncCheck = new AsyncActivity();
        //internetAsyncCheck.execute();
        AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
        Alerts alertInfo = new Alerts();

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

                        alert.setTitle("Brak połączenia z internetem");
                        alert.setMessage("Użyj innej sieci lub spróbuj ponownie później.");

                        alert.setPositiveButton("Rozumiem", (dialogInterface, i) -> {
                            Toast.makeText(viewEncyclopedia, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                            closeEncyclopedia(viewEncyclopedia);
                        });
                        alert.show();
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

            alert.setTitle("Pobieranie danych");
            alert.setMessage("Encyklopedia jest modułem, który musi zostać pobrany z internetu. " +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.\n\n" +
                    "Czy chcesz pobrać teraz zawartość bazy danych do aplikacji?");



            alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
                progressBar_encyclopedia.setVisibility(View.VISIBLE);
                linearLayout_encyclopedia.setVisibility(View.GONE);



                Thread thread = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {
                //declaring connection policy
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                    try {
                        if (new AsyncActivity().execute().get()) {
                            //==true
                            versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                        } else {
                            AlertDialog.Builder alert1 = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
                            alert1.setTitle("Brak połączenia z internetem");
                            alert1.setMessage("Użyj innej sieci lub spróbuj ponownie później.");

                            alert1.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> {
                                Toast.makeText(viewEncyclopedia, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                                closeEncyclopedia(viewEncyclopedia);
                            });
                            alert1.show();
                       }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }


                    textViewProgress_encyclopedia.setVisibility(View.GONE);
                    progressBar_encyclopedia.setVisibility(View.GONE);
                    linearLayout_encyclopedia.setVisibility(View.VISIBLE);
                }));

                thread.start();

            });
            alert.setNegativeButton("Nie", (dialogInterface, i) -> {
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                closeEncyclopedia(viewEncyclopedia);
            });

            alert.setOnCancelListener(dialogInterface -> {
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                closeEncyclopedia(viewEncyclopedia);
            });

            alert.create().show();

        }


    }


    private void closeEncyclopedia(ViewEncyclopedia viewEncyclopedia) {
        viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
        viewEncyclopedia.finish();
    }



}
