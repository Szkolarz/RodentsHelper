package com.example.rodentshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.ClassModels.VetsModelClass;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.MainViews.ViewVets;
import com.example.rodentshelper.SQL.DBHelperVet;

public class AddVets extends AppCompatActivity {

    private EditText editTextListID, editTextListName, editTextListAddress, editTextListPhone, editTextListNotes;
    private Button buttonListEdit, buttonListDelete, buttonListAdd;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vets_item_list);

        buttonListAdd = findViewById(R.id.buttonListAdd);
        buttonListEdit = findViewById(R.id.buttonListEdit);
        buttonListDelete = findViewById(R.id.buttonListDelete);

        System.out.println("UIWHWIQUHUIWQ");

        if (FlagSetup.getFlagVetAdd() == 0) {
            buttonListAdd.setVisibility(View.GONE);
            buttonListEdit.setVisibility(View.VISIBLE);
            buttonListDelete.setVisibility(View.VISIBLE);
            System.out.println("AAAA");
        }
        else {
            System.out.println("BBBBBB");
            buttonListAdd.setVisibility(View.VISIBLE);
            buttonListEdit.setVisibility(View.GONE);
            buttonListDelete.setVisibility(View.GONE);
        }

        editTextListName = findViewById(R.id.editTextListName);

        editTextListAddress = findViewById(R.id.editTextListAddress);
        editTextListPhone = findViewById(R.id.editTextListPhone);
        editTextListNotes = findViewById(R.id.editTextListNotes);


        buttonListEdit = findViewById(R.id.buttonListEdit);
        buttonListDelete = findViewById(R.id.buttonListDelete);



    }

    public void saveVet(View view) {
        String stringName = editTextListName.getText().toString();
        String stringAddress = editTextListAddress.getText().toString();
        String stringPhone = editTextListPhone.getText().toString();
        String stringNotes = editTextListNotes.getText().toString();


        if (stringName.length() <= 0) {
            Toast.makeText(AddVets.this, "Wprowadź wszystkie dane", Toast.LENGTH_SHORT).show();
        }
        else {
            DBHelperVet databaseHelper = new DBHelperVet(AddVets.this);

            System.out.println(stringAddress + " adressssssss");
            System.out.println(stringName + " adressssssss");
            VetsModelClass vetsModelClass = new VetsModelClass(1, stringName, stringAddress, stringPhone, stringNotes);
            databaseHelper.addNewVet(vetsModelClass);
            Toast.makeText(AddVets.this, "Pomyślnie dodano", Toast.LENGTH_SHORT).show();
            System.out.println("DODANO");
            FlagSetup.setFlagVetAdd(0);
            
            Intent intent = new Intent(AddVets.this, ViewVets.class);
            startActivity(intent);
            finish();
            System.out.println("finity");
        }

    }

    public void viewVets() {
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
