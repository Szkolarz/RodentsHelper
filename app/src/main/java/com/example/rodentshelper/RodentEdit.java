package com.example.rodentshelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.rodentshelper.SQL.SQLiteHelper;

import java.sql.Date;

public class RodentEdit extends AppCompatActivity {


    private RodentsModelClass rodentsModelClass = new RodentsModelClass();




    EditText editTextEditName, editTextEditDate, editTextEditFur, editTextEditNotes;
    Button buttonEditSaveRodent;
    RadioButton radioButtonEditGender1, radioButtonEditGender2;
    RadioGroup radioEditGroup;
    RadioButton radioButton, radioButton1, radioButton2;

    Context context;
    SQLiteHelper databaseHelper = new SQLiteHelper(context);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rodent_edit);


        Intent intent = getIntent();


        radioEditGroup = findViewById(R.id.radioEditGroup1);

        radioButton1 = (RadioButton) findViewById(R.id.radioButtonEditGender1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButtonEditGender2);


        editTextEditName = findViewById(R.id.editTextEditName);
        editTextEditDate = findViewById(R.id.editTextEditDate);
        editTextEditFur = findViewById(R.id.editTextEditFur);
        editTextEditNotes = findViewById(R.id.editTextEditNotes);
        buttonEditSaveRodent = findViewById(R.id.buttonEditSaveRodent);

        String genderKey = intent.getStringExtra("genderKey");

        if (genderKey.equals("Samiec"))
            radioButton1.setChecked(true);
        else
            radioButton2.setChecked(true);


        editTextEditName.setText(intent.getStringExtra("nameKey"));
        editTextEditDate.setText(intent.getStringExtra("birthKey"));

        editTextEditFur.setText(intent.getStringExtra("furKey"));
        editTextEditNotes.setText(intent.getStringExtra("notesKey"));




    }


    public void editRodent(android.view.View view) {

        System.out.println("kjhgfd");

        String stringName = editTextEditName.getText().toString();
        String stringBirth = editTextEditDate.getText().toString();;
        String stringFur =  editTextEditFur.getText().toString();
        String stringNotes =  editTextEditNotes.getText().toString();

        int selectedId = radioEditGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String stringGender = radioButton.getText().toString();


        SQLiteHelper databaseHelper = new SQLiteHelper(RodentEdit.this);
        RodentsModelClass rodentsModelClass = new RodentsModelClass( (Integer.valueOf(getIntent().getIntExtra("idKey", 0))), stringName, stringGender, stringBirth, stringFur, stringNotes);
        databaseHelper.updateRodent(rodentsModelClass);


        finish();


        Intent intent = new Intent(RodentEdit.this, ViewRodents.class);
        startActivity(intent);


    }


}