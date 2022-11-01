package com.example.rodentshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Arrays;
import java.util.List;

public class AddVets extends AppCompatActivity {

    EditText editTextName_vet, editTextAddress_vet, editTextPhone_vet, editTextNotes_vet;
    Button buttonDelete_vet, buttonEdit_vet, buttonAdd_vet;
    ListView ListViewVet;
    CheckBox checkBoxVet;

    private ArrayList<String> ArrayListLV;
    private ArrayList<Integer> ArrayListID;
    private ArrayList<Integer> ArrayListSelected;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vets_item_list);

        buttonAdd_vet = findViewById(R.id.buttonAdd_vet);
        buttonEdit_vet = findViewById(R.id.buttonEdit_vet);
        buttonDelete_vet = findViewById(R.id.buttonDelete_vet);

        ListViewVet = findViewById(R.id.ListViewVet);
        ListViewVet.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListViewVet.setItemsCanFocus(false);
        ListViewVet.setVisibility(View.GONE);

        checkBoxVet = findViewById(R.id.checkBoxVet);

        ArrayListID = new ArrayList<>();
        ArrayListLV = new ArrayList<>();
        ArrayListSelected = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, ArrayListLV);

        // on below line we are setting adapter for our list view.
        ListViewVet.setAdapter(adapter);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO rodentDao = db.dao();


        List<RodentModel> rodentModel = rodentDao.getAllRodents();

        for(int i = 0; i < rodentModel.size(); i++) {
            ArrayListID.add(rodentModel.get(i).getId());
            ArrayListLV.add(rodentModel.get(i).getName());
        }





        System.out.println(FlagSetup.getFlagVetAdd());

        //0 = widok zwykłej listy (edytuj + usuń)
        if (FlagSetup.getFlagVetAdd() == 0) {
            buttonAdd_vet.setVisibility(View.GONE);
            buttonEdit_vet.setVisibility(View.VISIBLE);
            buttonDelete_vet.setVisibility(View.VISIBLE);
        }
        // 1 = dodawanie nowego
        else {
            buttonAdd_vet.setVisibility(View.VISIBLE);
            buttonEdit_vet.setVisibility(View.GONE);
            buttonDelete_vet.setVisibility(View.GONE);
        }

        editTextName_vet = findViewById(R.id.editTextName_vet);
        editTextAddress_vet = findViewById(R.id.editTextAddress_vet);
        editTextPhone_vet = findViewById(R.id.editTextPhone_vet);
        editTextNotes_vet = findViewById(R.id.editTextNotes_vet);



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


            int LVlength = ListViewVet.getCount();
            SparseBooleanArray checked = ListViewVet.getCheckedItemPositions();
            for (int i = 0; i < LVlength; i++)
                if (checked.get(i)) {
                    //String item = ArrayListLV.get(i);
                    ArrayListSelected.add(i);

                    rodentVetDao.insertRecordRodentVet(new RodentVetModel(ArrayListID.get(i), rodentVetDao.getLastIdVet().get(0) ));

                    System.out.println(ArrayListID.get(i) + " jaki ajdicz");
                }



            viewVets();
        }

    }

    public void viewVets() {
        finish();
        Intent intent = new Intent(AddVets.this, ViewVets.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewVets();
        }
        return super.onKeyDown(keyCode, event);
    }




}
