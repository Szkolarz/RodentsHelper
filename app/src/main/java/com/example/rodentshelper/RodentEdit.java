package com.example.rodentshelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rodentshelper.SQL.SQLiteHelper;

import java.sql.Date;
import java.util.Calendar;

public class RodentEdit extends AppCompatActivity {


    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String dateFormat;

    EditText editTextEditName, editTextEditFur, editTextEditNotes;
    TextView textViewEditDate;
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

        textViewEditDate = findViewById(R.id.textViewEditDate);

        textViewEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RodentEdit.this,
                        android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date = day + "/" + month + "/" + year;
                textViewEditDate.setText(date);

                dateFormat = (year + "-" + month + "-" + day);
            }
        };


        Intent intent = getIntent();


        radioEditGroup = findViewById(R.id.radioEditGroup1);

        radioButton1 = (RadioButton) findViewById(R.id.radioButtonEditGender1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButtonEditGender2);


        editTextEditName = findViewById(R.id.editTextEditName);
        textViewEditDate = findViewById(R.id.textViewEditDate);
        editTextEditFur = findViewById(R.id.editTextEditFur);
        editTextEditNotes = findViewById(R.id.editTextEditNotes);
        buttonEditSaveRodent = findViewById(R.id.buttonEditSaveRodent);

        String genderKey = intent.getStringExtra("genderKey");

        if (genderKey.equals("Samiec"))
            radioButton1.setChecked(true);
        else
            radioButton2.setChecked(true);


        editTextEditName.setText(intent.getStringExtra("nameKey"));
        textViewEditDate.setText(intent.getStringExtra("birthKey"));

        editTextEditFur.setText(intent.getStringExtra("furKey"));
        editTextEditNotes.setText(intent.getStringExtra("notesKey"));




    }


    public void editRodent(android.view.View view) {

        String stringName = editTextEditName.getText().toString();
        String stringBirth = dateFormat;
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