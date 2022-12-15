package com.example.rodentshelper.ROOM.Visits;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ROOM.DAOMedicaments;
import com.example.rodentshelper.ROOM.DAORodents;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.DAOVisits;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;
import com.example.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

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
    ListView listViewVisit, listViewVisit2;
    CheckBox checkBoxVisit1, checkBoxVisit2, checkBoxVisit3;

    private AppDatabase getAppDatabase () {
        return Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
    }

    private DAOVisits getDaoVisits () {
        return getAppDatabase().daoVisits();
    }

    private DAOVets getDaoVets () {
        return getAppDatabase().daoVets();
    }

    private DAORodents getDaoRodents () {
        return getAppDatabase().daoRodents();
    }


    private DatePickerDialog.OnDateSetListener dateSetListener1;
    private String dateFormat1 = null;

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
        listViewVisit2 = findViewById(R.id.listViewVisit2);
        listViewVisit2.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listViewVisit2.setItemsCanFocus(false);
        listViewVisit2.setVisibility(View.GONE);

        checkBoxVisit1 = findViewById(R.id.checkBoxVisit1);
        checkBoxVisit2 = findViewById(R.id.checkBoxVisit2);
        checkBoxVisit3 = findViewById(R.id.checkBoxVisit3);


        //pelna lista zwierzat
         ArrayList<String> arrayListLV;
        //wybrane ID
         ArrayList<Integer> arrayListID;
        //koncowa lista z zaznaczonymi zwierzetami
         ArrayList<Integer> arrayListSelected;

         ArrayList<String> arrayListLV2;
         ArrayList<Integer> arrayListID2;
         ArrayList<Integer> arrayListSelected2;

        arrayListID = new ArrayList<>();
        arrayListLV = new ArrayList<>();
        arrayListSelected = new ArrayList<>();
        arrayListID2 = new ArrayList<>();
        arrayListLV2 = new ArrayList<>();
        arrayListSelected2 = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, arrayListLV);
        List<VetModel> vetModel = getDaoVets().getAllVets();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayListLV2);
        SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        List<RodentModel> rodentModel = getDaoRodents().getAllRodents(prefsFirstStart.getInt("prefsFirstStart", 0));

        for(int i = 0; i < vetModel.size(); i++) {
            arrayListID.add(vetModel.get(i).getId());
            arrayListLV.add(vetModel.get(i).getName());
        }

        for(int i = 0; i < rodentModel.size(); i++) {
            arrayListID2.add(rodentModel.get(i).getId());
            arrayListLV2.add(rodentModel.get(i).getName());
        }

        listViewVisit.setAdapter(adapter);
        listViewVisit2.setAdapter(adapter2);

        setVisibilityByFlag();

        //EDIT
        if (FlagSetup.getFlagVisitAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_vetKey = (getIntent().getStringExtra("id_vetKey"));
            String dateKey = getIntent().getStringExtra("dateKey");
            String timeKey = getIntent().getStringExtra("timeKey");
            String reasonKey = getIntent().getStringExtra("reasonKey");

            editTextReason_visit.setText(reasonKey);
            textViewTime_visit.setText(timeKey);

            textViewDate_visit.setText(dateKey);
            textViewDate1_visitHidden.setText(dateKey);


            List<VisitsWithRodentsCrossRef> visitModel = getDaoVisits().getVisitsWithRodents();


            Integer positionKey = Integer.parseInt(getIntent().getStringExtra("positionKey"));

            try {
                if (!(id_vetKey).equals("null")) {
                    List<String> list = getDaoVisits().getAllVisitsVets(Integer.valueOf(id_vetKey));
                    for (int i = 0; i < arrayListLV.size(); i++) {
                        for (int j = 0; j < list.size(); j++) {
                            if (arrayListLV.get(i).equals(list.get(j))) {
                                listViewVisit.setItemChecked(i, true);
                                checkBoxVisit1.setChecked(true);
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("There is no any vet left in relation");
            }

            try {
                for (int i = 0; i < arrayListLV2.size(); i ++) {
                    for (int j = 0; j < visitModel.get(positionKey).rodents.size(); j++) {
                        if (arrayListLV2.get(i).equals(visitModel.get(positionKey).rodents.get(j).getName() )) {
                            listViewVisit2.setItemChecked(i, true);
                            checkBoxVisit2.setChecked(true);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("There is no any rodent left in relation");
            }


            checkCheckBox();

            buttonSaveEdit_visit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        saveEditVisit(idKey, id_vetKey, arrayListSelected, arrayListID, arrayListSelected2, arrayListID2);
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
                checkCheckBox();
            }
        });

        checkBoxVisit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox();
            }
        });

        buttonAdd_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVisit(arrayListSelected, arrayListID, arrayListSelected2, arrayListID2);
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



    public void saveVisit(ArrayList<Integer> arrayListSelected, ArrayList<Integer> arrayListID, ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2) {

        String stringDate1 = dateFormat1;
        String timeKey = textViewTime_visit.getText().toString();
        String reasonKey = editTextReason_visit.getText().toString();


        if (stringDate1 == null ) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Nie wpisano wymaganych opcji");
            alert.setMessage("Należy wpisać datę wizyty");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddVisits.this, "Wprowadź wymagane dane", Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();
        }
        else {

            Integer id_vetKey = getVisitVet(getDaoVets(), arrayListSelected, arrayListID);

            getDaoVisits().insertRecordVisit(new VisitModel(id_vetKey, Date.valueOf(stringDate1),
                    timeKey, reasonKey));

            System.out.println("DODANO");
            getVisitVet(getDaoVets(), arrayListSelected, arrayListID);
            getRodentVisit(getDaoVisits(), arrayListSelected2, arrayListID2);

            viewVisits();
        }

    }

    private void saveEditVisit(Integer idKey, String id_vetKey, ArrayList<Integer> arrayListSelected, ArrayList<Integer> arrayListID,
                               ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2) {
        dateFormat1 = textViewDate1_visitHidden.getText().toString();


        getDaoVisits().updateVisitById(idKey, null,  Date.valueOf(dateFormat1),
                textViewTime_visit.getText().toString(), editTextReason_visit.getText().toString());

        // visitDao.SetVisitsIdVetNull(idKey);


        if (checkBoxVisit1.isChecked()) {
            getDaoVisits().SetVisitsIdVet(getVisitVet(getDaoVets(), arrayListSelected,arrayListID), idKey);
        }
        else {
            if (getVisitVet(getDaoVets(), arrayListSelected,arrayListID) != null)
                getDaoVisits().SetVisitsIdVetNull(Integer.valueOf(id_vetKey));
        }

        getDaoVisits().DeleteAllRodentsVisitsByVisit(idKey);

        getRodentVisit(getDaoVisits(), arrayListSelected2, arrayListID2);

        viewVisits();
    }


    private void getRodentVisit(DAOVisits rodentVisitDao, ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2) {

        if (FlagSetup.getFlagVisitAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentVisitDao.insertRecordRodentVisit(new RodentVisitModel(Integer.valueOf(prefsGetRodentId.getInt("rodentId", 0)), rodentVisitDao.getLastIdVisit().get(0)));
            return;
        }

        int listViewLength = listViewVisit2.getCount();
        SparseBooleanArray checked = listViewVisit2.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                arrayListSelected2.add(i);

                if (FlagSetup.getFlagVisitAdd() == 1)
                    rodentVisitDao.insertRecordRodentVisit(new RodentVisitModel(arrayListID2.get(i), rodentVisitDao.getLastIdVisit().get(0)));
                else {
                    Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
                    rodentVisitDao.insertRecordRodentVisit(new RodentVisitModel(arrayListID2.get(i), idKey));
                }
            }
    }


    private Integer getVisitVet(DAOVets visitVetDao, ArrayList<Integer> arrayListSelected, ArrayList<Integer> arrayListID) {
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
        startActivity(new Intent(getApplicationContext(), ViewVisits.class));
        finish();
    }


    private void checkCheckBox() {
        if (checkBoxVisit1.isChecked()) {
            listViewVisit.setVisibility(View.VISIBLE);
            listViewVisit.setSelected(true);
        }
        else {
            listViewVisit.clearChoices();
            listViewVisit.setVisibility(View.GONE);
        }

        if (checkBoxVisit2.isChecked()) {
            listViewVisit2.setVisibility(View.VISIBLE);
            listViewVisit2.setSelected(true);
        }
        else {
            listViewVisit2.clearChoices();
            listViewVisit2.setVisibility(View.GONE);
        }
    }


    public void setVisibilityByFlag() {
        //2 = static pet relation
        if (FlagSetup.getFlagVisitAdd() == 2) {
            checkBoxVisit1.setVisibility(View.GONE);

            buttonAdd_visit.setVisibility(View.VISIBLE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.GONE);
            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        }

        // 1 = adding new vet
        if (FlagSetup.getFlagVisitAdd() == 1) {
            buttonAdd_visit.setVisibility(View.VISIBLE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.GONE);
            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagVisitAdd() == 0) {
            buttonAdd_visit.setVisibility(View.GONE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.VISIBLE);
            textViewVetRelations_visit.setVisibility(View.GONE);
            textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        }
    }





}
