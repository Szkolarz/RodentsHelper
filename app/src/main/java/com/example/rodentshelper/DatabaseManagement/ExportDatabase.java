package com.example.rodentshelper.DatabaseManagement;

import android.content.Context;

import androidx.room.Room;

import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.example.rodentshelper.Encyclopedia.General.GeneralModel;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.SQL.Querries;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportDatabase {

    public static void exportDatabase(File dbFile) {

        try {
            Querries dbQuerries = new Querries();
            InputStream targetStream = new FileInputStream(dbFile);
            dbQuerries.exportLocalDatabase(targetStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            System.out.println("CYK");
            }

        fileOrDirectory.delete();

    }

    public static void importDatabase(Context context, String login, File databaseDirectory) throws IOException, SQLException, InterruptedException {
        Querries dbQuerries = new Querries();
        ResultSet resultSetImport = dbQuerries.importLocalDatabase(login);


        String s = context.getDatabasePath("rodents_helper").toString();
        String s1 = s.substring(0,s.lastIndexOf("/") + 1);
        s1.trim();


        while (resultSetImport.next()) {
            Blob blob = resultSetImport.getBlob("file");
            InputStream inputStream = resultSetImport.getBinaryStream("file");

            byte[] byte_array = blob.getBytes(1, (int)blob.length());
            FileOutputStream fos = new FileOutputStream(s1+"/filename");
            fos.write(byte_array);
            fos.close();












            /*byte[] z = resultSetImport.getBytes("file");

            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO dao = db.dao();

            dao.insertRecordDBManagement(new DatabaseManagementModel(
                    "h", resultSetImport.getBinaryStream("file")
            ));

            db.close();*/

           /* String s = context.getDatabasePath("rodents_helper").toString();
            String s1 = s.substring(0,s.lastIndexOf("/") + 1);
            s1.trim();

            try {

                FileOutputStream out = new FileOutputStream(s1+"/rodents_helper21");
                out.write(z);
                out.close();

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }*/


/*

            System.out.println("AQW");
            System.out.println(resultSetImport.getBinaryStream("file").toString() + " aaaa");

            File file = new File(resultSetImport.getBinaryStream("file").toString());



            InputStream in = resultSetImport.getBinaryStream("file");
            OutputStream out = new FileOutputStream(s1+"/rodents_helper21");

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
*/






        }
    }

}
