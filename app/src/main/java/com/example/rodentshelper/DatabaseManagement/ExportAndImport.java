package com.example.rodentshelper.DatabaseManagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rodentshelper.SQL.Querries;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportAndImport {

    public static void exportDatabase(File dbMain, File dbShm, File dbWal, Context context) {

        try {
            Querries dbQuerries = new Querries();
            InputStream targetStream1 = new FileInputStream(dbMain);
            dbQuerries.exportLocalDatabase(targetStream1, context);

            try {
                InputStream targetStream2 = new FileInputStream(dbShm);
                InputStream targetStream3 = new FileInputStream(dbWal);
                dbQuerries.exportLocalDatabase(targetStream2, context);
                dbQuerries.exportLocalDatabase(targetStream3, context);
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE: " + e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    void deleteRecursive(File databaseDirectory) {

        if (databaseDirectory.isDirectory())
            for (File child : databaseDirectory.listFiles()) {
                deleteRecursive(child);
            }

        databaseDirectory.delete();
    }



    public static void importDatabase(Context context, String login, File databaseDirectory) throws IOException, SQLException, InterruptedException {
        Querries dbQuerries = new Querries();
        ResultSet resultSetImport = dbQuerries.importLocalDatabase(login, context);

        String databaseFolder = context.getDatabasePath("rodents_helper").toString();
        String databaseFilePath = databaseFolder.substring(0,databaseFolder.lastIndexOf("/") + 1);
        databaseFilePath.trim();


        int flag = 1;
        while (resultSetImport.next()) {

            InputStream inputStream = resultSetImport.getBinaryStream("file");

            if (flag == 1)
                IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath+"/rodents_helper"));
            else if (flag == 2)
                IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath+"/rodents_helper-shm"));
            else if (flag == 3)
                IOUtils.copy(inputStream, new FileOutputStream(databaseFilePath+"/rodents_helper-wal"));

            flag++;

        }

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

        //probably not necessary
        /*AppDatabase db = Room.databaseBuilder(context,
                        AppDatabase.class, "rodents_helper")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        db.close();*/

    }

}
