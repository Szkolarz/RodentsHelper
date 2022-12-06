package com.example.rodentshelper.Encyclopedia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.AsyncActivity;
import com.example.rodentshelper.Encyclopedia.Treats.InsertRecords;
import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.SQL.Querries;

import java.sql.Date;
import java.sql.ResultSet;

public class InternetCheckEncyclopedia {

    private String DBversionActual = "0.1_05122022";
    private String DBversion = "";

    public void checkInternet(Context context) {

        Querries dbQuerries = new Querries();


        AsyncActivity internetAsyncCheck = new AsyncActivity();
        internetAsyncCheck.execute();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences prefsDB = context.getSharedPreferences("prefsDB", context.MODE_PRIVATE);
        String sharedPreferencesDBVersion = prefsDB.getString("dbversion", DBversion);


        if (sharedPreferencesDBVersion.equals(DBversionActual)) {



        } else {

            Alerts alert1 = new Alerts();
            alert1.simpleInfo("Pobieranie danych", "Encyklopedia jest modułem, który musi zostać pobrany z internetu." +
                    "Proces jest jednorazowy, raz pobrana baza danych może być później odczytywana bez internetu.", context);

            try {

                Boolean internetCheck = internetAsyncCheck.getInternetConnectionInfo();

                System.out.println("internet check:");
                System.out.println(internetCheck);

                if (internetCheck) { //==true

                    SharedPreferences prefsFirstDownload = context.getSharedPreferences("prefsFirstDownload", context.MODE_PRIVATE);
                    boolean firstDownload = prefsFirstDownload.getBoolean("firstDownload", true);


                    String a = "";
                    ResultSet resultSet = dbQuerries.selectTreats3();
                    ResultSet resultSetVersion = dbQuerries.checkVersion3();
                    while (resultSetVersion.next()) {
                        a = (resultSetVersion.getString("code"));
                    }

                    DBversion = (a);

                    //SharedPreferences prefsDB = context.getSharedPreferences("prefsDB", context.MODE_PRIVATE);
                    //String sharedPreferencesDBVersion = prefsDB.getString("dbversion", DBversion);

                    if (firstDownload == true || !sharedPreferencesDBVersion.equals(DBversion)) {

                        while (resultSet.next()) {

                            //InsertRecords insertRecords = new InsertRecords();
                            //insertRecords.insertRecordsTreats(textView1, textView2, resultSet.getInt("id_animal"));

                            //textView1.setText(resultSet.getString("name"));
                            //textView2.setText(resultSet.getString("description"));

                            AppDatabase db = Room.databaseBuilder(context,
                                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                            DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

                            daoEncyclopedia.insertRecordTreats(new TreatsModel(
                                    resultSet.getInt("id_animal"), resultSet.getString("name"), resultSet.getString("description"),
                                    resultSet.getBytes("image"), resultSet.getBoolean("is_healthy")
                            ));


                            //dbHandler.addNewRodent(resultSetTest.getString("imie"), resultSetTest.getString("nazwisko"));
                        }

                        SharedPreferences.Editor editorFirstDownload = prefsFirstDownload.edit();
                        editorFirstDownload.putBoolean("firstDownload", false);
                        editorFirstDownload.apply();

                        SharedPreferences.Editor editorDB = prefsDB.edit();
                        editorDB.putString("dbversion", DBversion);
                        editorDB.apply();


                    }


                } else {
                    System.out.println("Brak internetu");
                }


                System.out.println(dbQuerries.testSelect());


            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }



}
