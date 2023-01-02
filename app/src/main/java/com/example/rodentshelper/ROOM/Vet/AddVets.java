package com.example.rodentshelper.ROOM.Vet;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAORodents;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM._MTM._RodentVet.RodentVetModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddVets extends AppCompatActivity {

    EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
    Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet, buttonSaveEdit_vet;
    ListView ListViewVet;
    CheckBox checkBoxVet;
    TextView textViewRodentRelationsInfo_vet;
    LinearLayout aaa1;
    ImageButton imageButtonCall_vet;



    //pelna lista zwierzat
    private ArrayList<String> arrayListLV;
    //wybrane ID
    private ArrayList<Integer> arrayListID;
    //koncowa lista z zaznaczonymi zwierzetami
    private ArrayList<Integer> arrayListSelected;

    private AppDatabase getAppDatabase () {
         AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
         return db;
    }

    private DAORodents getDaoRodents () {
        DAORodents daoRodents = getAppDatabase().daoRodents();
        return daoRodents;
    }

    private DAOVets getDaoVets () {
        DAOVets daoVets = getAppDatabase().daoVets();
        return daoVets;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vets_item_list);

        TextView textViewRequired_vet = findViewById(R.id.textViewRequired_vet);

        LinearLayout linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        buttonAdd_vet = findViewById(R.id.buttonAdd_vet);
        buttonEdit_vet = findViewById(R.id.buttonEdit_vet);
        buttonDelete_vet = findViewById(R.id.buttonDelete_vet);
        buttonSaveEdit_vet = findViewById(R.id.buttonSaveEdit_vet);

        ListViewVet = findViewById(R.id.ListViewVet);
        ListViewVet.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListViewVet.setItemsCanFocus(false);
        ListViewVet.setVisibility(View.GONE);

        checkBoxVet = findViewById(R.id.checkBoxVet);

        arrayListID = new ArrayList<>();
        arrayListLV = new ArrayList<>();
        arrayListSelected = new ArrayList<>();

        textViewRodentRelationsInfo_vet = findViewById(R.id.textViewRodentRelationsInfo_vet);
        textViewRodentRelationsInfo_vet.setVisibility(View.GONE);

        editTextName_vet = findViewById(R.id.editTextName_vet);
        editTextAddress_vet = findViewById(R.id.editTextAddress_vet);
        editTextPhone_vet = findViewById(R.id.editTextPhone_vet);
        editTextNotes_vet = findViewById(R.id.editTextPeriodicity_med);

        imageButtonCall_vet = findViewById(R.id.imageButtonCall_vet);
        imageButtonCall_vet.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayListLV);

        SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        List<RodentModel> rodentModel = getDaoRodents().getAllRodents(prefsFirstStart.getInt("prefsFirstStart", 0));

        //List<RodentModel> list = rodentDao.getAllRodentsVets(idKey);

        for(int i = 0; i < rodentModel.size(); i++) {
            arrayListID.add(rodentModel.get(i).getId());
            arrayListLV.add(rodentModel.get(i).getName());
        }

        ListViewVet.setAdapter(adapter);



        setVisibilityByFlag(toolbar);


        if (FlagSetup.getFlagVetAdd() == 0) {

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String nameKey = getIntent().getStringExtra("nameKey");
            String addressKey = getIntent().getStringExtra("addressKey");
            String phoneKey = getIntent().getStringExtra("phoneKey");
            String notesKey = getIntent().getStringExtra("notesKey");

            editTextName_vet.setText(nameKey);
            editTextAddress_vet.setText(addressKey);
            editTextPhone_vet.setText(phoneKey);
            editTextNotes_vet.setText(notesKey);


            List<VetWithRodentsCrossRef> vetModel = getDaoVets().getVetsWithRodents();


            //checkBoxVet.setChecked(true);


            Integer positionKey = Integer.parseInt(getIntent().getStringExtra("positionKey"));

            for (int j = 0; j < arrayListLV.size(); j ++) {
                try {
                    for (int i = 0; i < vetModel.get(positionKey).rodents.size(); i++) {
                        if (arrayListLV.get(j).equals(vetModel.get(positionKey).rodents.get(i).getName())) {
                            ListViewVet.setItemChecked(j, true);
                            checkBoxVet.setChecked(true);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("There is no any rodent left in relation");
                }
            }

            checkCheckBox();
          /*  List<String> list = vetDao.getAllRodentsVets(idKey);





            try {
                for (int k = 0; k < vetModel.get(2).rodents.size(); k++) {
                       textViewRodentRelations_vet.append(vetModel.get(2).rodents.get(k).getName());
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("There is no any rodent left in relation");
            }


            for (int j = 0; j < arrayListLV.size(); j ++) {
                // aaaar.add(arrayListLV.get(j));
                for(int i = 0; i < list.size(); i++) {

                    if (arrayListLV.get(j).equals(list.get(i))) {

                        ListViewVet.setItemChecked(j, true);
                        checkBoxVet.setChecked(true);
                    }

                }
            }

            checkCheckBox(checkBoxVet, ListViewVet);*/



            buttonSaveEdit_vet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (editTextName_vet.getText().toString().length() <= 0) {
                        textViewRequired_vet.setVisibility(View.VISIBLE);
                        Alerts alert = new Alerts();
                        alert.alertLackOfData("Wprowadź nazwę weterynarza", AddVets.this);
                        Toast.makeText(AddVets.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
                    } else {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                        DAOVets vetDao = db.daoVets();


                        vetDao.updateVetById(idKey, editTextName_vet.getText().toString(),
                                editTextAddress_vet.getText().toString(), editTextPhone_vet.getText().toString(),
                                editTextNotes_vet.getText().toString());

                        vetDao.DeleteAllRodentsVetsByVet(idKey);

                        getSelectedItems(vetDao);

                        viewVets();

                        db.close();
                    }

                }
            });


        }




        checkBoxVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCheckBox();
            }
        });


        buttonAdd_vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVet(textViewRequired_vet);
            }
        });

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void listActions() {

    }


    public void saveVet(TextView textViewRequired_vet) {

        editTextName_vet = findViewById(R.id.editTextName_vet);
        editTextAddress_vet = findViewById(R.id.editTextAddress_vet);
        editTextPhone_vet = findViewById(R.id.editTextPhone_vet);
        editTextNotes_vet = findViewById(R.id.editTextPeriodicity_med);

        String stringName = editTextName_vet.getText().toString();
        String stringAddress = editTextAddress_vet.getText().toString();
        String stringPhone = editTextPhone_vet.getText().toString();
        String stringNotes = editTextNotes_vet.getText().toString();



        if (stringName.length() <= 0) {
            textViewRequired_vet.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Wprowadź nazwę weterynarza", this);
            Toast.makeText(AddVets.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
        } else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAOVets vetDao = db.daoVets();

            vetDao.insertRecordVet(new VetModel(stringName, stringAddress, stringPhone, stringNotes));

            System.out.println("DODANO");

            getSelectedItems(vetDao);

            viewVets();
        }

    }

    public void getSelectedItems(DAOVets rodentVetDao) {
        if (FlagSetup.getFlagVetAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentVetDao.insertRecordRodentVet(new RodentVetModel (Integer.valueOf(prefsGetRodentId.getInt("rodentId", 0)), rodentVetDao.getLastIdVet().get(0)));
            return;
        }

        int listViewLength = ListViewVet.getCount();
        SparseBooleanArray checked = ListViewVet.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                //String item = ArrayListLV.get(i);
                arrayListSelected.add(i);

                if (FlagSetup.getFlagVetAdd() == 1)
                    rodentVetDao.insertRecordRodentVet(new RodentVetModel(arrayListID.get(i), rodentVetDao.getLastIdVet().get(0) ));
                else {
                    Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
                    rodentVetDao.insertRecordRodentVet(new RodentVetModel(arrayListID.get(i), idKey));
                }
            }
    }


    public void viewVets() {
        startActivity(new Intent(getApplicationContext(), ViewVets.class));
        finish();
    }


    private void checkCheckBox() {
        if (checkBoxVet.isChecked()) {
            ListViewVet.setVisibility(View.VISIBLE);
            ListViewVet.setSelected(true);

        }
        else {
            ListViewVet.clearChoices();
            ListViewVet.setVisibility(View.GONE);
        }
    }

    public void setVisibilityByFlag(Toolbar toolbar) {
        //2 = static pet relation
        if (FlagSetup.getFlagVetAdd() == 2) {
            toolbar.setTitle("Dodawanie weterynarza");
            checkBoxVet.setVisibility(View.GONE);
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
            buttonSaveEdit_vet.setVisibility(View.GONE);
        }

        // 1 = adding new vet
        if (FlagSetup.getFlagVetAdd() == 1) {
            toolbar.setTitle("Dodawanie weterynarza");
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
            buttonSaveEdit_vet.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagVetAdd() == 0) {
            toolbar.setTitle("Edytowanie weterynarza");
            buttonSaveEdit_vet.setVisibility(View.VISIBLE);
            buttonAdd_vet.setVisibility(View.GONE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
        }
    }






}