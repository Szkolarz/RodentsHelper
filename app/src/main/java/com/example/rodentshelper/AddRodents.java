package com.example.rodentshelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodentshelper.SQL.SQLiteHelper;

public class AddRodents extends Activity {

    EditText editTextNotes, editTextName;
    Button buttonSaveRodent, buttonView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_rodent);

        editTextName = findViewById(R.id.editTextListName);
        editTextNotes = findViewById(R.id.editTextNotes);

        buttonSaveRodent = findViewById(R.id.buttonSaveRodent);
        buttonView = findViewById(R.id.buttonView);


    }

    public void saveRodent(View view) {
        String stringName = editTextName.getText().toString();
        String stringNotes = editTextNotes.getText().toString();

        if (stringName.length() <= 0 || stringNotes.length() <= 0) {
            Toast.makeText(AddRodents.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
            System.out.println("NEIN");
        }
        else {
            SQLiteHelper databaseHelper = new SQLiteHelper(AddRodents.this);
            RodentsModelClass rodentsModelClass = new RodentsModelClass(stringName, stringNotes);
            databaseHelper.addNeRodent(rodentsModelClass);
            Toast.makeText(AddRodents.this, "Pomyślnie dodano", Toast.LENGTH_SHORT).show();
            System.out.println("DODANO");
            finish();
            startActivity(getIntent());
        }

    }

    public void viewRodents(View view) {
        Intent intent = new Intent(AddRodents.this, ViewRodents.class);
        startActivity(intent);
    }



}
