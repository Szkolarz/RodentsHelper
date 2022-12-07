package com.example.rodentshelper.Encyclopedia;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;
import com.example.rodentshelper.SQL.Querries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class VersionCodeCheck {



    private String checkDbVersion (Context context, Querries dbQuerries) throws SQLException {
        String versionCodeFromVPS = "";
        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        ResultSet resultSetVersion = dbQuerries.checkVersion(prefsFirstStart.getInt("prefsFirstStart", 0));

        while (resultSetVersion.next()) {
            versionCodeFromVPS = (resultSetVersion.getString("code"));
        }

        String DBversion = (versionCodeFromVPS);
        return DBversion;
    }

    public Boolean isVersionUpToDate (Context context, Querries dbQuerries) throws SQLException {

        SharedPreferences prefsDB = context.getSharedPreferences("prefsDB", context.MODE_PRIVATE);
        String sharedPreferencesDBVersion = prefsDB.getString("dbversion", "0");

        String DBversion = checkDbVersion(context, dbQuerries);

        if (!sharedPreferencesDBVersion.equals(DBversion)) {
            return false;

        } else {
            return true;
        }

    }

    public void makeAnUpdate (Context context, Querries dbQuerries, SharedPreferences prefsFirstDownload) {

        String versionCodeFromVPS = "";

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        System.out.println(prefsFirstStart.getInt("prefsFirstStart", 0) + " tetettee");

        try {

            String DBversion = checkDbVersion(context, dbQuerries);


            SharedPreferences prefsDB = context.getSharedPreferences("prefsDB", context.MODE_PRIVATE);
            String sharedPreferencesDBVersion = prefsDB.getString("dbversion", "0");


            if (!sharedPreferencesDBVersion.equals(DBversion)) {

                readDataFromVPS(context, dbQuerries, prefsFirstStart);

                SharedPreferences.Editor editorFirstDownload = prefsFirstDownload.edit();
                editorFirstDownload.putBoolean("firstDownload", false);
                editorFirstDownload.apply();

                SharedPreferences.Editor editorDB = prefsDB.edit();
                editorDB.putString("dbversion", DBversion);
                editorDB.apply();
            }


        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public void readDataFromVPS (Context context, Querries dbQuerries, SharedPreferences prefsFirstStart) {

        try {

            ResultSet resultSetTreats = dbQuerries.selectTreats(prefsFirstStart.getInt("prefsFirstStart", 0));

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

            while (resultSetTreats.next()) {

                /** tu usuwasz najpierw cala zawartosc, potem dodajesz */

                /* TREATS */
                daoEncyclopedia.insertRecordTreats(new TreatsModel(
                        resultSetTreats.getInt("id_animal"), resultSetTreats.getString("name"), resultSetTreats.getString("description"),
                        resultSetTreats.getBytes("image"), resultSetTreats.getBoolean("is_healthy")
                ));

                /* CAGE SUPPLY */
                /*daoEncyclopedia.insertRecordCageSupply(new TreatsModel(
                        resultSetTreats.getInt("id_animal"), resultSetTreats.getString("name"), resultSetTreats.getString("description"),
                        resultSetTreats.getBytes("image"), resultSetTreats.getBoolean("is_healthy")
                ));*/

            }

            db.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
