package com.gryzoniopedia.rodentshelper.Encyclopedia;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Version.VersionModel;
import com.gryzoniopedia.rodentshelper.AsyncActivity;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Diseases.DiseasesModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.General.GeneralModel;
import com.gryzoniopedia.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOEncyclopedia;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;
import com.gryzoniopedia.rodentshelper.SQL.Querries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class VersionCodeCheck {

    public Integer getTestCountRecords (ViewEncyclopedia context) {

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        Integer count = daoEncyclopedia.getTreatsCountTest(prefsFirstStart.getInt("prefsFirstStart", 0));
        db.close();
        return count;
    }



    private String checkDbVersionFromVPS (Context context, Querries dbQuerries) throws SQLException, InterruptedException {

        String versionCodeFromVPS = "";
        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        ResultSet resultSetVersion = dbQuerries.checkVersion(prefsFirstStart.getInt("prefsFirstStart", 0), context);

        while (resultSetVersion.next()) {
            versionCodeFromVPS = (resultSetVersion.getString("code"));
        }

        return (versionCodeFromVPS);
    }


    //the first after an update
    public Boolean isVersionUpToDate (Context context, Querries dbQuerries) throws SQLException {

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        try {
            String DBversion = checkDbVersionFromVPS(context, dbQuerries);

            if (!(daoEncyclopedia.getVersionCode(prefsFirstStart.getInt("prefsFirstStart", 0))).equals(DBversion)) {
                db.close();
                return false;

            } else {
                db.close();
                return true;
            }

        } catch (Exception e) {
            db.close();
            System.out.println(e+" ERROR");
        }
    return true;
    }


    //starts when users runs encyclopedia for the first time
    public void makeAnUpdate (ViewEncyclopedia context, Querries dbQuerries, SharedPreferences prefsFirstDownload) throws ExecutionException, InterruptedException {

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        Boolean internetConnectionResult = new AsyncActivity().execute().get();

        if (internetConnectionResult) {
            try {
                String DBversion = checkDbVersionFromVPS(context, dbQuerries);

                SharedPreferences prefsDB = context.getSharedPreferences("prefsDB", MODE_PRIVATE);
                String sharedPreferencesDBVersion = prefsDB.getString("dbversion", "0");

                if (!sharedPreferencesDBVersion.equals(DBversion)) {
                    readDataFromVPS(context, dbQuerries, prefsFirstStart);
                    SharedPreferences.Editor editorFirstDownload = prefsFirstDownload.edit();
                    editorFirstDownload.putBoolean("firstDownload", false);
                    editorFirstDownload.apply();

                    Thread thread = new Thread(() -> context.runOnUiThread(() ->
                            Toast.makeText(context, "Pomyślnie pobrano dane", Toast.LENGTH_SHORT).show()));

                    thread.start();

                }

            } catch (Exception e) {
                alertError(context);
            }
        } else {
            alertError(context);
        }
    }


    private void alertError(ViewEncyclopedia context) {

        Thread thread = new Thread(() -> context.runOnUiThread(() -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogStyleUpdate);
            alert.setTitle("Błąd serwera");
            alert.setMessage("Wystąpił problem łączenia się z internetową bazą danych. " +
                    "Użyj innej sieci lub spróbuj ponownie później.");

            alert.setPositiveButton("Rozumiem", (dialogInterface, i) -> {
                Toast.makeText(context, "Spróbuj ponownie później", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ViewRodents.class));
                context.finish();
            });
            alert.show();
        }));

        thread.start();
    }


    public void readDataFromVPS (Context context, Querries dbQuerries, SharedPreferences prefsFirstStart) {
        try {
            Integer idAnimal = prefsFirstStart.getInt("prefsFirstStart", 0);

            ResultSet resultSetGeneral = dbQuerries.selectGeneral(idAnimal, context);
            ResultSet resultSetTreats = dbQuerries.selectTreats(idAnimal, context);
            ResultSet resultSetCageSupply = dbQuerries.selectCageSupply(idAnimal, context);
            ResultSet resultSetDiseases = dbQuerries.selectDiseases(idAnimal, context);
            ResultSet resultSetVersion = dbQuerries.selectVersion(context);

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

            daoEncyclopedia.deleteGeneral(idAnimal);
            daoEncyclopedia.deleteTreats(idAnimal);
            daoEncyclopedia.deleteCageSupply(idAnimal);
            daoEncyclopedia.deleteDiseases(idAnimal);
            daoEncyclopedia.deleteVersion();


            while (resultSetVersion.next()) {
                /* Version */
                daoEncyclopedia.insertRecordVersion(new VersionModel(
                        resultSetVersion.getInt("id_animal"), resultSetVersion.getString("code")));
            }

            while (resultSetGeneral.next()) {
                daoEncyclopedia.insertRecordGeneral(new GeneralModel(
                        resultSetGeneral.getInt("id_animal"), resultSetGeneral.getString("name"), resultSetGeneral.getString("description"),
                        resultSetGeneral.getBytes("image")
                ));
            }

            while (resultSetTreats.next()) {
                daoEncyclopedia.insertRecordTreats(new TreatsModel(
                        resultSetTreats.getInt("id_animal"), resultSetTreats.getString("name"), resultSetTreats.getString("description"),
                        resultSetTreats.getBytes("image"), resultSetTreats.getBoolean("is_healthy")
                ));

            }

            while (resultSetCageSupply.next()) {
                /* CAGE SUPPLY */
                daoEncyclopedia.insertRecordCageSupply(new CageSupplyModel(
                        resultSetCageSupply.getInt("id_animal"), resultSetCageSupply.getString("name"), resultSetCageSupply.getString("description"),
                        resultSetCageSupply.getBytes("image"), resultSetCageSupply.getBoolean("is_good")
                ));
            }

            while (resultSetDiseases.next()) {
                daoEncyclopedia.insertRecordDiseases(new DiseasesModel(
                        resultSetDiseases.getInt("id_animal"), resultSetDiseases.getString("name"), resultSetDiseases.getString("description")
                ));
            }

            db.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}