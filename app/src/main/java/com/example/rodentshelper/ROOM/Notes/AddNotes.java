package com.example.rodentshelper.ROOM.Notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddNotes extends AppCompatActivity {

    private EditText editTextTopic_notes, editTextContent_notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_item_list);

        TextView textViewDate_notes, textViewDateHidden_notes, textViewRequired_notes;

        LinearLayout linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        editTextTopic_notes = findViewById(R.id.editTextTopic_notes);
        editTextContent_notes = findViewById(R.id.editTextContent_notes);

        Button buttonEdit_notes = findViewById(R.id.buttonEdit_notes);
        Button buttonAdd_notes = findViewById(R.id.buttonAdd_notes);
        Button buttonSaveEdit_notes = findViewById(R.id.buttonSaveEdit_notes);
        Button buttonDelete_notes = findViewById(R.id.buttonDelete_notes);

        textViewDate_notes = findViewById(R.id.textViewDate_notes);
        textViewDateHidden_notes = findViewById(R.id.textViewDateHidden_notes);
        textViewRequired_notes = findViewById(R.id.textViewRequired_notes);


        if (FlagSetup.getFlagNotesAdd() == 1) {
            buttonAdd_notes.setVisibility(View.VISIBLE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.GONE);
            toolbar.setTitle("Dodawanie notatki");

            Date dateGet = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(dateGet);
            textViewDateHidden_notes.setText(formattedDate);
            textViewDate_notes.setText(DateFormat.formatDate(java.sql.Date.valueOf(formattedDate)));

        } else {
            buttonAdd_notes.setVisibility(View.GONE);
            buttonEdit_notes.setVisibility(View.GONE);
            buttonDelete_notes.setVisibility(View.GONE);
            buttonSaveEdit_notes.setVisibility(View.VISIBLE);
            toolbar.setTitle("Edycja notatki");


            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            String id_animalKey = (getIntent().getStringExtra("id_animalKey"));
            String topicKey = getIntent().getStringExtra("topicKey");
            String contentKey = getIntent().getStringExtra("contentKey");
            String create_dateKey = getIntent().getStringExtra("create_dateKey");


            editTextTopic_notes.setText(topicKey);
            editTextContent_notes.setText(contentKey);
            textViewDate_notes.setText( DateFormat.formatDate(java.sql.Date.valueOf(create_dateKey)));

            buttonSaveEdit_notes.setOnClickListener(view -> saveEditNotes(textViewRequired_notes));
        }

        buttonAdd_notes.setOnClickListener(view -> saveNotes(textViewDateHidden_notes, textViewRequired_notes));

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }




    public void saveNotes(TextView textViewDateHidden_notes, TextView textViewRequired_notes) {

        String topicKey = editTextTopic_notes.getText().toString();
        String contentKey = editTextContent_notes.getText().toString();

        if (contentKey.equals("") ) {
            textViewRequired_notes.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać treść notatki", this);
        } else {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes dao = db.daoNotes();

            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

            dao.insertRecordNotes(new NotesModel(prefsGetRodentId.getInt("rodentId", 0), topicKey, contentKey,
                    java.sql.Date.valueOf(textViewDateHidden_notes.getText().toString()), null));
            db.close();
            System.out.println("DODANO");
            viewNotes();
        }

    }

    public void saveEditNotes(TextView textViewRequired_notes) {
        if (editTextContent_notes.getText().toString().equals("") ) {
            textViewRequired_notes.setVisibility(View.VISIBLE);
            Alerts alert = new Alerts();
            alert.alertLackOfData("Należy wpisać treść notatki", this);
        } else {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
            DAONotes dao = db.daoNotes();

            Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));
            dao.updateNotesById(idKey, editTextTopic_notes.getText().toString(), editTextContent_notes.getText().toString());
            db.close();

            viewNotes();
        }
    }




    private void viewNotes() {
        startActivity(new Intent(getApplicationContext(), ViewNotes.class));
        finish();
    }







}
