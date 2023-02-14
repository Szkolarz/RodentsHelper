package com.gryzoniopedia.rodentshelper.Encyclopedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.gryzoniopedia.rodentshelper.AsyncActivity;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.SQL.Querries;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class InternetCheckEncyclopedia {

    public boolean isNetworkConnected(ViewEncyclopedia viewEncyclopedia) {
        ConnectivityManager cm = (ConnectivityManager) viewEncyclopedia.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }



    public void checkInternet(ViewEncyclopedia viewEncyclopedia, LinearLayout linearLayout_encyclopedia,
                              ProgressBar progressBar_encyclopedia, TextView textViewProgress_encyclopedia,
                              TextView textViewProgress_encyclopedia2, TextView textViewProgress_encyclopedia3,
                              LinearLayout linearLayoutUpdateCheck)
            throws SQLException, ExecutionException, InterruptedException {

        Querries dbQuerries = new Querries();
        AlertDialog.Builder alert = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);

        SharedPreferences prefsFirstDownload = viewEncyclopedia.getSharedPreferences("prefsFirstDownload", Context.MODE_PRIVATE);
        boolean firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);

        VersionCodeCheck versionCodeCheck = new VersionCodeCheck();

        if (versionCodeCheck.getTestCountRecords(viewEncyclopedia) <= 0) {
            SharedPreferences.Editor editorFirstDownload = prefsFirstDownload.edit();
            editorFirstDownload.putBoolean("firstDownload", true);
            editorFirstDownload.apply();
            firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);
        }

        SharedPreferences prefsAutoUpdate = viewEncyclopedia.getSharedPreferences("prefsAutoUpdate", Context.MODE_PRIVATE);
        boolean isAutoUpdateOn = prefsAutoUpdate.getBoolean("prefsAutoUpdate", true);

        boolean internetCheck = isNetworkConnected(viewEncyclopedia);

        if (!internetCheck)
            linearLayoutUpdateCheck.setVisibility(View.GONE);

        if (!firstDownload && internetCheck && isAutoUpdateOn) {

            Thread threadInit = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {

                try {//==true
                    if (new AsyncActivity().execute().get()) {

                        Thread thread = new Thread(() -> {
                            //declaring connection policy
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            try {
                                if (!versionCodeCheck.isVersionUpToDate(viewEncyclopedia, dbQuerries)) {
                                    viewEncyclopedia.runOnUiThread(() -> linearLayoutUpdateCheck.setVisibility(View.GONE));

                                    viewEncyclopedia.runOnUiThread(() -> {

                                        alert.setTitle("Aktualizacja danych dostępna!");
                                        alert.setMessage("W internetowej bazie danych pojawiła się aktualizacja. " +
                                                "Oznacza to poprawki lub nowe informacje w sekcji 'Encyklopedia'.\n\n" +
                                                "Czy chcesz pobrać teraz aktualizację encyklopedii?");

                                        alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                                            showDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia);

                                            Thread thread1 = new Thread(() -> {

                                                StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                                                StrictMode.setThreadPolicy(policy1);
                                                try {
                                                    versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                                                } catch (ExecutionException |
                                                         InterruptedException e) {
                                                    Log.e("95 internetCheck", Log.getStackTraceString(e));
                                                }

                                                viewEncyclopedia.runOnUiThread(() -> hideDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, textViewProgress_encyclopedia3,
                                                        progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia));


                                            });

                                            thread1.start();

                                        });

                                        alert.setNegativeButton("Nie", (dialogInterface, i) ->
                                                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show());

                                        alert.create().show();

                                    });


                                } else {
                                    viewEncyclopedia.runOnUiThread(() -> linearLayoutUpdateCheck.setVisibility(View.GONE));
                                }
                            } catch (SQLException e) {
                                Log.e("119 internetCheck", Log.getStackTraceString(e));
                            }
                        });

                        thread.start();

                    } else {
                        viewEncyclopedia.runOnUiThread(() -> linearLayoutUpdateCheck.setVisibility(View.GONE));
                        Toast.makeText(viewEncyclopedia, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException | InterruptedException e) {
                    Log.e("131 internetCheck", Log.getStackTraceString(e));
                }
                }));
            threadInit.start();

            hideDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, textViewProgress_encyclopedia3,
                    progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia);


        }






        if (firstDownload) {
            viewEncyclopedia.runOnUiThread(() -> linearLayoutUpdateCheck.setVisibility(View.GONE));

            alert.setTitle("Pobieranie danych");
            alert.setMessage("Encyklopedia jest modułem, który musi zostać pobrany z internetu. " +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.\n\n" +
                    "Czy chcesz pobrać teraz zawartość bazy danych do aplikacji?");



            alert.setPositiveButton("Tak", (dialogInterface, i) -> {

                showDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia);


                Thread threadInit = new Thread(() -> viewEncyclopedia.runOnUiThread(() -> {
                    try {
                        if (new AsyncActivity().execute().get()) {
                            //==true
                            Thread thread = new Thread(() -> {
                                //declaring connection policy
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                try {
                                    versionCodeCheck.makeAnUpdate(viewEncyclopedia, dbQuerries, prefsFirstDownload);
                                } catch (ExecutionException | InterruptedException e) {
                                    Log.e("172 internetCheck", Log.getStackTraceString(e));
                                }
                                    viewEncyclopedia.runOnUiThread(() -> hideDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, textViewProgress_encyclopedia3,
                                            progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia));

                            });
                            thread.start();

                        } else {
                            hideDownload(textViewProgress_encyclopedia, textViewProgress_encyclopedia2, textViewProgress_encyclopedia3,
                                    progressBar_encyclopedia, linearLayout_encyclopedia, viewEncyclopedia);
                            AlertDialog.Builder alert1 = new AlertDialog.Builder(viewEncyclopedia, R.style.AlertDialogStyleUpdate);
                            alert1.setTitle("Brak połączenia z internetem");
                            alert1.setMessage("Użyj innej sieci lub spróbuj ponownie później.");

                            alert1.setPositiveButton("Rozumiem", (dialogInterface1, i1) -> {
                                closeEncyclopedia(viewEncyclopedia);
                                Toast.makeText(viewEncyclopedia, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
                            });
                            alert1.show();
                       }
                    } catch (ExecutionException | InterruptedException e) {
                        Log.e("192 internetCheck", Log.getStackTraceString(e));
                    }
                }));
                threadInit.start();



            });
            alert.setNegativeButton("Nie", (dialogInterface, i) -> {
                closeEncyclopedia(viewEncyclopedia);
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
            });

            alert.setOnCancelListener(dialogInterface -> {
                closeEncyclopedia(viewEncyclopedia);
                Toast.makeText(viewEncyclopedia, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
            });

            alert.create().show();
        }


    }




    private void showDownload(TextView textViewProgress_encyclopedia, TextView textViewProgress_encyclopedia2, ProgressBar progressBar_encyclopedia,
                              LinearLayout linearLayout_encyclopedia, ViewEncyclopedia viewEncyclopedia) {

        FlagSetup.setAllowBackInEncyclopedia(false);

        viewEncyclopedia.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        textViewProgress_encyclopedia.setVisibility(View.VISIBLE);
        textViewProgress_encyclopedia2.setVisibility(View.VISIBLE);
        progressBar_encyclopedia.setVisibility(View.VISIBLE);
        progressBar_encyclopedia.setIndeterminate(true);

        linearLayout_encyclopedia.setVisibility(View.GONE);


    }


    private void hideDownload(TextView textViewProgress_encyclopedia, TextView textViewProgress_encyclopedia2, TextView textViewProgress_encyclopedia3,
                              ProgressBar progressBar_encyclopedia, LinearLayout linearLayout_encyclopedia,
                              ViewEncyclopedia viewEncyclopedia) {

        FlagSetup.setAllowBackInEncyclopedia(true);

        viewEncyclopedia.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        textViewProgress_encyclopedia3.setVisibility(View.GONE);
        textViewProgress_encyclopedia.setVisibility(View.GONE);
        textViewProgress_encyclopedia2.setVisibility(View.GONE);
        progressBar_encyclopedia.setVisibility(View.GONE);
        linearLayout_encyclopedia.setVisibility(View.VISIBLE);
    }


    private void closeEncyclopedia(ViewEncyclopedia viewEncyclopedia) {
        viewEncyclopedia.startActivity(new Intent(viewEncyclopedia, ViewRodents.class));
        viewEncyclopedia.finish();
    }
}
