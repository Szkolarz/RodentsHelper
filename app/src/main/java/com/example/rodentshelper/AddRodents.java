package com.example.rodentshelper;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.rodentshelper.SQL.DBHelperAnimal;

import java.sql.Date;
import java.util.Calendar;

public class AddRodents extends Activity {

    EditText editTextNotes, editTextName, editTextDate, editTextFur;
    Button buttonSaveRodent, buttonView;
    RadioButton radioButtonGender1, radioButtonGender2;


    RadioGroup radioGroup;
    RadioButton radioButton;

    private TextView textViewDate;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String dateFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rodent);

        editTextName = findViewById(R.id.editTextEditName);

        editTextFur = findViewById(R.id.editTextEditFur);
        editTextNotes = findViewById(R.id.editTextEditNotes);

        radioButtonGender1 = findViewById(R.id.radioButtonEditGender1);
        radioButtonGender2 = findViewById(R.id.radioButtonEditGender2);

        radioGroup = findViewById(R.id.radioEditGroup1);

        buttonSaveRodent = findViewById(R.id.buttonEditSaveRodent);
        buttonView = findViewById(R.id.buttonEditView);

        textViewDate = findViewById(R.id.textViewDate);


        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRodents.this,
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
                textViewDate.setText(date);

                dateFormat = (year + "-" + month + "-" + day);
            }
        };


    }

    public void saveRodent(View view) {
        String stringName = editTextName.getText().toString();

        String stringDate = dateFormat;
        String stringFur = editTextFur.getText().toString();
        String stringNotes = editTextNotes.getText().toString();

        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedRadio);
        String stringGender = radioButton.getText().toString();

        if (stringName.length() <= 0) {
            Toast.makeText(AddRodents.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
        }
        else {
            DBHelperAnimal databaseHelper = new DBHelperAnimal(AddRodents.this);
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
