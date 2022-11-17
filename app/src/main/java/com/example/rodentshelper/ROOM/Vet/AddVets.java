package com.example.rodentshelper.ROOM.Vet;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM._MTM.RodentVetModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.ArrayList;
import java.util.List;

public class AddVets extends AppCompatActivity {

    EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
    Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet, buttonSaveEdit_vet;
    ListView ListViewVet;
    CheckBox checkBoxVet;
    TextView textViewRodentRelationsInfo_vet;
LinearLayout aaa1;


    //pelna lista zwierzat
    private ArrayList<String> arrayListLV;
    //wybrane ID
    private ArrayList<Integer> arrayListID;
    //koncowa lista z zaznaczonymi zwierzetami
    private ArrayList<Integer> arrayListSelected;
    private Context context;
    private ArrayList<String> aaaar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vets_item_list);

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, arrayListLV);

        // on below line we are setting adapter for our list view.
        ListViewVet.setAdapter(adapter);



        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO rodentDao = db.dao();


        List<RodentModel> rodentModel = rodentDao.getAllRodents();

        for(int i = 0; i < rodentModel.size(); i++) {
            arrayListID.add(rodentModel.get(i).getId());
            arrayListLV.add(rodentModel.get(i).getName());
        }



        setVisibilityByFlag();


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


            DAO vetDao = db.dao();

            List<String> list = vetDao.getAllRodentsVets(idKey);

            for (int j = 0; j < arrayListLV.size(); j ++) {
               // aaaar.add(arrayListLV.get(j));
                for(int i = 0; i < list.size(); i++) {

                    if (arrayListLV.get(j).equals(list.get(i))) {

                        ListViewVet.setItemChecked(j, true);
                        checkBoxVet.setChecked(true);
                    }

                }
            }

            checkCheckBox(checkBoxVet, ListViewVet);



            buttonSaveEdit_vet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
                    DAO vetDao = db.dao();


                    vetDao.updateVetById(idKey, editTextName_vet.getText().toString(),
                            editTextAddress_vet.getText().toString(), editTextPhone_vet.getText().toString(),
                            editTextNotes_vet.getText().toString());

                    vetDao.DeleteAllRodentsVetsByVet(idKey);

                    getSelectedItems(vetDao);

                    viewVets();
                }
            });


        }




        checkBoxVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBoxVet.isChecked()) {
                    ListViewVet.setVisibility(View.VISIBLE);
                }
                else {
                    ListViewVet.clearChoices();
                    ListViewVet.setVisibility(View.GONE);
                }

            }
        });


        buttonAdd_vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             saveVet();
            }
        });
    }

    public void listActions() {

    }


    public void saveVet() {

        editTextName_vet = findViewById(R.id.editTextName_vet);
        editTextAddress_vet = findViewById(R.id.editTextAddress_vet);
        editTextPhone_vet = findViewById(R.id.editTextPhone_vet);
        editTextNotes_vet = findViewById(R.id.editTextPeriodicity_med);

        String stringName = editTextName_vet.getText().toString();
        String stringAddress = editTextAddress_vet.getText().toString();
        String stringPhone = editTextPhone_vet.getText().toString();
        String stringNotes = editTextNotes_vet.getText().toString();



        if (stringName.length() <= 0) {
            Toast.makeText(AddVets.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
        }
        else {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAO vetDao = db.dao();
            DAO rodentVetDao = db.dao();
            vetDao.insertRecordVet(new VetModel(stringName, stringAddress, stringPhone, stringNotes));

            System.out.println("DODANO");

            getSelectedItems(rodentVetDao);

            viewVets();
        }

    }

    public void getSelectedItems(DAO rodentVetDao) {
        if (FlagSetup.getFlagVetAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            rodentVetDao.insertRecordRodentVet(new RodentVetModel (Integer.valueOf(prefsGetRodentId.getInt("rodentId", 0)), rodentVetDao.getLastIdVet().get(0)));
            System.out.println(";lkjhgtfrdfghj,");
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
        finish();
        startActivity(new Intent(getApplicationContext(), ViewVets.class));
    }


    private void checkCheckBox(CheckBox checkBoxVet, ListView listViewVet) {
        if (checkBoxVet.isChecked()) {
            listViewVet.setVisibility(View.VISIBLE);
            listViewVet.setSelected(true);

        }
        else {
            listViewVet.clearChoices();
            listViewVet.setVisibility(View.GONE);
        }
    }

    public void setVisibilityByFlag() {
        //2 = static pet relation
        if (FlagSetup.getFlagVetAdd() == 2) {
            checkBoxVet.setVisibility(View.GONE);
            System.out.println("qqqq");
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
            buttonSaveEdit_vet.setVisibility(View.GONE);
        }

        // 1 = adding new vet
        if (FlagSetup.getFlagVetAdd() == 1) {
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
            buttonSaveEdit_vet.setVisibility(View.GONE);
        }

        // 0 = edit
        if (FlagSetup.getFlagVetAdd() == 0) {
            buttonSaveEdit_vet.setVisibility(View.VISIBLE);
            buttonAdd_vet.setVisibility(View.GONE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewVets();
        }
        return super.onKeyDown(keyCode, event);
    }




}
