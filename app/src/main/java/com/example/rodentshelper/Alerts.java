package com.example.rodentshelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM.Notes.ViewNotes;

import java.util.List;


public class Alerts {


    public void alertDeleteNotes(Context context, Integer id) {

    }

    public void alertLackOfData (String message, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        alert.setTitle("Nie wpisano wymaganych opcji");
        //alert.setMessage("Należy podać nazwę leku");
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }


    public void simpleError (String title, String message, Context context) {
        //AlertDialog.Builder alert = new AlertDialog.Builder(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.InfoDialogStyle);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Błąd", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    public void simpleInfo (String title, String message, Context context) {
        //AlertDialog.Builder alert = new AlertDialog.Builder(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.InfoDialogStyle);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Rozumiem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }

}
