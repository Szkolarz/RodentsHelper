package com.example.rodentshelper.ROOM.Notes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DAONotes;

public class AddNotes extends Activity {

    EditText editTextTopic_notes, editTextContent_notes;
    Button buttonEdit_notes, buttonAdd_notes, buttonSaveEdit_notes, buttonDelete_notes;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_item_list);

        editTextTopic_notes = findViewById(R.id.editTextTopic_notes);
        editTextContent_notes = findViewById(R.id.editTextContent_notes);

        buttonEdit_notes = findViewById(R.id.buttonEdit_notes);
        buttonAdd_notes = findViewById(R.id.buttonAdd_notes);
        buttonSaveEdit_notes = findViewById(R.id.buttonSaveEdit_notes);
        buttonDelete_notes = findViewById(R.id.buttonDelete_notes);



        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO vetDao = db.dao();



        if (FlagSetup.getFlagNotesAdd() == 1) {
            buttonAdd_notes.setVisibility(View.VISIBLE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.GONE);
        } else {
            buttonAdd_notes.setVisibility(View.GONE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.VISIBLE);


            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_animalKey = (getIntent().getStringExtra("id_animalKey"));
            String topicKey = getIntent().getStringExtra("topicKey");
            String contentKey = getIntent().getStringExtra("contentKey");


            editTextTopic_notes.setText(topicKey);
            editTextContent_notes.setText(contentKey);



            buttonSaveEdit_notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveEditNotes();
                }
            });
        }



        buttonAdd_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotes();
            }
        });


    }




    public void saveNotes() {

        String topicKey = editTextTopic_notes.getText().toString();
        String contentKey = editTextContent_notes.getText().toString();


        if (contentKey.equals("") ) {
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać treść notatki", this);
        }
        else {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes dao = db.daoNotes();

            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

            dao.insertRecordNotes(new NotesModel(prefsGetRodentId.getInt("rodentId", 0), topicKey, contentKey));
            System.out.println("DODANO");
            viewNotes();
        }

    }

    public void saveEditNotes() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotes dao = db.daoNotes();

        Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
        dao.updatNotesById(idKey, editTextTopic_notes.getText().toString(), editTextContent_notes.getText().toString());

        viewNotes();
    }




    private void viewNotes() {
        startActivity(new Intent(getApplicationContext(), ViewNotes.class));
        finish();
    }







}
