package com.gryzoniopedia.rodentshelper.ROOM.Visits;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.Notifications.SettingUpAlarms.NotificationVisit;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotifications;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAORodents;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVisits;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.VetModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddEditVisits extends AppCompatActivity {

    private EditText editTextReason_visit;
    private TextView textViewDate_visit, textViewTime_visit, textViewDate1_visitHidden,
            textViewVetRelationsInfo_visit, textViewVetRelationsInfo_visit2, textViewVetRelations_visit;
    private Button buttonEdit_visit, buttonAdd_visit, buttonSaveEdit_visit, buttonDelete_visit;
    private ListView listViewVisit, listViewVisit2;
    private CheckBox checkBoxVisit1, checkBoxVisit2, checkBoxVisit3;

    private DatePickerDialog.OnDateSetListener dateSetListener1;
    private String dateFormat1 = null;

    private int hour, minute;
    private String sendTime = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visits_item_list);

        TextView textViewRequired_visit = findViewById(R.id.textViewRequired_visit);

        LinearLayout linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        editTextReason_visit = findViewById(R.id.editTextReason_visit);

        textViewDate_visit = findViewById(R.id.textViewDate_visit);
        textViewTime_visit = findViewById(R.id.textViewTime_visit);


        buttonEdit_visit = findViewById(R.id.buttonEdit_visit);
        buttonAdd_visit = findViewById(R.id.buttonAdd_visit);
        buttonSaveEdit_visit = findViewById(R.id.buttonSaveEdit_visit);
        buttonDelete_visit = findViewById(R.id.buttonDelete_visit);

        textViewDate1_visitHidden = findViewById(R.id.textViewDate1_visitHidden);

        textViewVetRelationsInfo_visit = findViewById(R.id.textViewVetRelationsInfo_visit);
        textViewVetRelationsInfo_visit2 = findViewById(R.id.textViewVetRelationsInfo_visit2);
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
       // checkBoxVisit3.setEnabled(false);

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

        AppDatabase db = Room.databaseBuilder(AddEditVisits.this,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();
        DAOVets daoVets = db.daoVets();
        DAORodents daoRodents = db.daoRodents();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, arrayListLV);
        List<VetModel> vetModel = daoVets.getAllVets();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayListLV2);
        SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        List<RodentModel> rodentModel = daoRodents.getAllRodentsTEST();

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

        setVisibilityByFlag(toolbar);


        //EDIT
        if (FlagSetup.getFlagVisitAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_vetKey = (getIntent().getStringExtra("id_vetKey"));
            String dateKey = getIntent().getStringExtra("dateKey");
            String timeKey = getIntent().getStringExtra("timeKey");
            String reasonKey = getIntent().getStringExtra("reasonKey");

            DAONotifications daoNotifications = db.daoNotifications();

            if (daoNotifications.getIdVisitFromVisit(idKey) != null) {
                checkBoxVisit3.setChecked(true);
            }

            editTextReason_visit.setText(reasonKey);
            textViewTime_visit.setText(timeKey);

            textViewDate_visit.setText(dateKey);
            textViewDate1_visitHidden.setText(dateKey);

            checkForCheckbox();

            List<VisitsWithRodentsCrossRef> visitModel = daoVisits.getVisitsWithRodents();


            int positionKey = Integer.parseInt(getIntent().getStringExtra("positionKey"));

            try {
                if (!(id_vetKey).equals("null")) {
                    List<String> list = daoVisits.getAllVisitsVets(Integer.valueOf(id_vetKey));
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

            buttonSaveEdit_visit.setOnClickListener(view -> saveEditVisit(idKey, id_vetKey, arrayListSelected, arrayListID, arrayListSelected2, arrayListID2));


        }



        textViewDate_visit.setOnClickListener(view -> pickDate(dateSetListener1));


        dateSetListener1 = (datePicker, year, month, day) -> {
            month += 1;
            String date = day + "/" + month + "/" + year;
            textViewDate_visit.setText(date);
            dateFormat1 = (year + "-" + month + "-" + day);
            textViewDate1_visitHidden.setText(dateFormat1);
            checkForCheckbox();
        };


        checkBoxVisit1.setOnClickListener(view -> checkCheckBox());

        checkBoxVisit2.setOnClickListener(view -> checkCheckBox());

        checkBoxVisit3.setOnClickListener(view -> {
            if (checkBoxVisit3.isChecked()) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddEditVisits.this, R.style.AlertWhiteButtons);

                View mView = AddEditVisits.this.getLayoutInflater().inflate(R.layout.dialog_spinner, null);

                mBuilder.setTitle("Powiadomienie zostanie wysłane...");
                Spinner mSpinner = mView.findViewById(R.id.spinner_weight);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AddEditVisits.this,
                        android.R.layout.simple_spinner_item,
                        AddEditVisits.this.getResources().getStringArray(R.array.visitList));
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter1);
                mSpinner.setSelection(1);

                mBuilder.setPositiveButton("OK", (dialog, which) -> {
                    sendTime = mSpinner.getSelectedItem().toString();
                    dialog.dismiss();
                });

                mBuilder.setNegativeButton("Anuluj", (dialog, which) -> {
                    checkBoxVisit3.setChecked(false);
                    dialog.dismiss();
                });

                mBuilder.setOnCancelListener(dialog -> checkBoxVisit3.setChecked(false));

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }

        });

        buttonAdd_visit.setOnClickListener(view -> saveVisit(arrayListSelected, arrayListID, arrayListSelected2, arrayListID2, textViewRequired_visit));

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(AddEditVisits.this, ViewVisits.class);
            startActivity(intent);
            finish();
        });
    }


        private void pickDate (DatePickerDialog.OnDateSetListener dateSetListener) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditVisits.this,
                    android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }

        public void onClickTime(View view) {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
                hour = selectedHour;
                minute = selectedMinute;
                textViewTime_visit.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                checkForCheckbox();
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

            timePickerDialog.setTitle("Wybierz godzinę");
            timePickerDialog.show();
        }



    public void saveVisit(ArrayList<Integer> arrayListSelected, ArrayList<Integer> arrayListID,
                          ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2, TextView textViewRequired_visit) {

        String stringDate1 = dateFormat1;
        String timeKey = textViewTime_visit.getText().toString();
        String reasonKey = editTextReason_visit.getText().toString();

        AppDatabase db = Room.databaseBuilder(AddEditVisits.this,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();
        DAOVets daoVets = db.daoVets();

        if (stringDate1 == null ) {

            textViewRequired_visit.setVisibility(View.VISIBLE);

            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać datę wizyty.", AddEditVisits.this);
        } else {

            Integer id_vetKey = getVisitVet(daoVets, arrayListSelected, arrayListID);

            daoVisits.insertRecordVisit(new VisitModel(id_vetKey, Date.valueOf(stringDate1),
                    timeKey, reasonKey));


            if (checkBoxVisit3.isChecked()) {

                SharedPreferences prefsNotificationVisit = AddEditVisits.this.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditorNotificationVisit = prefsNotificationVisit.edit();
                prefsEditorNotificationVisit.putBoolean("prefsNotificationVisit", true);
                prefsEditorNotificationVisit.apply();

                DAONotifications daoNotifications = db.daoNotifications();

                NotificationVisit notificationVisit = new NotificationVisit();
                notificationVisit.setUpNotificationVisit(AddEditVisits.this, timeKey, dateFormat1, sendTime, daoNotifications.getLastIdFromVisit());
            }

            System.out.println("DODANO");
            getVisitVet(daoVets, arrayListSelected, arrayListID);
            getRodentVisit(daoVisits, arrayListSelected2, arrayListID2);
            db.close();
            viewVisits();
        }

    }

    private void saveEditVisit(Integer idKey, String id_vetKey, ArrayList<Integer> arrayListSelected, ArrayList<Integer> arrayListID,
                               ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2) {
        dateFormat1 = textViewDate1_visitHidden.getText().toString();

        AppDatabase db = Room.databaseBuilder(AddEditVisits.this,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();
        DAOVets daoVets = db.daoVets();
        DAONotifications daoNotifications = db.daoNotifications();

        daoVisits.updateVisitById(idKey, null,  Date.valueOf(dateFormat1),
                textViewTime_visit.getText().toString(), editTextReason_visit.getText().toString());

        // visitDao.SetVisitsIdVetNull(idKey);


        if (checkBoxVisit3.isChecked()) {
            SharedPreferences prefsNotificationVisit = AddEditVisits.this.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditorNotificationVisit = prefsNotificationVisit.edit();
            prefsEditorNotificationVisit.putBoolean("prefsNotificationVisit", true);
            prefsEditorNotificationVisit.apply();

            if (sendTime == null)
               sendTime = daoNotifications.getSendTimeNotificationVisit(idKey);

            daoNotifications.deleteNotificationByVisitId(idKey);

            NotificationVisit notificationVisit = new NotificationVisit();

            notificationVisit.setUpNotificationVisit(AddEditVisits.this, textViewTime_visit.getText().toString(), dateFormat1, sendTime, idKey);

        } else {
            //daoNotifications.updateMaxIdFromNotificationVisit(idKey);

            if (daoNotifications.selectIdVisitFromNotificationVisit(idKey) != null) {
                NotificationVisit notificationVisit = new NotificationVisit();
                notificationVisit.cancelAlarm(AddEditVisits.this, idKey);
                daoNotifications.deleteNotificationByVisitId(idKey);
            }

        }

        db.close();


        if (checkBoxVisit1.isChecked()) {
            daoVisits.SetVisitsIdVet(getVisitVet(daoVets, arrayListSelected,arrayListID), idKey);
        }
        else {
            if (getVisitVet(daoVets, arrayListSelected,arrayListID) != null)
                daoVisits.SetVisitsIdVetNull(Integer.valueOf(id_vetKey));
        }

        for (int i=0; i<arrayListID2.size(); i++) {
            daoVisits.DeleteAllRodentsVisitsByVisitAndRodent(idKey, arrayListID2.get(i));
        }

        getRodentVisit(daoVisits, arrayListSelected2, arrayListID2);
        db.close();

        viewVisits();
    }


    private void getRodentVisit(DAOVisits rodentVisitDao, ArrayList<Integer> arrayListSelected2, ArrayList<Integer> arrayListID2) {

        if (FlagSetup.getFlagVisitAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentVisitDao.insertRecordRodentVisit(new RodentVisitModel(prefsGetRodentId.getInt("rodentId", 0), rodentVisitDao.getLastIdVisit().get(0)));
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
        Integer id_vet;
        SparseBooleanArray checked = listViewVisit.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                arrayListSelected.add(i);

                id_vet = visitVetDao.getIdofVet(arrayListID.get(i));

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


    private void checkForCheckbox() {
        if (!textViewDate_visit.getText().toString().equals("Ustaw...") && !textViewTime_visit.getText().toString().equals("Ustaw...") ) {
            checkBoxVisit3.setVisibility(View.VISIBLE);
        }
    }


    public void setVisibilityByFlag(Toolbar toolbar) {
        //2 = static pet relation
        if (FlagSetup.getFlagVisitAdd() == 2) {
            toolbar.setTitle("Dodawanie wizyty");
            checkBoxVisit2.setVisibility(View.GONE);
            buttonAdd_visit.setVisibility(View.VISIBLE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.GONE);
            textViewVetRelations_visit.setVisibility(View.GONE);
        }

        // 1 = adding new vet
        if (FlagSetup.getFlagVisitAdd() == 1) {
            toolbar.setTitle("Dodawanie wizyty");
            buttonAdd_visit.setVisibility(View.VISIBLE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.GONE);
            textViewVetRelations_visit.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagVisitAdd() == 0) {
            toolbar.setTitle("Edytowanie wizyty");
            buttonAdd_visit.setVisibility(View.GONE);
            buttonEdit_visit.setVisibility(View.GONE);
            buttonDelete_visit.setVisibility(View.GONE);
            buttonSaveEdit_visit.setVisibility(View.VISIBLE);
            textViewVetRelations_visit.setVisibility(View.GONE);
        }

        textViewVetRelationsInfo_visit.setVisibility(View.GONE);
        textViewVetRelationsInfo_visit2.setVisibility(View.GONE);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AddEditVisits.this, ViewVisits.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
