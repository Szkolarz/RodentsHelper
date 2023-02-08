package com.gryzoniopedia.rodentshelper.DatabaseManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAO;
import com.gryzoniopedia.rodentshelper.SQL.Querries;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExportAndImport {

    public static void exportDatabase(File dbMain, File dbShm, File dbWal, String login, Context context) {

        try {
            Querries dbQuerries = new Querries();
            InputStream targetStream1 = new FileInputStream(dbMain);

            dbQuerries.deleteBeforeExport(login, context);

            Date dateGet = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(dateGet);

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO dao = db.dao();
            dao.updateCloudAccountExportDate(java.sql.Date.valueOf(formattedDate), login);
            db.close();

            dbQuerries.setExportDate(login, java.sql.Date.valueOf(formattedDate), context);

            dbQuerries.exportLocalDatabase(targetStream1, context);
            targetStream1.close();

            try {
                InputStream targetStream2 = new FileInputStream(dbShm);
                InputStream targetStream3 = new FileInputStream(dbWal);
                dbQuerries.exportLocalDatabase(targetStream2, context);
                dbQuerries.exportLocalDatabase(targetStream3, context);
                targetStream2.close();
                targetStream3.close();
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE: " + e);
            }

        } catch (Exception e) {
            Log.e("ExportAndImport", Log.getStackTraceString(e));
        }
    }


    /*private static void deleteRecursive(File databaseDirectory) {
        if (databaseDirectory.isDirectory())
            for (File child : databaseDirectory.listFiles()) {
                deleteRecursive(child);
            }
        databaseDirectory.delete();
    }*/


    private static void turnOffPreferences(Context context) {
        SharedPreferences prefsNotificationWeight = context.getSharedPreferences("prefsNotificationWeight", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding = context.getSharedPreferences("prefsNotificationFeeding", Context.MODE_PRIVATE);
        SharedPreferences prefsNotificationFeeding2 = context.getSharedPreferences("prefsNotificationFeeding2", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditorNotificationWeight = prefsNotificationWeight.edit();
        prefsEditorNotificationWeight.putBoolean("prefsNotificationWeight", false);
        prefsEditorNotificationWeight.apply();

        SharedPreferences.Editor prefsEditorNotificationFeeding = prefsNotificationFeeding.edit();
        prefsEditorNotificationFeeding.putBoolean("prefsNotificationFeeding", false);
        prefsEditorNotificationFeeding.apply();

        SharedPreferences.Editor prefsEditorNotificationFeeding2 = prefsNotificationFeeding2.edit();
        prefsEditorNotificationFeeding2.putBoolean("prefsNotificationFeeding2", false);
        prefsEditorNotificationFeeding2.apply();
    }



    public static boolean importDatabase(ActivityDatabaseManagement context, String login)
            throws IOException, SQLException, InterruptedException {
        Querries dbQuerries = new Querries();
        turnOffPreferences(context);
        ResultSet resultSetImport = dbQuerries.importLocalDatabase(login, context);

        String databaseFolder = context.getDatabasePath("rodents_helper").toString();
        String databaseFilePath = databaseFolder.substring(0,databaseFolder.lastIndexOf("/") + 1);
        databaseFilePath.trim();

        if (!resultSetImport.isBeforeFirst() ) {
            return false;
        } else {
            context.deleteDatabase("rodents_helper");
            //deleteRecursive(databaseDirectory);
            int flag = 1;
            while (resultSetImport.next()) {

                InputStream inputStream = resultSetImport.getBinaryStream("file");

                try {
                    if (flag == 1)
                        IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath + "/rodents_helper"));
                    else if (flag == 2)
                        IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath + "/rodents_helper-shm"));
                    else if (flag == 3)
                        IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath + "/rodents_helper-wal"));
                } catch (FileNotFoundException e) {
                    Log.e("importDatabase", Log.getStackTraceString(e));
                }

                inputStream.close();
                flag++;
            }

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO dao = db.dao();
            dao.updateCloudAccountImportDateToNull();
            db.close();
            
            return true;
        }
    }
}
