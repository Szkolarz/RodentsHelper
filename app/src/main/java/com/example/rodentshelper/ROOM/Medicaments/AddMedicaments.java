package com.example.rodentshelper.ROOM.Medicaments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM._MTM.RodentMedModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM._MTM.RodentVetModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMedicaments extends Activity {

    EditText editTextName_med, editTextDescription_med, editTextPeriodicity_med;
    TextView textViewDateStart_med, textViewDateEnd_med, textViewDate1_hidden, textViewDate2_hidden,
            textViewRodentRelationsInfo_med, textViewRodentRelations_med;
    Button buttonEdit_med, buttonAdd_med, buttonSaveEdit_med, buttonDelete_med;
    ImageView imageViewDate1_med, imageViewDate2_med;
    ListView listViewMed;
    CheckBox checkBoxMed;


    private DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;
    private Date dateFormat1 = null, dateFormat2 = null;

    //pelna lista zwierzat
    private ArrayList<String> arrayListLV;
    //wybrane ID
    private ArrayList<Integer> arrayListID;
    //koncowa lista z zaznaczonymi zwierzetami
    private ArrayList<Integer> arrayListSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicaments_item_list);

        editTextName_med = findViewById(R.id.editTextName_med);
        editTextDescription_med = findViewById(R.id.editTextDescription_med);
        editTextPeriodicity_med = findViewById(R.id.editTextPeriodicity_med);

        textViewDateStart_med = findViewById(R.id.textViewDateStart_med);
        textViewDateEnd_med = findViewById(R.id.textViewDateEnd_med);
        imageViewDate1_med = findViewById(R.id.imageViewDate1_med);
        imageViewDate2_med = findViewById(R.id.imageViewDate2_med);

        buttonEdit_med = findViewById(R.id.buttonEdit_med);
        buttonAdd_med = findViewById(R.id.buttonAdd_med);
        buttonSaveEdit_med = findViewById(R.id.buttonSaveEdit_med);
        buttonDelete_med = findViewById(R.id.buttonDelete_med);

        textViewDate1_hidden = findViewById(R.id.textViewDate1_hidden);
        textViewDate2_hidden = findViewById(R.id.textViewDate2_hidden);

        textViewRodentRelations_med = findViewById(R.id.textViewRodentRelations_med);
        textViewRodentRelationsInfo_med = findViewById(R.id.textViewRodentRelationsInfo_med);

        listViewMed = findViewById(R.id.listViewMed);
        listViewMed.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewMed.setItemsCanFocus(false);
        listViewMed.setVisibility(View.GONE);

        checkBoxMed = findViewById(R.id.checkBoxMed);

        arrayListID = new ArrayList<>();
        arrayListLV = new ArrayList<>();
        arrayListSelected = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayListLV);

        // on below line we are setting adapter for our list view.
        listViewMed.setAdapter(adapter);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO rodentDao = db.dao();


        List<RodentModel> rodentModel = rodentDao.getAllRodents();

        for(int i = 0; i < rodentModel.size(); i++) {
            arrayListID.add(rodentModel.get(i).getId());
            arrayListLV.add(rodentModel.get(i).getName());
        }


        setVisibilityByFlag();


        if (FlagSetup.getFlagMedAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            Integer id_vetKey = Integer.parseInt(getIntent().getStringExtra("id_vetKey"));
            String nameKey = getIntent().getStringExtra("nameKey");
            String descriptionKey = getIntent().getStringExtra("descriptionKey");
            String periodicityKey = getIntent().getStringExtra("periodicityKey");
            String date_startKey = getIntent().getStringExtra("date_startKey");
            String date_endKey = getIntent().getStringExtra("date_endKey");


            if (date_startKey.equals("null")) {
                date_startKey = "nie podano";
                textViewDate1_hidden.setText(null);
            }
            else
                textViewDate1_hidden.setText(date_startKey);

            if (date_endKey.equals("null")) {
                date_endKey = "nie podano";
                textViewDate2_hidden.setText(null);
            }
            else
                textViewDate2_hidden.setText(date_endKey);

            //System.out.println(textViewDate1_hidden.getText().toString());
            //System.out.println(textViewDate2_hidden.getText().toString());

            editTextName_med.setText(nameKey);
            editTextDescription_med.setText(descriptionKey);
            editTextPeriodicity_med.setText(periodicityKey);

            textViewDateStart_med.setText(date_startKey);
            textViewDateEnd_med.setText(date_endKey);


            DAO medDao = db.dao();

            List<String> list = medDao.getAllRodentsMeds(idKey);

            for (int j = 0; j < arrayListLV.size(); j ++) {
                for(int i = 0; i < list.size(); i++) {
                    if (arrayListLV.get(j).equals(list.get(i))) {
                        listViewMed.setItemChecked(j, true);
                        checkBoxMed.setChecked(true);
                    }
                }
            }

            checkCheckBox(checkBoxMed, listViewMed);



            buttonSaveEdit_med.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(textViewDate1_hidden.getText().toString());

                    if (textViewDate1_hidden.getText().toString().equals(""))
                        dateFormat1 = null;
                    else
                        dateFormat1 = Date.valueOf(textViewDate1_hidden.getText().toString());
                    if (textViewDate2_hidden.getText().toString().equals(""))
                        dateFormat2 = null;
                    else
                        dateFormat2 = Date.valueOf(textViewDate2_hidden.getText().toString());



                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAO medDao = db.dao();
                    medDao.updateMedById(idKey, id_vetKey, editTextName_med.getText().toString(),
                            editTextDescription_med.getText().toString(), editTextPeriodicity_med.getText().toString(),
                            dateFormat1, dateFormat2);

                    medDao.DeleteAllRodentsMedsByMed(idKey);

                    getRodentMed(medDao);

                    viewMedicaments();
                    finish();

                }
            });
        }


        textViewDateStart_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(dateSetListener1);
            }
        });

        textViewDateEnd_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(dateSetListener2);
            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                textViewDateStart_med.setText(date);
                dateFormat1 = Date.valueOf((year + "-" + month + "-" + day));
                textViewDate1_hidden.setText(dateFormat1.toString());
            }
        };

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                textViewDateEnd_med.setText(date);
                dateFormat2 = Date.valueOf((year + "-" + month + "-" + day));
                textViewDate2_hidden.setText(dateFormat2.toString());
            }
        };

        checkBoxMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxMed.isChecked()) {
                    listViewMed.setVisibility(View.VISIBLE);
                }
                else {
                    listViewMed.clearChoices();
                    listViewMed.setVisibility(View.GONE);
                }
            }
        });

        buttonAdd_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedicament();
            }
        });


    }


        private void pickDate (DatePickerDialog.OnDateSetListener dateSetListener) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicaments.this,
                    android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }




    public void saveMedicament() {

        String stringName = editTextName_med.getText().toString();
        String stringDescription = editTextDescription_med.getText().toString();
        String stringPeriodicity = editTextPeriodicity_med.getText().toString();
        Date stringDate1 = (dateFormat1);
        Date stringDate2 = (dateFormat2);

        System.out.println(dateFormat1);


        if (stringDate1 == (null)) {
            stringDate1 = null;
            System.out.println(stringDate1);
        }
        if (stringDate2 == (null))
            stringDate2 = null;



        if (stringName.length() <= 0 ) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Nie wpisano wymaganych opcji");
            alert.setMessage("Należy podać nazwę leku");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddMedicaments.this, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }
        else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO medDao = db.dao();

            medDao.insertRecordMed(new MedicamentModel(1, stringName, stringDescription, stringPeriodicity, (stringDate1), (stringDate2)));

            System.out.println("DODANO");
            getRodentMed(medDao);

            viewMedicaments();
        }

    }

    public void getRodentMed(DAO rodentMedDao) {

        if (FlagSetup.getFlagMedAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentMedDao.insertRecordRodentMed(new RodentMedModel(Integer.valueOf(prefsGetRodentId.getInt("rodentId", 0)), rodentMedDao.getLastIdMed().get(0)));


        }

        int listViewLength = listViewMed.getCount();
        SparseBooleanArray checked = listViewMed.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                arrayListSelected.add(i);

                if (FlagSetup.getFlagMedAdd() == 1)
                    rodentMedDao.insertRecordRodentMed(new RodentMedModel(arrayListID.get(i), rodentMedDao.getLastIdMed().get(0)));
                else {
                    Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
                    System.out.println("DZIKDSAKSAODKSA\ng\ng" + idKey);
                    rodentMedDao.insertRecordRodentMed(new RodentMedModel(arrayListID.get(i), idKey));
                }
            }
    }

    private void viewMedicaments() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewMedicaments.class));
    }

    private void viewRodents() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
    }

    private void checkCheckBox(CheckBox checkBoxMed, ListView listViewMed) {
        if (checkBoxMed.isChecked()) {
            listViewMed.setVisibility(View.VISIBLE);
            listViewMed.setSelected(true);
        }
        else {
            listViewMed.clearChoices();
            listViewMed.setVisibility(View.GONE);
        }
    }


    public void setVisibilityByFlag() {
        //2 = static pet relation
        if (FlagSetup.getFlagMedAdd() == 2) {
            checkBoxMed.setVisibility(View.GONE);

            buttonAdd_med.setVisibility(View.VISIBLE);
            buttonEdit_med.setVisibility(View.GONE);
            buttonDelete_med.setVisibility(View.GONE);
            buttonSaveEdit_med.setVisibility(View.GONE);
            textViewRodentRelations_med.setVisibility(View.GONE);
            textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        }

        // 1 = adding new vet
        if (FlagSetup.getFlagMedAdd() == 1) {
            buttonAdd_med.setVisibility(View.VISIBLE);
            buttonEdit_med.setVisibility(View.GONE);
            buttonDelete_med.setVisibility(View.GONE);
            buttonSaveEdit_med.setVisibility(View.GONE);
            textViewRodentRelations_med.setVisibility(View.GONE);
            textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagMedAdd() == 0) {
            buttonAdd_med.setVisibility(View.GONE);
            buttonEdit_med.setVisibility(View.GONE);
            buttonDelete_med.setVisibility(View.GONE);
            buttonSaveEdit_med.setVisibility(View.VISIBLE);
            textViewRodentRelations_med.setVisibility(View.GONE);
            textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}
