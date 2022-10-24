package com.example.rodentshelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rodentshelper.SQL.SQLiteHelper;

import java.sql.Date;

public class AddRodents extends Activity {

    EditText editTextNotes, editTextName, editTextDate, editTextFur;
    Button buttonSaveRodent, buttonView;
    RadioButton radioButtonGender1, radioButtonGender2;


    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_rodent);

        editTextName = findViewById(R.id.editTextEditName);
        editTextDate = findViewById(R.id.editTextEditDate);
        editTextFur = findViewById(R.id.editTextEditFur);
        editTextNotes = findViewById(R.id.editTextEditNotes);

        radioButtonGender1 = findViewById(R.id.radioButtonEditGender1);
        radioButtonGender2 = findViewById(R.id.radioButtonEditGender2);

        radioGroup = findViewById(R.id.radioEditGroup1);

        buttonSaveRodent = findViewById(R.id.buttonEditSaveRodent);
        buttonView = findViewById(R.id.buttonEditView);


    }

    public void saveRodent(View view) {
        String stringName = editTextName.getText().toString();

        String stringDate = editTextDate.getText().toString();
        String stringFur = editTextFur.getText().toString();
        String stringNotes = editTextNotes.getText().toString();

        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedRadio);
        String stringGender = radioButton.getText().toString();

        if (stringName.length() <= 0 || stringNotes.length() <= 0) {
            Toast.makeText(AddRodents.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
            System.out.println("NEIN");
        }
        else {
            SQLiteHelper databaseHelper = new SQLiteHelper(AddRodents.this);
            RodentsModelClass rodentsModelClass = new RodentsModelClass(stringName, stringGender, Date.valueOf(stringDate), stringFur, stringNotes);
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
