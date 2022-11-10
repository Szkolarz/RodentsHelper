package com.example.rodentshelper.Visits;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.Medicaments.MedicamentModel;
import com.example.rodentshelper.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.MTM.RodentMedModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddVisits extends Activity {

    EditText editTextReason_visit;
    TextView textViewDate_visit, textViewTime_visit, textViewDate1_visitHidden,
            textViewVetRelationsInfo_visit, textViewVetRelations_visit;
    Button buttonEdit_visit, buttonAdd_visit, buttonSaveEdit_visit, buttonDelete_visit;
    //ImageView imageViewDate1_med, imageViewDate2_med;
    ListView listViewVisit;
    CheckBox checkBoxVisit1, checkBoxVisit2;


    private DatePickerDialog.OnDateSetListener dateSetListener1;
    private String dateFormat1 = null;

    //pelna lista zwierzat
    private ArrayList<String> arrayListLV;
    //wybrane ID
    private ArrayList<Integer> arrayListID;
    //koncowa lista z zaznaczonymi zwierzetami
    private ArrayList<Integer> arrayListSelected;

    private int hour, minute;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visits_item_list);

        editTextReason_visit = findViewById(R.id.editTextReason_visit);

        textViewDate_visit = findViewById(R.id.textViewDate_visit);
        textViewTime_visit = findViewById(R.id.textViewTime_visit);


        buttonEdit_visit = findViewById(R.id.buttonEdit_visit);
        buttonAdd_visit = findViewById(R.id.buttonAdd_visit);
        buttonSaveEdit_visit = findViewById(R.id.buttonSaveEdit_visit);
        buttonDelete_visit = findViewById(R.id.buttonDelete_visit);

        textViewDate1_visitHidden = findViewById(R.id.textViewDate1_visitHidden);

        textViewVetRelationsInfo_visit = findViewById(R.id.textViewVetRelationsInfo_visit);
        textViewVetRelations_visit = findViewById(R.id.textViewVetRelations_visit);

        listViewVisit = findViewById(R.id.listViewVisit);
        listViewVisit.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewVisit.setItemsCanFocus(false);
        listViewVisit.setVisibility(View.GONE);

        checkBoxVisit1 = findViewById(R.id.checkBoxVisit1);
        checkBoxVisit2 = findViewById(R.id.checkBoxVisit2);

        arrayListID = new ArrayList<>();
        arrayListLV = new ArrayList<>();
        arrayListSelected = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, arrayListLV);

        listViewVisit.setAdapter(adapter);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO vetDao = db.dao();


        List<VetModel> vetModel = vetDao.getAllVets();

        for(int i = 0; i < vetModel.size(); i++) {
            arrayListID.add(vetModel.get(i).getId());
            arrayListLV.add(vetModel.get(i).getName());
        }


        if (FlagSetup.getFlagVisitAdd() == 1) {
            buttonAdd_visit.setVisibility(View.VISIBLE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.GONE);

            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        } else {
            buttonAdd_visit.setVisibility(View.GONE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.VISIBLE);

            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);


            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_vetKey = (getIntent().getStringExtra("id_vetKey"));
            String dateKey = getIntent().getStringExtra("dateKey");
            String timeKey = getIntent().getStringExtra("timeKey");
            String reasonKey = getIntent().getStringExtra("reasonKey");


            editTextReason_visit.setText(reasonKey);
            textViewTime_visit.setText(timeKey);

            textViewDate_visit.setText(dateKey);
            textViewDate1_visitHidden.setText(dateKey);


            DAO visitDao = db.dao();

            if (!(id_vetKey).equals("null")) {
                List<String> list = visitDao.getAllVisitsVets(Integer.valueOf(id_vetKey));

                for (int j = 0; j < arrayListLV.size(); j++) {
                    for (int i = 0; i < list.size(); i++) {
                        if (arrayListLV.get(j).equals(list.get(i))) {
                            listViewVisit.setItemChecked(j, true);
                            checkBoxVisit1.setChecked(true);
                        }
                    }
                }
            }
            checkCheckBox(checkBoxVisit1, listViewVisit);



            buttonSaveEdit_visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dateFormat1 = textViewDate1_visitHidden.getText().toString();


                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAO visitDao = db.dao();
                    visitDao.updateVisitById(idKey, null,  Date.valueOf(dateFormat1),
                            textViewTime_visit.getText().toString(), editTextReason_visit.getText().toString());

                    // visitDao.SetVisitsIdVetNull(idKey);


                    if (checkBoxVisit1.isChecked()) {
                        visitDao.SetVisitsIdVet(getVisitVet(visitDao), idKey);
                    }
                    else {
                        if (getVisitVet(visitDao) != null)
                            visitDao.SetVisitsIdVetNull(Integer.valueOf(id_vetKey));
                    }
                    viewVisits();

                }
            });
        }



        textViewDate_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(dateSetListener1);
            }
        });


        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                textViewDate_visit.setText(date);
                dateFormat1 = (year + "-" + month + "-" + day);
                textViewDate1_visitHidden.setText(dateFormat1);
            }
        };


        checkBoxVisit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxVisit1.isChecked()) {
                    listViewVisit.setVisibility(View.VISIBLE);
                }
                else {
                    listViewVisit.setVisibility(View.GONE);
                }
            }
        });

        buttonAdd_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVisit();
            }
        });


    }


        private void pickDate (DatePickerDialog.OnDateSetListener dateSetListener) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddVisits.this,
                    android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }

        public void onClickTime(View view) {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    textViewTime_visit.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

            timePickerDialog.setTitle("Wybierz godzinę");
            timePickerDialog.show();
        }



    public void saveVisit() {



        String stringDate1 = dateFormat1;
        String timeKey = textViewTime_visit.getText().toString();
        String reasonKey = editTextReason_visit.getText().toString();





        if (stringDate1 == null ) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Nie wpisano wymaganych opcji");
            alert.setMessage("Należy podać nazwę leku");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddVisits.this, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }
        else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO visitDao = db.dao();

            Integer id_vetKey = getVisitVet(visitDao);

            visitDao.insertRecordVisit(new VisitModel(id_vetKey, Date.valueOf(stringDate1),
                    timeKey, reasonKey));

            System.out.println("DODANO");
            getVisitVet(visitDao);
            viewVisits();
        }

    }

    public Integer getVisitVet(DAO visitVetDao) {
        int listViewLength = listViewVisit.getCount();
        Integer id_vet = null;
        SparseBooleanArray checked = listViewVisit.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                arrayListSelected.add(i);

                id_vet = visitVetDao.getIdofVet(arrayListID.get(i));

               /* if (FlagSetup.getFlagMedAdd() == 1)
                    visitVetDao.insertRecordRodentMed(new RodentMedModel(arrayListID.get(i), rodentMedDao.getLastIdMed().get(0) ));
                else {
                    Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
                    visitVetDao.insertRecordRodentMed(new RodentMedModel(arrayListID.get(i), idKey));
                }*/
                return id_vet;
            }
        return null;
    }



    private void viewVisits() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewVisits.class));
    }

    private void viewRodents() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
    }

    private void checkCheckBox(CheckBox checkBoxVisit1, ListView listViewVisit) {
        if (checkBoxVisit1.isChecked()) {
            listViewVisit.setVisibility(View.VISIBLE);
            listViewVisit.setSelected(true);
        }
        else {
            listViewVisit.setVisibility(View.GONE);

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            viewVisits();
        }
        return super.onKeyDown(keyCode, event);
    }



}
