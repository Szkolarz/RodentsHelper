package com.android.rodentshelper.ROOM.Medicaments;

import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.android.rodentshelper.Alerts;
import com.android.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.android.rodentshelper.ROOM.AppDatabase;
import com.android.rodentshelper.ROOM.DAOMedicaments;
import com.android.rodentshelper.ROOM.DAORodents;
import com.android.rodentshelper.ROOM.Rodent.RodentModel;
import com.android.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;
import com.android.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddEditMedicaments extends AppCompatActivity {

    private EditText editTextName_med, editTextDescription_med, editTextPeriodicity_med;
    private TextView textViewDateStart_med, textViewDateEnd_med, textViewDate1_hidden, textViewDate2_hidden,
            textViewRodentRelationsInfo_med, textViewRodentRelations_med;
    private Button buttonEdit_med, buttonAdd_med, buttonSaveEdit_med, buttonDelete_med;
    private ListView listViewMed;
    private CheckBox checkBoxMed;
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

        ImageButton imageButtonDate_med1, imageButtonDate_med2;
        TextView textViewRequired_med = findViewById(R.id.textViewRequired_med);

        LinearLayout linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        editTextName_med = findViewById(R.id.editTextName_med);
        editTextDescription_med = findViewById(R.id.editTextDescription_med);
        editTextPeriodicity_med = findViewById(R.id.editTextPeriodicity_med);

        textViewDateStart_med = findViewById(R.id.textViewDateStart_med);
        textViewDateEnd_med = findViewById(R.id.textViewDateEnd_med);
        imageButtonDate_med1 = findViewById(R.id.imageButtonDate_med1);
        imageButtonDate_med2 = findViewById(R.id.imageButtonDate_med2);


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

        SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();
        DAOMedicaments daoMedicaments = db.daoMedicaments();

        List<RodentModel> rodentModel = daoRodents.getAllRodentsTEST();

        for(int i = 0; i < rodentModel.size(); i++) {
            arrayListID.add(rodentModel.get(i).getId());
            arrayListLV.add(rodentModel.get(i).getName());
        }

        // on below line we are setting adapter for our list view.
        listViewMed.setAdapter(adapter);

        setVisibilityByFlag(toolbar);


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


            List<MedicamentWithRodentsCrossRef> medicamentModel = daoMedicaments.getMedsWithRodents();


            int positionKey = Integer.parseInt(getIntent().getStringExtra("positionKey"));

            for (int j = 0; j < arrayListLV.size(); j ++) {
                try {
                    for (int i = 0; i < medicamentModel.get(positionKey).rodents.size(); i++) {
                        if (arrayListLV.get(j).equals(medicamentModel.get(positionKey).rodents.get(i).getName())) {
                            listViewMed.setItemChecked(j, true);
                            checkBoxMed.setChecked(true);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("There is no any rodent left in relation");
                }
            }
            checkCheckBox();


            buttonSaveEdit_med.setOnClickListener(view -> {

                if (editTextName_med.getText().toString().length() <= 0 ) {
                    textViewRequired_med.setVisibility(View.VISIBLE);
                    Alerts alert = new Alerts();
                    alert.alertLackOfData("Należy podać nazwę leku.", AddEditMedicaments.this);
                } else {


                    System.out.println(textViewDate1_hidden.getText().toString());

                    if (textViewDate1_hidden.getText().toString().equals(""))
                        dateFormat1 = null;
                    else
                        dateFormat1 = Date.valueOf(textViewDate1_hidden.getText().toString());
                    if (textViewDate2_hidden.getText().toString().equals(""))
                        dateFormat2 = null;
                    else
                        dateFormat2 = Date.valueOf(textViewDate2_hidden.getText().toString());


                    daoMedicaments.updateMedById(idKey, id_vetKey, editTextName_med.getText().toString(),
                            editTextDescription_med.getText().toString(), editTextPeriodicity_med.getText().toString(),
                            dateFormat1, dateFormat2);

                    daoMedicaments.DeleteAllRodentsMedsByMed(idKey);

                    getRodentMed(daoMedicaments);
                    db.close();

                    viewMedicaments();
                    finish();
                }

            });
        }


        textViewDateStart_med.setOnClickListener(view -> pickDate(dateSetListener1));

        imageButtonDate_med1.setOnClickListener(view -> pickDate(dateSetListener1));

        imageButtonDate_med2.setOnClickListener(view -> pickDate(dateSetListener2));

        textViewDateEnd_med.setOnClickListener(view -> pickDate(dateSetListener2));

        dateSetListener1 = (datePicker, year, month, day) -> {
            month += 1;
            String date = day + "/" + month + "/" + year;
            textViewDateStart_med.setText(date);
            dateFormat1 = Date.valueOf((year + "-" + month + "-" + day));
            textViewDate1_hidden.setText(dateFormat1.toString());
        };

        dateSetListener2 = (datePicker, year, month, day) -> {
            month += 1;
            String date = day + "/" + month + "/" + year;
            textViewDateEnd_med.setText(date);
            dateFormat2 = Date.valueOf((year + "-" + month + "-" + day));
            textViewDate2_hidden.setText(dateFormat2.toString());
        };

        checkBoxMed.setOnClickListener(view -> checkCheckBox());

        buttonAdd_med.setOnClickListener(view -> saveMedicament(textViewRequired_med));

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(AddEditMedicaments.this, ViewMedicaments.class);
            startActivity(intent);
            finish();
        });
    }


        private void pickDate (DatePickerDialog.OnDateSetListener dateSetListener) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditMedicaments.this,
                    android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }




    public void saveMedicament(TextView textViewRequired_med) {

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
            textViewRequired_med.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy podać nazwę leku.", this);
        } else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAOMedicaments daoMedicaments = db.daoMedicaments();
            daoMedicaments.insertRecordMed(new MedicamentModel(1, stringName, stringDescription, stringPeriodicity, (stringDate1), (stringDate2)));

            System.out.println("DODANO");
            getRodentMed(daoMedicaments);
            db.close();
            viewMedicaments();
        }
    }

    public void getRodentMed(DAOMedicaments rodentMedDao) {

        if (FlagSetup.getFlagMedAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentMedDao.insertRecordRodentMed(new RodentMedModel(prefsGetRodentId.getInt("rodentId", 0), rodentMedDao.getLastIdMed().get(0)));
            return;
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
                    rodentMedDao.insertRecordRodentMed(new RodentMedModel(arrayListID.get(i), idKey));
                }
            }
    }

    private void viewMedicaments() {
        startActivity(new Intent(getApplicationContext(), ViewMedicaments.class));
        finish();
    }


    private void checkCheckBox() {
        if (checkBoxMed.isChecked()) {
            listViewMed.setVisibility(View.VISIBLE);
            listViewMed.setSelected(true);
        }
        else {
            listViewMed.clearChoices();
            listViewMed.setVisibility(View.GONE);
        }
    }


    public void setVisibilityByFlag(Toolbar toolbar) {
        //2 = static pet relation
        if (FlagSetup.getFlagMedAdd() == 2) {
            toolbar.setTitle("Dodawanie leku");
            checkBoxMed.setVisibility(View.GONE);

            buttonAdd_med.setVisibility(View.VISIBLE);
            buttonEdit_med.setVisibility(View.GONE);
            buttonDelete_med.setVisibility(View.GONE);
            buttonSaveEdit_med.setVisibility(View.GONE);
            textViewRodentRelations_med.setVisibility(View.GONE);
            textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        }

        // 1 = adding new medicament
        if (FlagSetup.getFlagMedAdd() == 1) {
            toolbar.setTitle("Dodawanie leku");
            buttonAdd_med.setVisibility(View.VISIBLE);
            buttonEdit_med.setVisibility(View.GONE);
            buttonDelete_med.setVisibility(View.GONE);
            buttonSaveEdit_med.setVisibility(View.GONE);
            textViewRodentRelations_med.setVisibility(View.GONE);
            textViewRodentRelationsInfo_med.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagMedAdd() == 0) {
            toolbar.setTitle("Edytowanie leku");
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AddEditMedicaments.this, ViewMedicaments.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}
