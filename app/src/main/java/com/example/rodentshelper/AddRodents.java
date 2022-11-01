package com.example.rodentshelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.sql.Date;
import java.util.Calendar;

public class AddRodents extends Activity {

    EditText editTextNotes, editTextName, editTextFur;
    Button buttonAdd_rodent, buttonEdit_rodent;
    RadioButton radioButtonGender1, radioButtonGender2;

    RadioGroup radioGroup;
    RadioButton radioButton;

    private TextView textViewDate, textViewDate_hidden;

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

        buttonAdd_rodent = findViewById(R.id.buttonAdd_rodent);


        buttonEdit_rodent = findViewById(R.id.buttonSaveEdit_rodent);


        textViewDate = findViewById(R.id.textViewDate);
        textViewDate_hidden = findViewById(R.id.textViewDate_hidden);



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
                textViewDate_hidden.setText(dateFormat);

            }
        };


        buttonAdd_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRodent();

            }
        });

        if (FlagSetup.getFlagRodentAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            Integer id_animalKey = Integer.parseInt(getIntent().getStringExtra("id_animalKey"));
            String nameKey = getIntent().getStringExtra("nameKey");
            String genderKey = getIntent().getStringExtra("genderKey");
            String birthKey = getIntent().getStringExtra("birthKey");
            String furKey = getIntent().getStringExtra("furKey");
            String notesKey = getIntent().getStringExtra("notesKey");

            if (genderKey.equals("Samiec"))
                radioButtonGender1.setChecked(true);
            else
                radioButtonGender2.setChecked(true);



            editTextName.setText(nameKey);
            textViewDate.setText(birthKey);
            textViewDate_hidden.setText(birthKey);
            editTextFur.setText(furKey);
            editTextNotes.setText(notesKey);


            buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedRadio = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedRadio);


                    dateFormat = textViewDate_hidden.getText().toString();

                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAO rodentDao = db.dao();
                    rodentDao.updateRodentById(idKey, id_animalKey, editTextName.getText().toString(),
                            radioButton.getText().toString(), Date.valueOf(dateFormat),
                            editTextFur.getText().toString(), editTextNotes.getText().toString());
                    viewRodents();

                }
            });
        }


        /*buttonEdit_rodent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewRodents.class));
            }
        });*/

    }

    public void saveRodent() {
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

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO rodentDao = db.dao();
            rodentDao.insertRecordRodent(new RodentModel(1, stringName, stringGender, Date.valueOf(stringDate), stringFur, stringNotes));

            System.out.println("DODANO");
            viewRodents();
        }

    }

    private void viewRodents() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }



}
