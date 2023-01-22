package com.android.rodentshelper;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.rodentshelper.R;


public class Alerts {


    public void alertLackOfData (String message, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        alert.setTitle("Nie wpisano wymaganych opcji");
        alert.setMessage(message);
        alert.setPositiveButton("OK", (dialogInterface, i) -> Toast.makeText(context, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show());
        alert.create().show();
    }

    public void simpleError (String title, String message, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.InfoDialogStyle);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", (dialog, which) -> {

        });
        alert.create().show();
    }

    public void simpleInfo (String title, String message, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.InfoDialogStyle);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Rozumiem", (dialogInterface, i) -> {

        });
        alert.create().show();
    }

}
