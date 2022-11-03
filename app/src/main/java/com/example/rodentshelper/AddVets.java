package com.example.rodentshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.MainViews.ViewVets;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.MTM.RodentVetModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AddVets extends AppCompatActivity {

    EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
    Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet, buttonSaveEdit_vet;
    ListView ListViewVet;
    CheckBox checkBoxVet;



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

        editTextName_vet = findViewById(R.id.editTextName_vet);
        editTextAddress_vet = findViewById(R.id.editTextAddress_vet);
        editTextPhone_vet = findViewById(R.id.editTextPhone_vet);
        editTextNotes_vet = findViewById(R.id.editTextNotes_vet);

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





        System.out.println(FlagSetup.getFlagVetAdd());

        //0 = widok zwykłej listy (edytuj + usuń)
        if (FlagSetup.getFlagVetAdd() == 0) {

        }
        // 1 = dodawanie nowego
        if (FlagSetup.getFlagVetAdd() == 1) {
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
            buttonSaveEdit_vet.setVisibility(View.GONE);
        }

        if (FlagSetup.getFlagVetAdd() == 0) {
            buttonSaveEdit_vet.setVisibility(View.VISIBLE);
            buttonAdd_vet.setVisibility(View.GONE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String nameKey = getIntent().getStringExtra("nameKey");
            String addressKey = getIntent().getStringExtra("addressKey");
            String phoneKey = getIntent().getStringExtra("phoneKey");
            String notesKey = getIntent().getStringExtra("notesKey");

            System.out.println(nameKey);

            editTextName_vet.setText(nameKey);
            editTextAddress_vet.setText(addressKey);
            editTextPhone_vet.setText(phoneKey);
            editTextNotes_vet.setText(notesKey);


            DAO vetDao = db.dao();

            List<String> list = vetDao.getAllRodentsVets(idKey);

            for (int j = 0; j < arrayListLV.size(); j ++) {
               // aaaar.add(arrayListLV.get(j));
                for(int i = 0; i < list.size(); i++) {

                    System.out.println(arrayListLV.get(j) + "fds");
                    System.out.println(list.get(i) + "qqq");

                    if (arrayListLV.get(j).equals(list.get(i))) {


                        ListViewVet.setItemChecked(i, true);
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
        editTextNotes_vet = findViewById(R.id.editTextNotes_vet);

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
        int listViewLength = ListViewVet.getCount();
        SparseBooleanArray checked = ListViewVet.getCheckedItemPositions();
        for (int i = 0; i < listViewLength; i++)
            if (checked.get(i)) {
                //String item = ArrayListLV.get(i);
                arrayListSelected.add(i);

                rodentVetDao.insertRecordRodentVet(new RodentVetModel(arrayListID.get(i), rodentVetDao.getLastIdVet().get(0) ));

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
            listViewVet.setVisibility(View.GONE);
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
